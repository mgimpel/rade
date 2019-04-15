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

import fr.aesn.rade.persist.model.Departement;

/**
 * JPA DataAccessObject for Departement.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface DepartementJpaDao
  extends JpaRepository<Departement, Integer> {
  /**
   * Returns a List of Departement with the given CodeInsee.
   * Database Query created from Method Name by JPA.
   * @param codeInsee the Code INSEE of the Departement.
   * @return a List of Departement with the given CodeInsee.
   */
  public List<Departement> findByCodeInsee(String codeInsee);

  /**
   * Returns a List of all Departement using the given region and departement
   * name.
   * @param region the region of the Departement.
   * @param nameLike a pattern to search for Departements with a name resembling.
   * @return a List of all Departement matching the given parameters.
   */
  public List<Departement> findByRegionLikeAndNomEnrichiLikeIgnoreCaseOrderByNomEnrichiAsc(String region,
                                                                                           String nameLike);

  /**
   * Returns a List of all Departement using the given region, departement name
   * and date.
   * @param region the region of the Departement.
   * @param nameLike a pattern to search for Departements with a name resembling.
   * @param date the date at which the Departements were valid.
   * @return a List of all Departement matching the given parameters.
   */
  @Query("SELECT DISTINCT(d) FROM Departement d"
               + " WHERE (d.region LIKE ?1)"
               + " AND (UPPER(d.nomMajuscule) LIKE UPPER(?2) OR UPPER(d.nomEnrichi) LIKE UPPER(?2))"
               + " AND (d.debutValidite IS NULL OR d.debutValidite <= ?3)"
               + " AND (d.finValidite IS NULL OR d.finValidite > ?3)"
               + " ORDER BY d.nomEnrichi")
  public List<Departement> findByRegionLikeAndNomEnrichiLikeIgnoreCaseValidOnDate(String region,
                                                                                  String nameLike,
                                                                                  Date date);
}
