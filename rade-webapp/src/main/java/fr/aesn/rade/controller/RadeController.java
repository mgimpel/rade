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
package fr.aesn.rade.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import fr.aesn.rade.service.CommuneService;

/**
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Controller
public class RadeController extends AbstractController {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(RadeController.class);
  /** Service. */
  @Autowired
  private CommuneService communeService;

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                               HttpServletResponse response)
    throws Exception
  {
    ModelAndView model = new ModelAndView("HelloWorldPage");
    model.addObject("msg", "hello world");
    return model;
  }

  /**
   * Login mapping.
   * @return ModelAndView for the Application Root.
   * @throws IOException if the is an IOException.
   */
  @RequestMapping(value = "/login")
  public String login() {
    log.debug("Requesting /login");
    return "login";
  }

  /**
   * Create a logout URL to log user out then redirect to login page.
   * @param request HTTP Servlet Request.
   * @param response HTTP Servlet Response.
   * @return the View (the login page).
   * @throws IOException if the is an IOException.
   */
  @RequestMapping(value = "/logout")
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
