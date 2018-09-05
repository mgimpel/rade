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
package fr.aesn.rade.habilitations;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import fr.aesn.rade.habilitations.ws.HabilitationException;
import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrv;
import fr.aesn.rade.habilitations.ws.RoleBean;

/**
 * Custom AuthenticationProvider for Spring Security that queries the AESN
 * Habilitation application.
 *
 * The Habilitations application provides many more features than are required
 * by Spring Security. As such only a small subset of features will be used,
 * namely:
 * <ul>
 * <li>authentification: which authenticates the given user (with the given
 *     password)</li>
 * <li>getRolesDunPerimetre: which lists all the roles for a given user</li>
 * </ul>
 * It should be noted that only roles beginning with "RAD_" are used by the
 * Rade application (RAD_ADMIN, RAD_GESTION and RAD_CONSULT are the only roles
 * defined so far).
 *
 * NB: The WebServices provided by the Habilitations application are quite
 * ancient, and incompatible with modern SOAP libraries such as CXF and Axis2.
 * As such it is necessary to user Axis1 for these WebServices.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class HabilitationsAuthenticationProvider
  extends AbstractUserDetailsAuthenticationProvider {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HabilitationsAuthenticationProvider.class);

  /** The WebService Stub that queries the Habilitations server. */
  private HabilitationsUtilisateurSrv habilitationsService;

  /**
   * Retrieve the <code>UserDetails</code> from an implementation-specific
   * location, with the option of throwing an
   * <code>AuthenticationException</code> immediately if the presented
   * credentials are incorrect (this is especially useful if it is necessary
   * to bind to a resource as the user in order to obtain or generate a
   * <code>UserDetails</code>).
   *
   * <p>The classes will not perform credentials inspection in this method,
   * instead performing it in
   * additionalAuthenticationChecks(UserDetails, UsernamePasswordAuthenticationToken)
   * so that code related to credentials validation need not be duplicated
   * across two methods.</p> 
   *
   * @param username The username to retrieve
   * @param authentication The authentication request
   * @return the user information (never <code>null</code> -
   *         instead an exception should the thrown)
   * @throws AuthenticationException if the credentials could not be validated
   *         (generally a <code>BadCredentialsException</code>,
   *         an <code>AuthenticationServiceException</code> or
   *         <code>UsernameNotFoundException</code>)
   */
  @Override
  protected UserDetails retrieveUser(String username,
                                     UsernamePasswordAuthenticationToken authentication) {
    if (authentication == null) {
      throw new BadCredentialsException("Authentication Token was null");
    }
    String name = authentication.getName();
    if (username == null || !username.equals(name)) {
      throw new BadCredentialsException("Username different from that "
                                        + "provided by authentication: "
                                        + username + " != " + name);
    }
    Object credentials = authentication.getCredentials();
    if (credentials == null) {
      throw new BadCredentialsException("Credentials were null");
    }
    String password = credentials.toString();

    // Call "getRolesDunPerimetre" WebService (with empty perimetre)
    // and extract RAD_* roles
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    RoleBean[] roles;
    try {
      roles = habilitationsService.getRolesDunPerimetre(username, "");
      String code;
      for (RoleBean role : roles) {
        if (role != null) {
          code = role.getCode();
          // Valid roles for the application are: "RAD_ADMIN", "RAD_GESTION" &
          // "RAD_CONSULT"
          if (code != null && code.startsWith("RAD_")) {
            log.info("Role found for user {}: {}", username, code);
            grantedAuthorities.add(new SimpleGrantedAuthority(code));
          }
        }
      }
    } catch (RemoteException e) {
      log.debug("Unable to list user roles for {}", username, e);
      throw new AuthenticationServiceException("Unable to list user roles for "
                                               + username, e);
    }

    return new User(username, password, grantedAuthorities);
    // NB: If one wants more user details (firstname, surname, email,
    // department,...), one could extend the User class and then use the
    // getDetailsUtilisateur WebService to populate it with the extra details.
    // This WebService returns details like:
    // * code et libelle fonction
    // * code et libelle structure
    // * prenom, nom, nom complet, courriel
    // * responsable
    // * profil GED
  }

  /**
   * Perform any additional checks on a returned (or cached)
   * <code>UserDetails</code> for a given authentication request.
   * Generally a subclass will at least compare the
   * Authentication.getCredentials() with a UserDetails.getPassword()
   * If custom logic is needed to compare additional properties of
   * <code>UserDetails</code> and/or
   * <code>UsernamePasswordAuthenticationToken</code>,
   * these should also appear in this method.
   *
   * @param userDetails as retrieved from the
   *        retrieveUser(String, UsernamePasswordAuthenticationToken)
   *        or <code>UserCache</code>
   * @param authentication the current request that needs to be authenticated
   * @throws AuthenticationException AuthenticationException if the credentials
   *         could not be validated (generally a <code>BadCredentialsException</code>,
   *         an <code>AuthenticationServiceException</code>)
   */ 
  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication) {
    if (authentication == null) {
      throw new BadCredentialsException("Authentication Token was null");
    }
    String name = authentication.getName();
    Object credentials = authentication.getCredentials();
    if (credentials == null) {
      throw new BadCredentialsException("Credentials were null");
    }
    String password = credentials.toString();

    // Call authentication WebService to authenticate user
    try {
      boolean result = habilitationsService.authentification(name, password);
      if (!result) {
        throw new BadCredentialsException("Authentication result negative");
      }
      log.info("User authenticated: {}", name);
    } catch (HabilitationException e) {
      log.debug("Unable to authenticate user {}", name, e);
      throw new BadCredentialsException("Unable to authenticate user " + name,
                                        e);
    } catch (RemoteException e) {
      log.debug("Unable to authenticate user {}", name, e);
      throw new AuthenticationServiceException("Unable to authenticate user "
                                               + name, e);
    }
  }

  /**
   * Sets the WebService Stub to be used by this AuthenticationProvider.
   * @param service The WebService Stub that queries the Habilitations server.
   */
  public void setHabilitationsService(HabilitationsUtilisateurSrv service) {
    this.habilitationsService = service;
  }
}
