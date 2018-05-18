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
package fr.aesn.rade.service.impl;

import java.util.Date;

import fr.aesn.rade.persist.model.EntiteAdministrative;

/**
 * Collection of Shared Business Rules.
 * The methods in this class should be static and stateless.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class SharedBusinessRules {
  /**
   * Hidden private Constructor makes the class non-instantiable.
   */
  private SharedBusinessRules() {
    // Empty Constructor.
  }

  /**
   * Check whether the given EntiteAdministrative is Valid at the given date
   * (i.e. debutValidité &lt; date &lt; finValidité).
   * If finValidité is null, the the EntiteAdministrative is considered
   * currently valid.
   * @param entiteAdmin the EntiteAdministrative to check.
   * @param date the Date to check
   * @return true if the given EntiteAdministrative is Valid at the given date.
   */
  public static final boolean isEntiteAdministrativeValid(final EntiteAdministrative entiteAdmin, final Date date) {
    if (entiteAdmin == null || date == null) {
      return false;
    }
    Date debut = entiteAdmin.getDebutValidite();
    Date fin = entiteAdmin.getFinValidite();
    return ((debut != null && debut.before(date)) &&
            (fin == null || fin.after(date)));
  }
}
