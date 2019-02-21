/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2019 Sophie Belin
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/* $Id$ */
package fr.aesn.rade.webapp.controller;

import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.common.util.DateConversionUtils;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.webapp.export.Export;
import fr.aesn.rade.webapp.export.ExportExcel;
import fr.aesn.rade.webapp.model.DisplayCommune;
import fr.aesn.rade.webapp.model.SearchCommune;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Spring MVC controller for searching and displaying communes.
 * @author sophie.belin
 */
@Slf4j
@Controller
@RequestMapping(CommuneController.REQUEST_MAPPING)
@SessionAttributes({"searchCommune"})
public class CommuneController {
  /** RequestMapping for this Controller. */
  public static final String REQUEST_MAPPING = "/referentiel/commune";
  /** Default name for the export file. */
  public static final String DEFAULT_EXPORT_FILENAME = "export-communes";
  /** Commune Service. */
  @Autowired
  private CommunePlusService communePlusService;
  /** Region Service. */
  @Autowired
  private RegionService regionService;
  /** Departement Service. */
  @Autowired
  private DepartementService departementService;

  /**
   * Attribut de session du contrôleur
   * @return Objet de recherche de commune
   */
  @ModelAttribute("searchCommune")
  private SearchCommune searchCommune() {
    return new SearchCommune();
  }

  /**
   * Appel de la page de recherche de commune. 
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getSearchForm(Model model,
                              @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    return viewCommuneSearch(model, searchCommune);
  }

  /**
   * Remise à zéro du formulaire de recherche
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(params = "annuler", value = "/resultats", method = RequestMethod.POST)
  public String resetSearchForm(Model model,
                                @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    searchCommune.reset();
    return "redirect:/referentiel/commune";
  }

  /**
   * Recherche d'une commune via la méthode POST en fonction d'une liste de critères
   * @param searchCommune 
   * @param model
   * @return Vue de la page de recherche ou des résultats
   */
  @RequestMapping(params = "valider", value = "/resultats", method = RequestMethod.POST)
  public String submitSearchForm(Model model,
                                 @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    log.debug("Search for commune with criteria: {}", searchCommune);
    String dept   = "-1".equals(searchCommune.getCodeDepartement())
                   ? null : searchCommune.getCodeDepartement();
    String region = "-1".equals(searchCommune.getCodeRegion())
                   ? null : searchCommune.getCodeRegion();
    String bassin = "-1".equals(searchCommune.getCodeCirconscription())
                   ? null : searchCommune.getCodeCirconscription();
    if(searchCommune.getCodeInsee() == null
        && dept == null && region == null && bassin == null
        && (searchCommune.getNomEnrichi() == null || searchCommune.getNomEnrichi().isEmpty())) {
      model.addAttribute("errorRecherche", "Au moins un des champs doit être renseigné");
      return viewCommuneSearch(model, searchCommune);
    }
    List<CommunePlusWithGenealogie> communes = null;
    communes = communePlusService.getCommuneByCriteria(searchCommune.getCodeInsee(),
                                          dept,
                                          region,
                                          bassin,
                                          searchCommune.getNomEnrichi(),
                                          searchCommune.getDateEffet());
    if(communes == null || communes.isEmpty()){
      model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
      return viewCommuneSearch(model, searchCommune);
    }
    searchCommune.setCommunes(communes);
    searchCommune.setPage("1");
    if(communes.size() == 1) {
      CommunePlusWithGenealogie commune = communes.get(0);
      Date dateValidite = searchCommune.getDateEffet();
      if (dateValidite == null) {
        dateValidite = new Date();
      }
      return "redirect:/referentiel/commune/"
             + commune.getCommunePlus().getCodeInsee() + "?date="
             + DateConversionUtils.formatDateToStringUrl(dateValidite);
    } else {
      return "redirect:/referentiel/commune/resultats?page=1";
    }
  }

