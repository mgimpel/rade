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
package fr.aesn.rade.webapp.mvc;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
  public String home() {
    return "home";
  }

  /**
   * Login mapping.
   * @param model MVC model passed to JSP.
   * @return View for the Login page.
   */
  @RequestMapping("/login")
  public String login(final Model model) {
    log.debug("Requesting /login");
    model.addAttribute("titre", "Login");
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

  /**
   * Help page.
   * @param model MVC model passed to JSP.
   * @return View for the Help page.
   */
  @GetMapping("/aide")
  public String aide(final Model model) {
    model.addAttribute("titre", "Aide");
    return "aide";
  }

  /**
   * FAQ page.
   * @return redirect to help page.
   */
  @GetMapping("/faq")
  public String faq() {
    return "redirect:/aide";
  }

  /**
   * Error page to display if the controller threw an Exception.
   * @param model MVC model passed to JSP.
   * @param e exception that was thrown.
   * @return View for the page.
   */
  @ExceptionHandler(IOException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleIOException(final Model model,
                                  final Exception e) {
    model.addAttribute("timestamp", new Date());
    model.addAttribute("error", "Internal Server Error");
    model.addAttribute("status", "500");
    model.addAttribute("message", e.getMessage());
    model.addAttribute("exception", e.getClass().getName());
    model.addAttribute("trace", ExceptionUtils.getStackTrace(e));
    return "error";
  }
}
