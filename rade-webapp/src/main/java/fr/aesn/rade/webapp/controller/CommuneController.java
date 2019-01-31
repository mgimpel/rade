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
import fr.aesn.rade.service.BassinService;
import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.webapp.export.Export;
import fr.aesn.rade.webapp.export.ExportExcel;
import fr.aesn.rade.webapp.model.DisplayCommune;
import fr.aesn.rade.webapp.model.SearchCommune;
import fr.aesn.rade.webapp.model.SearchEntite;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Spring MVC controller for searching and displaying communes
 * @author sophie.belin
 */
@Slf4j
@Controller
@RequestMapping("/referentiel/commune")
@SessionAttributes({"searchCommune", "entite"})
public class CommuneController {
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
  /** Bassin Service. */
  @Autowired
  private BassinService bassinService;

  /**
   * Export de la liste des communes au format Excel
   * @param response 
   * @param searchCommune Objet contenant la recherche
   */
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public void exportCommunesExcel(HttpServletResponse response,
      @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    Export export = new ExportExcel();
    searchCommune.setListeResultats(paginateResultatsCommune(searchCommune, true));
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + DEFAULT_EXPORT_FILENAME + "\"");
    try {
      OutputStream out = response.getOutputStream();
      export.exportCommune(out, searchCommune.getCommunes());
      out.flush();
      out.close();
    } catch (IOException ex) {
      log.info("Echec lors de l'export au format excel", ex.getMessage());
    }
  }

  /**
   * Appel de la page de recherche de commune. 
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(method = RequestMethod.GET)
  public String communesearch(Model model,
                              @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    return initRechercheCommuneView(searchCommune, model);
  }

  /**
   * Appel de la page de résultats de commune.
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(value = "/resultats", method = RequestMethod.GET)
  public String communeresults(Model model,
                               @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    String view;
    if(searchCommune.getCommunes() != null && !searchCommune.getCommunes().isEmpty()) {
      searchCommune.setListeResultats(paginateResultatsCommune(searchCommune, false));
      view = initResultatsRechercheCommuneView(searchCommune, model);
    } else {
      view = initRechercheCommuneView(searchCommune, model);
    }
    return view;
  }

  /**
   * Recherche d'une commune via la méthode POST en fonction d'une liste de critères
   * @param searchCommune 
   * @param model
   * @return Vue de la page de recherche ou des résultats
   */
  @RequestMapping(params = "valider", value = "/resultats", method = RequestMethod.POST)
  public String communedisplay(@ModelAttribute("searchCommune") SearchCommune searchCommune,
                               Model model) {
    log.debug("Search for commune with criteria: {}", searchCommune);
    List<CommunePlusWithGenealogie> communes = null;

    if(!(searchCommune.getCodeInsee() == null
        && "-1".equals(searchCommune.getCodeDepartement())
        && "-1".equals(searchCommune.getCodeCirconscription())
        && "-1".equals(searchCommune.getCodeRegion())
        && (searchCommune.getNomEnrichi() == null || searchCommune.getNomEnrichi().isEmpty()))) {
      String dept   = "-1".equals(searchCommune.getCodeDepartement()) ? null : searchCommune.getCodeDepartement();
      String region = "-1".equals(searchCommune.getCodeRegion()) ? null : searchCommune.getCodeRegion();
      String bassin = "-1".equals(searchCommune.getCodeCirconscription()) ? null : searchCommune.getCodeCirconscription();
      communes = communePlusService.getCommuneByCriteria(searchCommune.getCodeInsee(),
                                          dept,
                                          region,
                                          bassin,
                                          searchCommune.getNomEnrichi(),
                                          searchCommune.getDateEffet());
      if(communes == null || communes.isEmpty()){
        model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
      }
    } else {
      model.addAttribute("errorRecherche", "Au moins un des champs doit être renseigné");
    }

    if(communes == null || communes.isEmpty()) {
      return initRechercheCommuneView(searchCommune, model);
    } else {
      searchCommune.setCommunes(communes);
      searchCommune.setPage("1");
      if(communes.size() == 1) {
        CommunePlusWithGenealogie commune = communes.iterator().next();
        Date dateValidite;
        if(searchCommune.getDateEffet() == null) {
          dateValidite = new Date();
        } else {
          dateValidite = searchCommune.getDateEffet();
        }
        return "redirect:/referentiel/commune/"
               + commune.getCommunePlus().getCodeInsee() + "?date="
               + DateConversionUtils.formatDateToStringUrl(dateValidite);
      } else {
        model.addAttribute("searchCommune", searchCommune);
        model.addAttribute("titre", "Liste des résultats");
        return "redirect:/referentiel/commune/resultats?page=1";
      }
    }
  }

  /**
   * Remise à zéro du formulaire de recherche
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(params = "annuler", value = "/resultats", method = RequestMethod.POST)
  public String annulerForm(Model model,
                           @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    searchCommune.reset();
    if(searchCommune.getListeResultats() != null) {
      searchCommune.getListeResultats().clear();
    } else {
      searchCommune.setListeResultats(new ArrayList<>());
    }
    return initRechercheCommuneView(searchCommune, model);
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
  public String communedisplay(@PathVariable("code") String code,
                               @RequestParam(value = "date", required = false) String dateParam,
                               Model model) {
    String view = "communesearch";
    Date dateValidite = null;

    if(dateParam != null) {
      try {
        dateValidite = DateConversionUtils.formatStringToDateUrl(dateParam);
      } catch (ParseException e) {
        log.info("Le format de la date n'est pas valide et doit être yyyy-MM-dd : {}", dateParam);
      }
    }
    log.debug("Display commune: {}", code);
    if (code != null) {
      CommunePlusWithGenealogie commune = communePlusService.getCommuneWithGenealogie(code, dateValidite);
      if(commune != null) {
        Departement departement = departementService.getDepartementByCode(commune.getCommunePlus().getDepartement(), dateValidite);
        Region region = regionService.getRegionByCode(departement.getRegion(), dateValidite);
        DisplayCommune displayCommune = new DisplayCommune(commune, departement, region);
        view = initDetailCommuneView(displayCommune, model);
      } else {
        model.addAttribute("errorRecherche", "La commune recherchée n'existe pas");
      }
    } else {
      view = initRechercheCommuneView(new SearchCommune(), model);
      model.addAttribute("errorRecherche", "La recherche n'a rien retourné");
    }
    return view;
  }

  /**
   * Initialisation de la page d'affichage d'une commune
   * @param displayCommune 
   * @param model
   * @return Vue due la page de recherche
   */
  public String initDetailCommuneView(DisplayCommune displayCommune, 
                                      Model model) {
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
  public String initRechercheCommuneView(SearchCommune searchCommune, Model model){
    if(searchCommune.getDateEffet() == null){
      searchCommune.setDateEffet(new Date());
    }
    searchCommune.setDepartementsByCodeInsee(departementService.getAllDepartement(searchCommune.getDateEffet()));
    searchCommune.setRegionsByCodeInsee(regionService.getAllRegion(searchCommune.getDateEffet()));
    searchCommune.setCirconscriptionByCode(bassinService.getAllBassin());
    model.addAttribute("searchCommune", searchCommune);
    model.addAttribute("titre", "Rechercher une Commune");
    return "communesearch";
  }

  /**
   * Initialisation de la page de résultats
   * @param searchCommune 
   * @param model
   * @return Vue due la page de résultats
   */
  public String initResultatsRechercheCommuneView(SearchCommune searchCommune,
                                                  Model model){
    searchCommune.setListeResultats(paginateResultatsCommune(searchCommune, false));
    model.addAttribute("searchCommune", searchCommune);
    model.addAttribute("titre", "Liste des résultats");
    return "communeresults";
  }

  /**
   * Méthode de pagination des communes
   * @param searchCommune 
   * @param allCommune 
   * @return Liste des communes affichés sur la page des résultats
   */
  public List<DisplayCommune> paginateResultatsCommune(SearchCommune searchCommune, boolean allCommune) {
    List<DisplayCommune> listeResultats = new ArrayList<>();
    int firstCommuneIndex = allCommune ? 0 : searchCommune.getFirstCommuneIndex();
    int lastCommuneIndex = allCommune ? searchCommune.getCommunes().size() : searchCommune.getLastCommuneIndex();

    for(int i = firstCommuneIndex ; i < lastCommuneIndex ; i++) {
      CommunePlusWithGenealogie commune = searchCommune.getCommunes().get(i);
      listeResultats.add(new DisplayCommune(commune));
    }
    return listeResultats;
  }

  /**
   * Renvoie la liste des départements en fonction de la région pour l'affichage de la liste 
   * déroulante "départements" de la page de recherche.
   * @param regionId 
   * @param dateParam
   * @return Tableau associatif comprenant le code insee et le nom de chaque département
   */
  @RequestMapping(value = "/json/deptlist", method = RequestMethod.GET)
  public @ResponseBody Map<String,String> getDepartementByRegion(
          @RequestParam(value = "regionId", required = false) String regionId,
          @RequestParam(value = "date", required = false) String dateParam) {
    Date date = null;
    if (dateParam == null || dateParam.isEmpty()) {
      try {
        date = DateConversionUtils.formatStringToDateUrl(dateParam);
      } catch (ParseException e) {
        log.info("Invalid date format, expected yyyy-MM-dd but was {}", dateParam);
      }
    }
    String region = (regionId == null || regionId.isEmpty() || "-1".equals(regionId) ? null : regionId);
    List<Departement> depts = departementService.getDepartementForRegion(region, date);
    HashMap<String,String> departementByRegion = new HashMap<>();
    for(Departement dept : depts) {
      departementByRegion.put(dept.getCodeInsee(), dept.getNomEnrichi());
    }
    return departementByRegion;
  }

  /**
   * Attribut de session du contrôleur
   * @return Objet de recherche de commune
   */
  @ModelAttribute("searchCommune")
  public SearchCommune searchCommune() {
    return new SearchCommune();
  }

  /**
   * Attribut de session du contrôleur
   * @return Objet de recherche de commune
   */
  @ModelAttribute("entite")
  public SearchEntite searchEntite() {
    return new SearchEntite();
  }
}
