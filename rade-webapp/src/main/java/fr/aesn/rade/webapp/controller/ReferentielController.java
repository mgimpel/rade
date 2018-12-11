/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Marc Gimpel
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

import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.persist.model.Commune;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.webapp.model.SearchCommune;
import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.service.BassinService;
import fr.aesn.rade.webapp.model.DisplayCommune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring MVC Controller for Rade.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
@Controller
@RequestMapping("/referentiel")
public class ReferentielController {
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
   * Region Search mapping.
   * @param code INSEE code for Region.
   * @param model MVC model passed to JSP.
   * @return View for the Login page.
   */
  @RequestMapping(value = "/region", method = RequestMethod.GET)
  public String regionsearch(@RequestParam(value = "code", required = false) String code,
                             Model model) {
    log.info("Search for region: {}", code);
    if (code != null) {
      Region region = regionService.getRegionByCode(code, new Date());
      return (regiondisplay(region, model));
    }
    model.addAttribute("titre", "Recherche Region");
    model.addAttribute("region", new Region());
    return "regionsearch";
  }

  /**
   * Region Search mapping.
   * @param criteria Region search criteria entered in form.
   * @param result binding to Region object result.
   * @param model MVC model passed to JSP.
   * @return View for the Login page.
   */
  @RequestMapping(value = "/region", method = RequestMethod.POST)
  public String regiondisplay(@ModelAttribute("region") Region criteria, 
                              BindingResult result,
                              Model model) {
    log.info("Search for region with criteria: {}", criteria);
    if (result.hasErrors()) {
      return "error";
    }
    Region region = regionService.getRegionByCode(criteria.getCodeInsee(), new Date());
    return (regiondisplay(region, model));
  }

  /**
   * Region Search mapping.
   * @param code INSEE code for Region.
   * @param model MVC model passed to JSP.
   * @return View for the Login page.
   */
  @RequestMapping(value = "/region/{code}")
  public String regiondisplay(@PathVariable("code") String code, 
                              Model model) {
    log.info("Display region: {}", code);
    if (code != null) {
      Region region = regionService.getRegionByCode(code, new Date());
      if (region != null) {
        return (regiondisplay(region, model));
      }
    }
    return "redirect:/referentiel/region";
  }

  /**
   * @param region the region to display.
   * @param model the Spring MVC model.
   * @return View for the region display page.
   */
  public String regiondisplay(Region region, 
                              Model model) {
    model.addAttribute("titre", "Region");
    model.addAttribute("region", region);
    return "regiondisplay";
  }
  
  
  /**
   * Commune Search mapping.
   * @return View for the Login page.
   */
  @RequestMapping(value = "/commune", method = RequestMethod.GET)
  public String communesearch(@RequestParam(value = "code", required = false) String code,
                             Model model) {
    log.info("Search for commune: {}", code);
    if (code != null) {
        return (communedisplay(code, model));
    }    
    SearchCommune searchCommune = new SearchCommune();
    searchCommune.setDateEffet(new Date());
    model.addAttribute("titre", "Recherche Commune");
    model.addAttribute("searchCommune", searchCommune); 
    List<Region> regions = regionService.getAllRegion();
    List<Departement> departements = departementService.getAllDepartement();
    List<CirconscriptionBassin> bassins = bassinService.getAllBassin();
        HashMap<String, String> regionsByCodeInsee = new HashMap<>();
        for(Region r : regions){
            regionsByCodeInsee.put(r.getCodeInsee(), r.getNomEnrichi());
        }        
        HashMap<String, String> departementsByCodeInsee = new HashMap<>();
        for(Departement d : departements){
            departementsByCodeInsee.put(d.getCodeInsee(), d.getNomEnrichi());
        }

        Collections.sort(bassins, new Comparator<CirconscriptionBassin>(){
            @Override
            public int compare(CirconscriptionBassin c1, CirconscriptionBassin c2) {
                return c1.getLibelleLong().compareTo(c2.getLibelleLong());
            }

        });
    model.addAttribute("listeRegions", regionsByCodeInsee);
    model.addAttribute("listeDepartements", departementsByCodeInsee);
    model.addAttribute("listeCirconscriptions", bassins);
    model.addAttribute("dateEffet", new Date());
    return "communesearch";
  }

