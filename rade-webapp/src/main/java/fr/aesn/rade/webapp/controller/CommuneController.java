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
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
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
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
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
    export.exportCommune(response, searchCommune);
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
   * @param criteria 
   * @param model
   * @return Vue de la page de recherche ou des résultats
   */
  @RequestMapping(value = "/resultats", method = RequestMethod.POST)
  public String communedisplay(@ModelAttribute("searchCommune") SearchCommune criteria,
                               Model model) {
    log.debug("Search for commune with criteria: {}", criteria);

    String view = "communesearch";

    List<Commune> communes = null;

    if(!(criteria.getCodeInsee() == null
        && criteria.getCodeDepartement().equals("-1")
        && criteria.getCodeCirconscription().equals("-1")
        && criteria.getCodeRegion().equals("-1")
        && (criteria.getNomEnrichi() == null || criteria.getNomEnrichi().equals(""))
        && criteria.getDateEffet() == null)){
      communes = communeService.getCommuneByCriteria(criteria.getCodeInsee(),
          criteria.getCodeDepartement(),
          criteria.getCodeCirconscription(),
          criteria.getCodeRegion(),
          criteria.getNomEnrichi(),
          criteria.getDateEffet());
      if(communes == null || communes.isEmpty()){
        model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
      }
    }else{
      model.addAttribute("errorRecherche", "Au moins un des champs doit être renseigné");
    }

    if(communes == null || communes.isEmpty()){
      view = initRechercheCommuneView(criteria, model);
    }else{
      if(communes.size() == 1){
        Commune commune = communes.iterator().next();
        Date date = new Date();
        if(commune.getFinValidite() != null){
          date.setTime(commune.getFinValidite().getTime() - 86400000);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        view = communedisplay(commune.getCodeInsee(), sdf.format(date), model);
      }else{
        criteria.setPage("1");
        criteria.setCommunes(communes);
        view = initResultatsRechercheCommuneView(criteria, model);
      }
    }

    return view;
  }


  /**
   * Recherche d'une commune via son code Insee
   * @param code 
   * @param model
   * @return Vue de la page de recherche ou des résultats en fonction du nombre 
   * de résultats
   */
  @RequestMapping(value = "/{code}")
  public String communedisplay(@PathVariable("code") String code, 
                               Model model) {
    String view = "communesearch";
    log.debug("Display commune: {}", code);
    if (code != null) {
      List<Commune> communes = communeService.getCommuneByCode(code);

      if(communes != null && communes.size() > 0){
        if(communes.size() == 1){
          view = communedisplay(communes.get(0).getCodeInsee(),
              communes.get(0).getDebutValidite().toString(), model);
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
   * Recherche d'une commune via son code Insee et sa date de validité
   * @param code 
   * @param date Date de validité de la commune
   * @param model
   * @return Vue de la page de recherche de communes avec message d'erreur ou du détail 
   * de la commune si elle existe
   */
  @RequestMapping(value = "/{code}/{date}")
  public String communedisplay(@PathVariable("code") String code, 
                               @PathVariable("date") String date,
                               Model model) {
    String view = "communesearch";

    CommunePlus communePlus = communePlusService.getCommuneByCode(code, date);

    if(communePlus != null){
      Commune communeInsee = communePlus.getCommuneInsee();

      if(communeInsee != null){
        DisplayCommune displayCommune = new DisplayCommune();
        displayCommune.setCodeInsee(communeInsee.getCodeInsee());
        displayCommune.setNomEnrichi(communeInsee.getNomEnrichi());
        displayCommune.setNomMajuscule(communeInsee.getNomMajuscule());
        displayCommune.setDebutValidite(communeInsee.getDebutValidite());
        displayCommune.setFinValidite(communeInsee.getFinValidite());
        displayCommune.setArticle(communeInsee.getTypeNomClair().getArticle());
        displayCommune.setArticleEnrichi(communeInsee.getArticleEnrichi());
        displayCommune.setGenealogieParentCodeInsee(new HashMap<>());

        for(GenealogieEntiteAdmin genealogieParent : communeInsee.getParents()){
          displayCommune.setMotifModification(genealogieParent.getTypeGenealogie().getLibelleLong());
          displayCommune.setCommentaireModification(genealogieParent.getCommentaire());
          String codeInsee = null;
          switch(genealogieParent.getParentEnfant().getParent().getTypeEntiteAdmin().getCode()){
          case "COM":
            codeInsee = communeService.getCommuneById(genealogieParent.getParentEnfant().getParent().getId()).getCodeInsee();
            break;
          case "REG":
            codeInsee = regionService.getRegionById(genealogieParent.getParentEnfant().getParent().getId()).getCodeInsee();
            break;
          case "DEP":
            codeInsee = departementService.getDepartementById(genealogieParent.getParentEnfant().getParent().getId()).getCodeInsee();
          }
          displayCommune.getGenealogieParentCodeInsee().put(genealogieParent, codeInsee);
        }

        displayCommune.setGenealogieEnfantCodeInsee(new HashMap<>());
        for(GenealogieEntiteAdmin genealogieEnfant : communeInsee.getEnfants()){
          String codeInsee = null;
          switch(genealogieEnfant.getParentEnfant().getEnfant().getTypeEntiteAdmin().getCode()){
          case "COM":
            codeInsee = communeService.getCommuneById(genealogieEnfant.getParentEnfant().getEnfant().getId()).getCodeInsee();
            break;
          case "REG":
            codeInsee = regionService.getRegionById(genealogieEnfant.getParentEnfant().getEnfant().getId()).getCodeInsee();
            break;
          case "DEP":
            codeInsee = departementService.getDepartementById(genealogieEnfant.getParentEnfant().getEnfant().getId()).getCodeInsee();
          }
          displayCommune.getGenealogieEnfantCodeInsee().put(genealogieEnfant, codeInsee);
        }

        if(communeInsee.getParents().size() > 0){
          GenealogieEntiteAdmin parent = communeInsee.getParents().iterator().next();
          displayCommune.setMotifModification(parent.getTypeGenealogie().getLibelleLong());
          displayCommune.setCommentaireModification(parent.getCommentaire());
        }

        CommuneSandre communeSandre = communePlus.getCommuneSandre();
        if(communeSandre != null){
          displayCommune.setNomBassin(communeSandre.getCirconscriptionBassin().getLibelleLong());
          displayCommune.setDateCreation(communeSandre.getDateCreationCommune());
          displayCommune.setDateModification(communeSandre.getDateMajCommune());
          displayCommune.setCodeBassin(communeSandre.getCirconscriptionBassin().getCode());
        }

        Departement departement = departementService.getDepartementByCode(communeInsee.getDepartement(), communeInsee.getDebutValidite());
        displayCommune.setNomDepartement(departement.getNomEnrichi());
        displayCommune.setCodeDepartement(departement.getCodeInsee());
        displayCommune.setNomRegion(regionService.getRegionByCode(departement.getRegion(), communeInsee.getDebutValidite()).getNomEnrichi());

        view = initDetailCommuneView(displayCommune,  model);
      }else{
        model.addAttribute("errorRecherche", "La recherche n'a rien retourné");
        view = initRechercheCommuneView(new SearchCommune(), model);
      }
    }else{
      model.addAttribute("errorRecherche", "La recherche n'a rien retourné");
      view = initRechercheCommuneView(new SearchCommune(), model);
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
    StringBuilder sb = new StringBuilder();
    sb.append("Commune / Détail commune ");
    sb.append(displayCommune.getCodeInsee());
    sb.append(" ");
    sb.append(displayCommune.getNomEnrichi());
    model.addAttribute("titre", sb.toString());
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
      Commune commune = searchCommune.getCommunes().get(i);
      Commune communed = communeService.getCommuneById(commune.getId());

      DisplayCommune displayCommune = new DisplayCommune();
      displayCommune.setNomEnrichi(communed.getNomEnrichi());
      displayCommune.setCodeInsee(communed.getCodeInsee());
      displayCommune.setDebutValidite(communed.getDebutValidite());
      displayCommune.setFinValidite(communed.getFinValidite());

      displayCommune.setGenealogieParentCodeInsee(new HashMap<>());

      for(GenealogieEntiteAdmin genealogieParent : communed.getParents()){
        displayCommune.setMotifModification(genealogieParent.getTypeGenealogie().getLibelleLong());
        String codeInsee = null;
        EntiteAdministrative entiteAdmin = genealogieParent.getParentEnfant().getParent();
        switch(genealogieParent.getParentEnfant().getParent().getTypeEntiteAdmin().getCode()){
        case "COM":
          codeInsee = communeService.getCommuneById(entiteAdmin.getId()).getCodeInsee();
          break;
        case "REG":
          codeInsee = regionService.getRegionById(entiteAdmin.getId()).getCodeInsee();
          break;
        case "DEP":
          codeInsee = departementService.getDepartementById(entiteAdmin.getId()).getCodeInsee();
        }
        displayCommune.getGenealogieParentCodeInsee().put(genealogieParent, codeInsee);
      }

      listeResultats.add(displayCommune);
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
