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
   * Session Attribute Bean for Search Parameters and Result.
   * @return Session Attribute Bean for Search Parameters and Result.
   */
  @ModelAttribute("searchCommune")
  private SearchCommune searchCommune() {
    return new SearchCommune();
  }

  /**
   * Get the Search Form. 
   * @param model MVC model passed to JSP.
   * @param searchCommune search details for the model.
   * @return the corresponding view.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getSearchForm(final Model model,
                              @ModelAttribute("searchCommune") final SearchCommune searchCommune) {
    return viewCommuneSearch(model, searchCommune);
  }

  /**
   * Reset the search form.
   * @param model MVC model passed to JSP.
   * @param searchCommune search details for the model.
   * @return redirect back to the search form.
   */
  @RequestMapping(params = "annuler", value = "/resultats", method = RequestMethod.POST)
  public String resetSearchForm(final Model model,
                                @ModelAttribute("searchCommune") final SearchCommune searchCommune) {
    searchCommune.reset();
    return "redirect:/referentiel/commune";
  }

  /**
   * Submit (POST) the search form.
   * @param model MVC model passed to JSP.
   * @param searchCommune search details for the model.
   * @return the corresponding view or redirect appropriately.
   */
  @RequestMapping(params = "valider", value = "/resultats", method = RequestMethod.POST)
  public String submitSearchForm(final Model model,
                                 @ModelAttribute("searchCommune") final SearchCommune searchCommune) {
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
    searchCommune.setPage(1);
    if(communes.size() == 1) {
      CommunePlusWithGenealogie commune = communes.get(0);
      Date date = searchCommune.getDateEffet();
      return "redirect:/referentiel/commune/"
             + commune.getCommunePlus().getCodeInsee()
             + (date == null ? "" : "?date=" + DateConversionUtils.toUrlString(date));
    } else {
      return "redirect:/referentiel/commune/resultats?page=1";
    }
  }

  /**
   * Get the search result list.
   * @param model MVC model passed to JSP.
   * @param searchCommune search details for the model.
   * @return the corresponding view or redirect appropriately.
   */
  @RequestMapping(value = "/resultats", method = RequestMethod.GET)
  public String getResultList(final Model model,
                              @ModelAttribute("searchCommune") final SearchCommune searchCommune) {
    if(searchCommune.getCommunes() != null && searchCommune.getCommunes().size() > 1) {
      return viewCommuneResults(model, searchCommune);
    } else {
      return "redirect:/referentiel/commune";
    }
  }

  /**
   * Get the Commune display page.
   * @param model MVC model passed to JSP.
   * @param code Code INSEE de la commune affichée
   * @param dateParam Date de validité de la commune passée en tant que paramètre optionnel 
   * @param searchCommune search details for the model.
   * @return the corresponding view.
   */
  @RequestMapping(value = "/{code}")
  public String getDisplayPage(final Model model,
                               @PathVariable("code") final String code,
                               @RequestParam(value = "date", required = false) final String dateParam,
                               @ModelAttribute("searchCommune") final SearchCommune searchCommune) {
    Date dateValidite = DateConversionUtils.urlStringToDate(dateParam, new Date());
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
   * Setup model and return view for the search page.
   * @param model MVC model passed to JSP.
   * @param searchCommune search details for the model.
   * @return View for the search page
   */
  private String viewCommuneSearch(final Model model,
                                   final SearchCommune searchCommune) {
    model.addAttribute("titre", "Rechercher une Commune");
    model.addAttribute("searchCommune", searchCommune);
    return "communesearch";
  }

  /**
   * Setup model and return view for the result page.
   * @param model MVC model passed to JSP.
   * @param searchCommune search details for the model.
   * @return View for the result page
   */
  private String viewCommuneResults(final Model model,
                                    final SearchCommune searchCommune) {
    model.addAttribute("titre", "Liste des résultats");
    model.addAttribute("searchCommune", searchCommune);
    return "communeresults";
  }

  /**
   * Setup model and return view for the display page.
   * @param model MVC model passed to JSP.
   * @param displayCommune entity details for the model.
   * @return View for the display page
   */
  private String viewCommuneDisplay(final Model model,
                                    final DisplayCommune displayCommune) {
    model.addAttribute("titre", "Commune / Détail commune "
                                 + displayCommune.getCodeInsee()
                                 + " "
                                 + displayCommune.getNomEnrichi());
    model.addAttribute("displayCommune", displayCommune);
    return "communedisplay";
  }

  /**
   * Export de la liste des communes au format Excel
   * @param response Servlet Response Object.
   * @param searchCommune Objet contenant la recherche
   */
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public void exportExcel(final HttpServletResponse response,
                          @ModelAttribute("searchCommune") final SearchCommune searchCommune) {
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