  @RequestMapping(value = "/commune", method = RequestMethod.POST)
  public String communedisplay(@ModelAttribute("searchCommune") SearchCommune criteria, 
                              BindingResult result,
                              Model model) {
    log.info("Search for commune with criteria: {}", criteria);
    if (result.hasErrors()) {
      return "error";
    }
    
    String view = "communesearch";
    
    // 1. recherche de la commune
    List<Commune> communes = null;
    
    if(!(criteria.getCodeInsee() == null 
            && criteria.getCodeDepartement() == "-1" 
            && criteria.getCodeCirconscription() == "-1" 
            && criteria.getCodeRegion()  == "-1" 
            && (criteria.getNomEnrichi() == null || criteria.getNomEnrichi().equals("")) 
            && criteria.getDateEffet() == null)){
        communes = communeService.getCommuneByCriteria(criteria.getCodeInsee(), 
                                                       criteria.getCodeDepartement(), 
                                                       criteria.getCodeRegion(), 
                                                       criteria.getCodeCirconscription(), 
                                                       criteria.getNomEnrichi(), 
                                                       criteria.getDateEffet());
        if(communes == null || communes.size() == 0){
            model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
        }
    }else{
        model.addAttribute("errorRecherche", "Au moins un des champs doit être renseigné"); 
    }
    

//    List<Commune> communes = null;
//    if(criteria.getCodeInsee() != null && !criteria.getCodeInsee().equals("")){
//        if(criteria.getDateEffet() != null){
//            Commune commune = communeService.getCommuneByCode(criteria.getCodeInsee(), criteria.getDateEffet());
//            if(commune != null){
//                communes = new ArrayList<Commune>();
//                communes.add(commune);
//            }
//        }else{
//            communes = communeDao.findByCodeInsee(criteria.getCodeInsee());
//        }
//    }else{
//        String codeDepartement = criteria.getCodeDepartement().equals("-1") ? " " : criteria.getCodeDepartement();
//        String codeRegion = criteria.getCodeRegion().equals("-1") ? " " : criteria.getCodeRegion();
//        String codeBassin = criteria.getCodeCirconscription().equals("-1") ? " " : criteria.getCodeCirconscription();
//        String nameLike = criteria.getNomEnrichi() == null ||  criteria.getNomEnrichi().trim().equals("") ? " " : criteria.getNomEnrichi();
//        
//        if(!(codeDepartement.equals(" ") && codeRegion.equals(" ") && codeBassin.equals(" ") && nameLike.equals(" ") && criteria.getDateEffet() == null)){
//            if(criteria.getDateEffet() == null){
//                 if(codeRegion.equals(" ") || !codeDepartement.equals(" ") ){
//                     log.info("Recherche par dept, bassin et namelike : " + codeDepartement +' ' + codeBassin + ' ' + nameLike.trim());
//                        communes = communeDao.findByDeptBassinAndNameLike(codeDepartement,codeBassin, nameLike.trim());
//                 }else{
//                     log.info("Recherche par bassin, region et namelike : " + codeBassin +' ' + codeRegion + ' ' + nameLike.trim());
//                        communes = communeDao.findByBassinRegionAndNameLike(codeBassin,codeRegion, nameLike.trim());
//                 }
//             }else{
//                 if(codeRegion.equals(" ")){
//                     if(codeBassin.equals(" ")){
//                         if(codeDepartement.equals(" ")){
//                             log.info("Recherche par dept, namelike et date d'effet : " + codeDepartement +' ' + nameLike.trim() + ' ' + criteria.getDateEffet());
//                             communes = communeDao.findByNameLikeValidOnDate(nameLike.trim(),criteria.getDateEffet());
//                         }else{
//                             if(nameLike.equals(" ")){
//                                 log.info("Recherche par dept et date d'effet : " + codeDepartement +' ' + nameLike.trim() + ' ' + criteria.getDateEffet());
//                                 communes = communeDao.findByDepartementValidOnDate(codeDepartement,criteria.getDateEffet());
//                             }else{
//                                 log.info("Recherche par dept, namelike et date d'effet : " + codeDepartement +' ' + nameLike.trim() + ' ' + criteria.getDateEffet());
//                                 communes = communeDao.findByDepartementAndNameLikeValidOnDate(codeDepartement,nameLike.trim(),criteria.getDateEffet());
//                             }
//                         }
//                     }else{
//                         log.info("Recherche par dept, bassin, nameLike et date d'effet : " + codeDepartement + " " + codeBassin +' ' + nameLike.trim() + ' ' + criteria.getDateEffet());
//                         communes = communeDao.findByDeptBassinAndNameLikeValidOnDate(codeDepartement,codeBassin,nameLike.trim(),criteria.getDateEffet());
//                     }
//                 }else{
//                     log.info("Recherche par dept, bassin, region, namelike et date d'effet : " + nameLike.trim() +' ' + codeBassin + " " + codeRegion + ' ' + criteria.getDateEffet());
//                     communes = communeDao.findByBassinRegionAndNameLikeValidOnDate(codeBassin, codeRegion,nameLike.trim(), criteria.getDateEffet());
//                 }
//             }
//            if(communes == null || communes.size() == 0){
//                 model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
//            }
//        }else{
//            model.addAttribute("errorRecherche", "Au moins un des champs doit être renseigné");
//        }
//    }

    // 2. redirection et construction du model en fonction du resultat
    if(communes == null || communes.size() == 0){
        model.addAttribute("searchCommune", criteria);
        List<Region> regions = regionService.getAllRegion();
        List<Departement> departements = departementService.getAllDepartement();
        List<CirconscriptionBassin> bassins = bassinService.getAllBassin();

        HashMap<String, String> regionsByCodeInsee = new HashMap<>();
        for(Region r : regions){
            regionsByCodeInsee.put(r.getCodeInsee(), r.getNomEnrichi());
        }        

        HashMap<String, String> departementsByCodeInsee = new HashMap<>();
        for(Departement d : departements){
            departementsByCodeInsee.put(d.getCodeInsee(), d.getNomEnrichi());
        }

        Collections.sort(bassins, new Comparator<CirconscriptionBassin>(){
            @Override
            public int compare(CirconscriptionBassin c1, CirconscriptionBassin c2) {
                return c1.getLibelleLong().compareTo(c2.getLibelleLong());
            }

        });
        SearchCommune searchCommune = new SearchCommune();
        searchCommune.setDateEffet(new Date());
        model.addAttribute("listeRegions", regionsByCodeInsee);
        model.addAttribute("listeDepartements", departementsByCodeInsee);
        model.addAttribute("listeCirconscriptions", bassins);
        model.addAttribute("searchCommune", searchCommune);
        model.addAttribute("titre", "Recherche Commune");
        model.addAttribute("searchCommune", criteria);
    }else{
        if(communes.size() == 1){
            // un seul résultat : affichage de la fiche commune
            Commune commune = communes.iterator().next();
            view = communedisplay(commune.getCodeInsee(), commune.getDebutValidite().toString(), model);
        }else{
            // plusieurs résultats : affichage de la liste des résultats
            List<DisplayCommune> listeResultats = new ArrayList();
            for(Commune commune : communes){
                DisplayCommune displayCommune = new DisplayCommune();
                displayCommune.setDepartementService(departementService);
                displayCommune.setCommuneService(communeService);
                displayCommune.setRegionService(regionService);
                displayCommune.setNomEnrichi(commune.getNomEnrichi());
                displayCommune.setCodeInsee(commune.getCodeInsee());
                displayCommune.setDebutValidite(commune.getDebutValidite());
                displayCommune.setFinValidite(commune.getFinValidite());
                displayCommune.setParents(commune.getParents());
                if(displayCommune.getParents().size() > 0){
                    displayCommune.setMotifModification(displayCommune.getParents().iterator().next().getTypeGenealogie().getLibelleLong());
                }
                listeResultats.add(displayCommune);
            }
            criteria.setListeResultats(listeResultats);
            criteria.setPage("1");
            model.addAttribute("titre", "Liste des résultats");
            model.addAttribute("searchResult", criteria);
            view = "communeresults";
        }
    }
    
    return view;
  }

