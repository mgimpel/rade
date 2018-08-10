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

import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.aesn.rade.persist.model.Departement;

/**
 * Service Interface for Departement.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface DepartementService {
  /**
   * List all Departement.
   * @return a List of all the Departement.
   */
  public List<Departement> getAllDepartement();

  /**
   * List all Departement valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Departement.
   */
  public List<Departement> getAllDepartement(Date date);

  /**
   * Returns a Map of all Departement indexed by ID.
   * @return a Map of all Departement indexed by ID.
   */
  public Map<Integer, Departement> getDepartementMap();

  /**
   * Returns a Map of all Departement valid at the given date and indexed by
   * code.
   * @param date the date at which the departements are valid
   * @return a Map of all Departement indexed by code INSEE.
   */
  public Map<String, Departement> getDepartementMap(Date date);

  /**
   * Get the Departement with the given ID.
   * @param id the Departement ID.
   * @return the Departement with the given ID.
   */
  public Departement getDepartementById(int id);

  /**
   * Get the Departement with the given code.
   * @param code the Departement code.
   * @return list of Departements that have historically had the given code.
   */
  public List<Departement> getDepartementByCode(String code);

  /**
   * Get the Departement with the given code at the given date.
   * @param code the Departement code.
   * @param date the date at which the code was valid
   * @return the Departement with the given code at the given date.
   */
  public Departement getDepartementByCode(String code, Date date);

  /**
   * Get the Departement with the given code at the given date.
   * @param code the Departement code.
   * @param date the date at which the code was valid
   * @return the Departement with the given code at the given date.
   */
  public Departement getDepartementByCode(String code, String date);

  /**
   * Invalidates the given departement by setting the departements finValidite
   * field to the given date.
   * @param dept the departement to invalidate.
   * @param date the date of end of validity for the departement.
   * @return the now invalidated departement.
   */
  public Departement invalidateDepartement(Departement dept, Date date);
}
