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
package fr.aesn.rade.persist.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.aesn.rade.persist.model.Commune;

/**
 * JPA DataAccessObject for Commune.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface CommuneJpaDao
  extends JpaRepository<Commune, Integer> {
  /**
   * Returns a List of Commune with the given CodeInsee.
   * Database Query created from Method Name by JPA.
   * @param codeInsee the Code INSEE of the Commune.
   * @return a List of Commune with the given CodeInsee.
   */
  public List<Commune> findByCodeInsee(String codeInsee);

  /**
   * Returns a List of all Commune valid at the given date.
   * @param date the date at which the Commune was valid
   * @return a List of all the valid Commune.
   */
  @Query("SELECT c FROM Commune c WHERE c.debutValidite <= ?1 "
                               + "AND (c.finValidite IS NULL OR c.finValidite > ?1)")
  public List<Commune> findAllValidOnDate(Date date);
  
  /**
   * Returns a List of all Commune matching the request parameters.
   * @param date the date at which the Commune was valid
   * @param codedepartement the Departement INSEE code of the Commune.
   * @return a List of all Commune matching the request parameters.
   */
  @Query("SELECT c FROM Commune c WHERE c.debutValidite <= ?1 "
                               + "AND (c.finValidite IS NULL OR c.finValidite > ?1)"
                               + "AND (c.departement = ?2)")
  public List<Commune> findAllByCodedepartementValidOnDate(Date date, String codedepartement);
  
  /**
   * Returns a List of all Commune matching the request parameters.
   * @param date the date at which the Commune was valid
   * @param nom a part of the Commune enrich name.
   * @return a List of all Commune matching the request parameters.
   */
  @Query("SELECT c FROM Commune c WHERE c.debutValidite <= ?1 "
                               + "AND (c.finValidite IS NULL OR c.finValidite > ?1)"
                               + "AND (LOWER(c.nomEnrichi) LIKE CONCAT('%',LOWER(?2),'%'))")
  public List<Commune> findAllByNomValidOnDate(Date date, String nom);
  
  /**
   * Returns a List of all Commune matching the request parameters.
   * @param date the date at which the Commune was valid
   * @param codedepartement the Departement INSEE code of the Commune.
   * @param nom a part of the Commune enrich name.
   * @return a List of all Commune matching the request parameters.
   */
  @Query("SELECT c FROM Commune c WHERE c.debutValidite <= ?1 "
                               + "AND (c.finValidite IS NULL OR c.finValidite > ?1)"
                               + "AND (c.departement = ?2)"
                               + "AND (LOWER(c.nomEnrichi) LIKE CONCAT('%',LOWER(?3),'%'))")
  public List<Commune> findAllByCodedepartementAndNomValidOnDate(Date date, String codedepartement, String nom);

  /**
   * Returns the Commune with the given CodeInsee valid at the given date.
   * @param codeInsee the Code INSEE of the Commune.
   * @param date the date at which the Commune was valid
   * @return the valid Commune.
   */
  @Query("SELECT c FROM Commune c WHERE c.codeInsee = ?1 "
                               + "AND c.debutValidite <= ?2 "
                               + "AND (c.finValidite IS NULL OR c.finValidite > ?2)")
  public Commune findByCodeInseeValidOnDate(String codeInsee, Date date);
}