  @RequestMapping(value = "/commune/{code}")
  public String communedisplay(@PathVariable("code") String code, 
                              Model model) {
    String view = "communesearch";
    log.info("Display commune: {}", code);
    if (code != null) {        
        List<Commune> communes = communeService.getCommuneByCode(code);
        
        if(communes != null && communes.size() > 0){
            
            if(communes.size() == 1){
                view = communedisplay(communes.get(0).getCodeInsee(), communes.get(0).getDebutValidite().toString(), model);
            }else{
                SearchCommune searchCommune = new SearchCommune();
                searchCommune.setCodeInsee(code);
                List<DisplayCommune> listeResultats = new ArrayList<>();
                
                for(Commune commune : communes){
                    DisplayCommune displayCommune = new DisplayCommune();
                    displayCommune.setDepartementService(departementService);
                    displayCommune.setCommuneService(communeService);
                    displayCommune.setRegionService(regionService);
                    displayCommune.setNomEnrichi(commune.getNomEnrichi());
                    displayCommune.setCodeInsee(commune.getCodeInsee());
                    displayCommune.setDebutValidite(commune.getDebutValidite());
                    displayCommune.setFinValidite(commune.getFinValidite());
                    displayCommune.setParents(commune.getParents());
                    if(displayCommune.getParents().size() > 0){
                        displayCommune.setMotifModification(displayCommune.getParents().iterator().next().getTypeGenealogie().getLibelleLong());
                    }
                    listeResultats.add(displayCommune);
                    
                }
                searchCommune.setListeResultats(listeResultats);
                model.addAttribute("searchCommune", searchCommune);
                model.addAttribute("titre", "Liste des résultats");
                view = "communeresults";
            }  
        }    
    }else{
        model.addAttribute("errorRecherche", "La recherche n'a rien retourné");
        model.addAttribute("searchCommune", new SearchCommune());
        view = "communesearch";
    }
    
    return view;
  }
  
