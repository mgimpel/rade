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

import fr.aesn.rade.persist.model.Delegation;

/**
 * Service Interface for Delegation.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface DelegationService {
  /**
   * Add a Delegation.
   * @param delegation the Delegation to add.
   */
  public void addDelegation(Delegation delegation);

  /**
   * Update a Delegation.
   * @param delegation the Delegation to update.
   */
  public void updateDelegation(Delegation delegation);

  /**
   * List all Delegation.
   * @return a List of all the Delegation.
   */
  public List<Delegation> getAllDelegation();

  /**
   * Returns a Map of all Delegation indexed by ID.
   * @return a Map of all Delegation indexed by ID.
   */
  public Map<String, Delegation> getDelegationMap();

  /**
   * Get the Delegation with the given ID.
   * @param code the Delegation ID.
   * @return the Delegation with the given ID.
   */
  public Delegation getDelegationById(String code);

  /**
   * Delete the Delegation with the given ID.
   * @param code the Delegation ID.
   */
  public void removeDelegation(String code);
}
