/*
 * Copyright (C) 2019 sophie.belin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.aesn.rade.webapp.controller;

import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.common.util.DateConversionUtils;
import fr.aesn.rade.common.util.StringConversionUtils;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.service.BassinService;
import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.CommuneService;
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
import javax.servlet.http.HttpServletRequest;
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
  /** Service. */
  @Autowired
  private RegionService regionService;
  @Autowired
  private CommuneService communeService;
  @Autowired
  private DepartementService departementService;
  @Autowired
  private BassinService bassinService;
  @Autowired
  private CommunePlusService communePlusService;
  private final String DEFAULT_EXPORT_FILENAME = "export-communes";

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
      export.exportCommune(out, searchCommune.getListeResultats());
      out.flush();
      out.close();
    } catch (IOException ex) {
      log.info("Echec lors de l'export au format excel", ex.getMessage());
    }
  }

  /**
   * Appel de la page de recherche de commune.
   * @param request 
   * @param model
   * @param searchCommune 
   * @return Vue correspondant à liste des résultats
   */
  @RequestMapping(method = RequestMethod.GET)
  public String communesearch(HttpServletRequest request, 
                              Model model,
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
    if(searchCommune.getCommunes() != null && searchCommune.getCommunes().size() > 0){
      searchCommune.setListeResultats(paginateResultatsCommune(searchCommune, false));
      view = initResultatsRechercheCommuneView(searchCommune, model);
    }else{
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
  @RequestMapping(value = "/resultats", method = RequestMethod.POST)
  public String communedisplay(@ModelAttribute("searchCommune") SearchCommune searchCommune,
                               Model model) {
    log.debug("Search for commune with criteria: {}", searchCommune);

    String view = "communesearch";

    List<CommunePlus> communes = null;

    if(!(searchCommune.getCodeInsee() == null
        && searchCommune.getCodeDepartement().equals("-1")
        && searchCommune.getCodeCirconscription().equals("-1")
        && searchCommune.getCodeRegion().equals("-1")
        && (searchCommune.getNomEnrichi() == null || searchCommune.getNomEnrichi().equals(""))
        && searchCommune.getDateEffet() == null)){
        
      String codeDepartement = searchCommune.getCodeDepartement().equals("-1") ? null : searchCommune.getCodeDepartement();
      String codeRegion = searchCommune.getCodeRegion().equals("-1") ? null : searchCommune.getCodeRegion();
      String codeBassin =  searchCommune.getCodeCirconscription().equals("-1") ? null : searchCommune.getCodeCirconscription();
        
      communes = communePlusService.getCommuneByCriteria(searchCommune.getCodeInsee(),
          codeDepartement,
          codeBassin,
          codeRegion,
          searchCommune.getNomEnrichi(),
          searchCommune.getDateEffet());
      
      if(communes == null || communes.isEmpty()){
        model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
      }
    }else{
      model.addAttribute("errorRecherche", "Au moins un des champs doit être renseigné");
    }

    if(communes == null || communes.isEmpty()){
      view = initRechercheCommuneView(searchCommune, model);
    }else{
      if(communes.size() == 1){
        CommunePlus commune = communes.iterator().next();
        Date date = new Date();
        if(commune.getFinValiditeCommuneInsee() != null){
          date.setTime(commune.getFinValiditeCommuneInsee().getTime() - 86400000);
        }
        return "redirect:/referentiel/commune/" + commune.getCodeInsee() + "?date=" + DateConversionUtils.formatDateToStringUrl(date);
      }else{
        searchCommune.setPage("1");
        searchCommune.setCommunes(communes);
        view = initResultatsRechercheCommuneView(searchCommune, model);
      }
    }

    return view;
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
  public String communedisplay(@PathVariable("code") String code, @RequestParam(value = "date", required = false) String dateParam, 
                               Model model) {
    String view = "communesearch";
    
    Date dateValidite = null;

    if(dateParam != null){
        try {
             dateValidite = DateConversionUtils.formatStringToDateUrl(dateParam);
        } catch (ParseException ex) {
            log.info("Erreur : le format de la date n'est pas valide et doit être yyyy-MM-dd : " + dateParam, ex.toString());
        }
    }
    log.debug("Display commune: {}", code);
    if (code != null) {
      List<CommunePlus> communes = communePlusService.getCommuneByCriteria(code, null,null,null,null,dateValidite);

      if(communes != null && communes.size() > 0){
        if(communes.size() == 1){
          DisplayCommune displayCommune = new DisplayCommune(communes.get(0), communeService, departementService, regionService);
          view = initDetailCommuneView(displayCommune, model);
          
        }else{
          SearchCommune searchCommune = new SearchCommune();
          searchCommune.setCodeInsee(code);
          searchCommune.setCommunes(communes);
          searchCommune.setPage("1");
          view = initResultatsRechercheCommuneView(searchCommune, model);
        }
      }else{
        model.addAttribute("errorRecherche", "La commune recherchée n'existe pas");
      }
    }else{
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
                                      Model model){
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
    searchCommune.setDepartementsByCodeInsee(departementService.getAllDepartement());
    searchCommune.setRegionsByCodeInsee(regionService.getAllRegion());
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
  public List<DisplayCommune> paginateResultatsCommune(SearchCommune searchCommune, boolean allCommune){
    List<DisplayCommune> listeResultats = new ArrayList();
    int firstCommuneIndex = allCommune ? 0 : searchCommune.getFirstCommuneIndex();
    int lastCommuneIndex = allCommune ? searchCommune.getCommunes().size() : searchCommune.getLastCommuneIndex();

    for(int i = firstCommuneIndex ; i < lastCommuneIndex ; i++){
      CommunePlus communePlus = searchCommune.getCommunes().get(i);
      listeResultats.add(new DisplayCommune(communePlus, communeService, departementService, regionService));
    }
    return listeResultats;
  }

  /**
   * Renvoie la liste des départements en fonction de la région.
   * @param regionId 
   * @param searchCommune
   * @return Tableau associatif comprenant le code insee et le nom de chaque département
   */
  @RequestMapping(value = "/dep/{regionId}", method = RequestMethod.GET)
  public @ResponseBody  HashMap<String,String> getDepartementByRegion(
      @PathVariable("regionId") String regionId,
      @ModelAttribute("searchCommune") SearchCommune searchCommune) {
    List<Departement> listeDepartement = searchCommune.getDepartements();
    HashMap<String,String> departementByRegion = new HashMap<>();

    for(Departement d : listeDepartement){
      if(d.getRegion().equals(regionId) || regionId.equals("-1")){
        departementByRegion.put(d.getCodeInsee(), d.getNomEnrichi());
      }
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
