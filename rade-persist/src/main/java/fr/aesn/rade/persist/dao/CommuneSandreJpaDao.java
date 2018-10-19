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

import fr.aesn.rade.persist.model.CommuneSandre;

/**
 * JPA DataAccessObject for CommuneSandre.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface CommuneSandreJpaDao
  extends JpaRepository<CommuneSandre, String> {
  /**
   * Returns a List of all Commune valid at the given date.
   * @param date the date at which the Commune was valid
   * @return a List of all the valid Commune.
   */
  @Query("SELECT c FROM CommuneSandre c WHERE c.dateCreationCommune <= ?1")
  public List<CommuneSandre> findAllValidOnDate(Date date);

  /**
   * Returns the Commune with the given CodeInsee valid at the given date.
   * @param codeInsee the Code INSEE of the Commune.
   * @param date the date at which the Commune was valid
   * @return the valid Commune.
   */
  @Query("SELECT c FROM CommuneSandre c WHERE c.codeCommune = ?1 "
                               + "AND c.dateCreationCommune <= ?2")
  public CommuneSandre findByCodeInseeValidOnDate(String codeInsee, Date date);
}
