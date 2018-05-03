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
package fr.aesn.rade.service;

import java.util.List;
import java.util.Map;

import fr.aesn.rade.persist.model.CirconscriptionBassin;

/**
 * Service Interface for Circonscription Bassin.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface BassinService {
  /**
   * List all Bassin.
   * @return a List of all the Bassin.
   */
  public List<CirconscriptionBassin> getAllBassin();

  /**
   * Returns a Map of all Bassin indexed by ID.
   * @return a Map of all Bassin indexed by ID.
   */
  public Map<String, CirconscriptionBassin> getBassinMap();

  /**
   * Get the Bassin with the given code.
   * @param code the Bassin code.
   * @return the Bassin with the given code.
   */
  public CirconscriptionBassin getBassinByCode(String code);
}
