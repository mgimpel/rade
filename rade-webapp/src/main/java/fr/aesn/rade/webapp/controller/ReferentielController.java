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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Spring MVC Controller for Rade.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Controller
@RequestMapping("/referentiel")
public class ReferentielController {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(ReferentielController.class);
  /** Service. */
  @Autowired
  private RegionService regionService;

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
}
