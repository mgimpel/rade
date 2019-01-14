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

import fr.aesn.rade.webapp.model.SearchEntite;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring MVC Controller for Rade.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
@Controller
public class MainController {

  /**
   * Homepage mapping.
   * @return View for the Homepage.
   */
  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("entite", new SearchEntite());
    return "home";
  }

  /**
   * Login mapping.
   * @param model MVC model passed to JSP.
   * @return View for the Login page.
   */
  @RequestMapping("/login")
  public String login(Model model) {
    log.debug("Requesting /login");
    model.addAttribute("titre", "Login");    
    model.addAttribute("entite", new SearchEntite());
    return "login";
  }

  /**
   * Create a logout URL to log user out then redirect to login page.
   * @param request HTTP Servlet Request.
   * @param response HTTP Servlet Response.
   * @return View for the Logout page (i.e. the Login page again).
   */
  @RequestMapping("/logout")
  public String logoutPage(final HttpServletRequest request,
                           final HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      log.debug("Requesting /logout for user: {}", auth.getName());
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    // Redirect user to Login page
    return "redirect:/login?logout";
  }
}
