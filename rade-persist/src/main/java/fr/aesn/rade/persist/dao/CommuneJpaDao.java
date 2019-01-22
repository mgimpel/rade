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
  @Query("SELECT c FROM Commune c"
               + " WHERE (c.debutValidite IS NULL OR c.debutValidite <= ?1)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?1)")
  public List<Commune> findAllValidOnDate(Date date);

  /**
   * Returns the Commune with the given CodeInsee valid at the given date.
   * @param codeInsee the Code INSEE of the Commune.
   * @param date the date at which the Commune was valid
   * @return the valid Commune.
   */
  @Query("SELECT c FROM Commune c"
               + " WHERE c.codeInsee = ?1"
               + " AND (c.debutValidite IS NULL OR c.debutValidite <= ?2)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?2)")
  public Commune findByCodeInseeValidOnDate(String codeInsee,
                                            Date date);

  /**
   * Returns a List of from the given departement valid at the given date.
   * @param dept the departement of the Communes.
   * @param date the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Query("SELECT c FROM Commune c"
               + " WHERE c.departement = ?1"
               + " AND (c.debutValidite IS NULL OR c.debutValidite <= ?2)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?2)")
  public List<Commune> findByDepartementValidOnDate(String dept,
                                                    Date date);

  /**
   * Returns a List of all Commune resembling the given name and valid at the
   * given date.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @param date the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Query("SELECT c FROM Commune c"
               + " WHERE (c.nomMajuscule LIKE '%' || UPPER(?1) || '%')"
               + " AND (c.debutValidite IS NULL OR c.debutValidite <= ?2)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?2)")
  public List<Commune> findByNameLikeValidOnDate(String nameLike,
                                                 Date date);

  /**
   * Returns a List of all Commune from the given departement, resembling the
   * given name and valid at the given date.
   * @param dept the departement of the Communes.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @param date the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Query("SELECT c FROM Commune c"
               + " WHERE c.departement = ?1"
               + " AND (c.nomMajuscule LIKE '%' || UPPER(?2) || '%')"
               + " AND (c.debutValidite IS NULL OR c.debutValidite <= ?3)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?3)")
  public List<Commune> findByDepartementAndNameLikeValidOnDate(String dept,
                                                               String nameLike,
                                                               Date date);
  /**
   * Returns a List of all Commune using the given Code INSEE, department and 
   * commune name 
   * @param codeInsee the code INSEE of the Communes.
   * @param departement the department of the Communes.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @return a List of all Commune matching the given parameters.
   */
  public List<Commune> findByCodeInseeLikeAndDepartementLikeAndAndNomEnrichiLikeIgnoreCase(String codeInsee,
                                                                                           String departement, 
                                                                                           String nameLike);
  /**
   * Returns a List of all Commune using the given Code INSEE, department, commune 
   * name and a date
   * @param codeInsee the code INSEE of the Communes.
   * @param departement the department of the Communes.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @param date the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Query("SELECT DISTINCT(c) FROM Commune c"
               + " WHERE (c.codeInsee LIKE ?1)"
               + " AND (c.departement LIKE ?2)"
               + " AND (UPPER(c.nomMajuscule) LIKE UPPER(?3) OR UPPER(c.nomEnrichi) LIKE UPPER(?3))" 
               + " AND (c.debutValidite <= ?4)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?4)"
               + " ORDER BY c.nomEnrichi")
  public List<Commune> findByCodeInseeLikeAndDepartementLikeAndNomEnrichiLikeIgnoreCaseValidOnDate(String codeInsee,
                                                                                         String departement, 
                                                                                         String nameLike, 
                                                                                         Date date);
  /**
   * Returns a List of all Commune using the given Code INSEE, department and commune
   * name 
   * @param codeInsee the code INSEE of the Communes.
   * @param region the region of the Communes.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @return a List of all Commune matching the given parameters.
   */
  @Query("SELECT DISTINCT(c) FROM Commune c, Departement d"
               + " WHERE c.departement = d.codeInsee"
               + " AND (c.codeInsee LIKE ?1)"
               + " AND (d.region LIKE ?2)"
               + " AND (UPPER(c.nomMajuscule) LIKE UPPER(?3) OR UPPER(c.nomEnrichi) LIKE UPPER(?3))" 
               + " ORDER BY c.nomEnrichi")
  public List<Commune> findByCodeInseeLikeAndRegionLikeAndNomEnrichiLikeIgnoreCase(String codeInsee,
                                                                         String region, 
                                                                         String nameLike);
  /**
   * Returns a List of all Commune using the given Code INSEE, region, commune name
   * and a date
   * @param codeInsee the code INSEE of the Communes.
   * @param region the region of the Communes.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @param date the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Query("SELECT DISTINCT(c) FROM Commune c, Departement d"
               + " WHERE c.departement = d.codeInsee"
               + " AND (c.codeInsee LIKE ?1)"
               + " AND (d.region LIKE ?2)"
               + " AND (UPPER(c.nomMajuscule) LIKE UPPER(?3) OR UPPER(c.nomEnrichi) LIKE UPPER(?3))" 
               + " AND (c.debutValidite <= ?4)"
               + " AND (c.finValidite IS NULL OR c.finValidite > ?4)"
               + " ORDER BY c.nomEnrichi")
  public List<Commune> findByCodeInseeLikeAndRegionLikeAndNomEnrichiLikeIgnoreCaseValidOnDate(String codeInsee,
                                                                                    String region, 
                                                                                    String nameLike, 
                                                                                    Date date);
  
}
