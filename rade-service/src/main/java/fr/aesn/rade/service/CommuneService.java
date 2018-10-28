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

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Commune;

/**
 * Service Interface for Commune.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface CommuneService {
  /**
   * List all Commune.
   * @return a List of all the Commune.
   */
  public List<Commune> getAllCommune();

  /**
   * List all Commune valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Commune.
   */
  public List<Commune> getAllCommune(Date date);

  /**
   * Returns a Map of all Commune indexed by ID.
   * @return a Map of all Commune indexed by ID.
   */
  public Map<Integer, Commune> getCommuneMap();

  /**
   * Returns a Map of all Commune valid at the given date and indexed by code.
   * @param date the date at which the Commune are valid.
   * @return a Map of all Commune indexed by code INSEE.
   */
  public Map<String, Commune> getCommuneMap(Date date);

  /**
   * Get the Commune with the given ID.
   * @param id the Commune ID.
   * @return the Commune with the given ID.
   */
  public Commune getCommuneById(int id);

  /**
   * Get the Commune with the given code.
   * @param code the Commune code.
   * @return list of Communes that have historically had the given code.
   */
  public List<Commune> getCommuneByCode(String code);

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  public Commune getCommuneByCode(String code, Date date);

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  public Commune getCommuneByCode(String code, String date);

  /**
   * Invalidates the given commune by setting the communes finValidite
   * field to the given date.
   * @param commune the commune to invalidate.
   * @param date the date of end of validity for the commune.
   * @return the now invalidated commune.
   */
  public Commune invalidateCommune(Commune commune, Date date);

  /**
   * Changes the name (MOD=100 : Changement de Nom) of the Commune with the
   * given CodeInsee effective as of the given Date.
   * @param codeInsee the code of Commune to change.
   * @param dateEffective the date that the change takes effect.
   * @param tnccoff the type of the official new name.
   * @param nccoff the official new name.
   * @param audit audit details about change.
   * @param commentaire comment for the genealogie link.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  public Commune mod100ChangementdeNom(String codeInsee, Date dateEffective,
                                       String tnccoff, String nccoff,
                                       Audit audit, String commentaire)
    throws InvalidArgumentException;

  /**
   * Creates (MOD=200 : Creation) a new Commune with the given CodeInsee and
   * details, effective as of the given Date.
   * @param codeInsee the code of the new Commune.
   * @param dateEffective the date that the change takes effect.
   * @param departement the departement to which the new Commune belongs.
   * @param tnccoff the type of the official name.
   * @param nccoff the official name.
   * @param audit audit details about change.
   * @param commentaire comment for the new Commune.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  public Commune mod200Creation(String codeInsee, Date dateEffective,
                                String departement,
                                String tnccoff, String nccoff,
                                Audit audit, String commentaire)
    throws InvalidArgumentException;
}
