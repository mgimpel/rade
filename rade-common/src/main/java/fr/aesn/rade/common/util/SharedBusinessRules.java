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
package fr.aesn.rade.common.util;

import java.util.Date;

import fr.aesn.rade.persist.model.EntiteAdministrative;

/**
 * Collection of Shared Business Rules.
 * The methods in this class should be static and stateless.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public final class SharedBusinessRules {
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
   * @param date the Date to check.
   * @return true if the given EntiteAdministrative is Valid at the given date.
   */
  public static final boolean isEntiteAdministrativeValid(final EntiteAdministrative entiteAdmin, final Date date) {
    if (entiteAdmin == null || date == null) {
      return false;
    }
    return isBetween(entiteAdmin.getDebutValidite(), entiteAdmin.getFinValidite(), date);
  }

  /**
   * Check whether the given date is between the start and end dates.
   * (i.e. start &lt; date &lt; end).
   * If start is null, it is considered forever in the past
   * (i.e. always before the given date).
   * If end is null, it is considered forever in the future
   * (i.e. always after the given date).
   * @param start the start Date.
   * @param end the end Date.
   * @param date the Date to check.
   * @return true if the given date is between the start and end dates. 
   */
  public static final boolean isBetween(final Date start, final Date end, final Date date) {
    if (date == null) {
      return false;
    }
    return (start == null || (start != null && start.before(date)))
           && (end == null || end.after(date));
  }
}
