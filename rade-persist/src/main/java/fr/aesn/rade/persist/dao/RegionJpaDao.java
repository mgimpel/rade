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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.aesn.rade.persist.model.Region;

/**
 * JPA DataAccessObject for Region.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface RegionJpaDao
  extends JpaRepository<Region, Integer> {
  /**
   * Returns a List of Region with the given CodeInsee.
   * Database Query created from Method Name by JPA.
   * @param codeInsee the Code INSEE of the Region.
   * @return a List of Region with the given CodeInsee.
   */
  public List<Region> findByCodeInsee(String codeInsee);
}