  /**
   * Appel de la page de résultats de commune.
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(value = "/resultats", method = RequestMethod.GET)
  public String getResultList(Model model,
                              @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    if(searchCommune.getCommunes() != null && !searchCommune.getCommunes().isEmpty()) {
      searchCommune.buildListeResultats();
      return viewCommuneResults(model, searchCommune);
    } else {
      return viewCommuneSearch(model, searchCommune);
    }
  }

  /**
   * Recherche d'une commune via son code Insee 
   * @param code Code INSEE de la commune affichée
   * @param dateParam Date de validité de la commune passée en tant que paramètre optionnel 
   * @param model
   * @return Vue de la page de recherche ou des résultats en fonction du nombre 
   * de résultats
   */
  @RequestMapping(value = "/{code}")
  public String getDisplayPage(Model model,
                               @PathVariable("code") String code,
                               @RequestParam(value = "date", required = false) String dateParam,
                               @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    Date dateValidite = null;
    if(dateParam != null) {
      try {
        dateValidite = DateConversionUtils.formatStringToDateUrl(dateParam);
      } catch (ParseException e) {
        log.info("Invalid Date format ({}) : {}", DateConversionUtils.URL_DATE_FORMAT, dateParam);
      }
    }
    log.debug("Display commune: {}", code);
    if (code != null) {
      CommunePlusWithGenealogie commune = communePlusService.getCommuneWithGenealogie(code, dateValidite);
      if(commune != null) {
        Departement departement = departementService.getDepartementByCode(commune.getCommunePlus().getDepartement(), dateValidite);
        Region region = regionService.getRegionByCode(departement.getRegion(), dateValidite);
        DisplayCommune displayCommune = new DisplayCommune(commune, departement, region);
        return viewCommuneDisplay(model, displayCommune);
      } else {
        model.addAttribute("errorRecherche", "La commune recherchée n'existe pas");
        return viewCommuneSearch(model, searchCommune);
      }
    } else {
      model.addAttribute("errorRecherche", "La recherche n'a rien retourné");
      return viewCommuneSearch(model, searchCommune);
    }
  }

  /**
   * Initialisation de la page d'affichage d'une commune
   * @param displayCommune 
   * @param model
   * @return Vue due la page de recherche
   */
  private String viewCommuneDisplay(Model model, DisplayCommune displayCommune) {
    model.addAttribute("titre", "Commune / Détail commune "
                                 + displayCommune.getCodeInsee()
                                 + " "
                                 + displayCommune.getNomEnrichi());
    model.addAttribute("displayCommune", displayCommune);
    return "communedisplay";
  }

  /**
   * Initialisation de la page de recherche
   * @param searchCommune 
   * @param model
   * @return Vue due la page de recherche
   */
  private String viewCommuneSearch(Model model, SearchCommune searchCommune) {
    if(searchCommune.getDateEffet() == null){
      searchCommune.setDateEffet(new Date());
    }
    model.addAttribute("titre", "Rechercher une Commune");
    model.addAttribute("searchCommune", searchCommune);
    return "communesearch";
  }

  /**
   * Initialisation de la page de résultats
   * @param searchCommune 
   * @param model
   * @return Vue due la page de résultats
   */
  private String viewCommuneResults(Model model, SearchCommune searchCommune) {
    model.addAttribute("titre", "Liste des résultats");
    model.addAttribute("searchCommune", searchCommune);
    return "communeresults";
  }

  /**
   * Export de la liste des communes au format Excel
   * @param response 
   * @param searchCommune Objet contenant la recherche
   */
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public void exportExcel(HttpServletResponse response,
                          @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition",
                       "attachment; filename=\"" + DEFAULT_EXPORT_FILENAME + "\"");
    Export export = new ExportExcel();
    try (OutputStream out = response.getOutputStream()) {
      export.exportCommune(out, searchCommune.getCommunes());
      out.flush();
    } catch (IOException e) {
      log.info("Echec lors de l'export au format excel", e);
    }
  }
}
