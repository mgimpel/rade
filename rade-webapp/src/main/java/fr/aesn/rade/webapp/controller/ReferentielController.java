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

import fr.aesn.rade.common.util.DateConversionUtils;
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
import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.webapp.model.SearchEntite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Spring MVC Controller for Rade.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
@Controller
@RequestMapping("/referentiel")
@SessionAttributes({"searchCommune", "entite"})
public class ReferentielController {
  /** Service. */
  @Autowired
  private RegionService regionService;

  public final static String REGION  = "region";
  public final static String DEPT    = "dept";
  public final static String COMMUNE = "commune";
  public final static String BASSIN  = "bassin"; 

  /**
   * Entite search mapping
   * Recherche d'entité (commune, région, département, bassin, délégation) par code
   * @param entite
   * @param model
   * @return redirect to the suitable entity search
   */
  @RequestMapping("/entiteSearch")
  public String entiteSearch(@ModelAttribute("entite") SearchEntite entite, Model model) {
    log.info("Recherche d'entités, type : " + entite.getType() + ", code :" + entite.getCode());
    String view = "home";
    if(entite.getCode() != null && !entite.getCode().isEmpty()){
      // une fois la combo des types dégrisée, décommenter la ligne suivante
      String type = "commune"; // entite.getType();
      if(COMMUNE.equalsIgnoreCase(type)) {
        view = "redirect:/referentiel/commune/" + entite.getCode() + "?date=" + DateConversionUtils.formatDateToStringUrl(new Date());
      }
      model.addAttribute("entite", new SearchEntite());
    }
    return view;
  }

  /**
   * Region Search mapping.
   * @param code INSEE code for Region.
   * @param model MVC model passed to JSP.
   * @return View for the Login page.
   */
  @RequestMapping(value = "/region", method = RequestMethod.GET)
  public String regionsearch(@RequestParam(value = "code", required = false) String code,
                             Model model) {
    log.debug("Search for region: {}", code);
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
    log.debug("Search for region with criteria: {}", criteria);
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
    log.debug("Display region: {}", code);
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
   * Attribut de session du contrôleur
   * @return Objet de recherche de commune
   */
  @ModelAttribute("entite")
  public SearchEntite searchEntite() {
    return new SearchEntite();
  }
}