  @RequestMapping(value = "/commune/{code}/{date}")
  public String communedisplay(@PathVariable("code") String code, @PathVariable("date") String date,
                              Model model) {
    String view = "communesearch";
      CommunePlus communePlus = communePlusService.getCommuneByCode(code, date);
      Commune communeInsee = communePlus.getCommuneInsee();
      log.info("Commune + date " + communeInsee);
    if(communeInsee != null){
        log.info("Display commune: {}", code);
        DisplayCommune displayCommune = new DisplayCommune();
        displayCommune.setDepartementService(departementService);
        displayCommune.setCommuneService(communeService);
        displayCommune.setRegionService(regionService);
        displayCommune.setCodeInsee(communeInsee.getCodeInsee());
        displayCommune.setNomEnrichi(communeInsee.getNomEnrichi());
        displayCommune.setNomMajuscule(communeInsee.getNomMajuscule());
        displayCommune.setDebutValidite(communeInsee.getDebutValidite());
        displayCommune.setFinValidite(communeInsee.getFinValidite());
        displayCommune.setArticle(communeInsee.getTypeNomClair().getArticle());
        displayCommune.setArticleEnrichi(communeInsee.getArticleEnrichi());
        displayCommune.setEnfants(communeInsee.getEnfants());
        displayCommune.setParents(communeInsee.getParents());
        if(displayCommune.getParents().size() > 0){
            GenealogieEntiteAdmin parent = displayCommune.getParents().iterator().next();
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
        
        view = communedisplay(displayCommune,  model);
    }else{
        List<Region> regions = regionService.getAllRegion();
        List<Departement> departements = departementService.getAllDepartement();
        List<CirconscriptionBassin> bassins = bassinService.getAllBassin();
        Collections.sort(regions, new Comparator<Region>(){
            @Override
            public int compare(Region r1, Region r2) {
                return r1.getNomEnrichi().compareTo(r2.getNomEnrichi());
            }

        });

        Collections.sort(departements, new Comparator<Departement>(){
            @Override
            public int compare(Departement d1, Departement d2) {
                return d1.getNomEnrichi().compareTo(d2.getNomEnrichi());
            }

        });

        Collections.sort(bassins, new Comparator<CirconscriptionBassin>(){
            @Override
            public int compare(CirconscriptionBassin c1, CirconscriptionBassin c2) {
                return c1.getLibelleLong().compareTo(c2.getLibelleLong());
            }

        });
        SearchCommune searchCommune = new SearchCommune();
        searchCommune.setDateEffet(new Date());
        model.addAttribute("listeRegions", regions);
        model.addAttribute("listeDepartements", departements);
        model.addAttribute("listeCirconscriptions", bassins);
        model.addAttribute("searchCommune", searchCommune);
        model.addAttribute("errorRecherche", "La recherche n'a donné aucun résultat.");
        view = "communesearch";
    }

    return view;
  }

  public String communedisplay(DisplayCommune displayCommune, 
                              Model model) {
    model.addAttribute("titre", "Commune / Détail commune " + displayCommune.getCodeInsee() + " " + displayCommune.getNomEnrichi() + "");
    model.addAttribute("displayCommune", displayCommune);
    return "communedisplay";
  }
  
  @RequestMapping(value = "/commune/export", method = RequestMethod.GET)
    public ModelAndView getExcel() {
            return new ModelAndView("ExcelTest");
    }
}
