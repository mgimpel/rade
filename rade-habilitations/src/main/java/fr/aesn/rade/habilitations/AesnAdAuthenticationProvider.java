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

/**
 * Custom AuthenticationProvider for Spring Security that queries AESN
 * Active Directory.
 * 
 * The Habilitations provides many more features than are required by
 * Spring Security, and all the details are in fact stored in the Active
 * Directory. As such it is possible to bypass Habilitations completely and
 * directly query the AD.
 * 
 * The AD can easily authenticate users. To recover the list of roles is a bit
 * more technical. There are 2 types of roles:
 * <ul>
 * <li>User roles : these are given directly to the user</li>
 * <li>Structure roles : these are granted automatically to users based on
 * their place in the hierarchical structure of the agency.</li>
 * </ul>
 * To recover all the roles a user may have it is necessary to search both
 * types as follows:
 * <ul>
 * <li>User roles : simply recover all "role" attributes of the user</li>
 * <li>Structure roles : This is a multi-step process:
 *   <ul>
 *   <li>1) identify the structure that the user is attached to. This can be
 *   determined from the "division" attribute.</li>
 *   <li>2) find the Structure OU within "OU=AGENCE,OU=Structures" directory.
 *   Note that it is hierarchical, and thus my be several layers down.</li>
 *   <li>3) recover all "role" attributes of the unit.</li>
 *   <li>4) recursively climb up the tree all the way up to "OU=AGENCE",
 *   and at each step recover all "role" attributes of the unit.</li>
 *   </ul>
 * </li>
 * </ul>
 * 
 * NB: This AuthenticationProvider could be based on
 * org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class AesnAdAuthenticationProvider {
  //TODO
}
