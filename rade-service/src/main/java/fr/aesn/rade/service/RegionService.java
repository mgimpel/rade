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

import fr.aesn.rade.persist.model.Region;

/**
 * Service Interface for Region.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface RegionService {
  /**
   * List all Region.
   * @return a List of all the Region.
   */
  public List<Region> getAllRegion();

  /**
   * List all Region valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Region.
   */
  public List<Region> getAllRegion(Date date);

  /**
   * Returns a Map of all Region indexed by ID.
   * @return a Map of all Region indexed by ID.
   */
  public Map<Integer, Region> getRegionMap();

  /**
   * Get the Region with the given ID.
   * @param id the Region ID.
   * @return the Region with the given ID.
   */
  public Region getRegionById(int id);

  /**
   * Get the Region with the given code.
   * @param code the Region code.
   * @return list of Regions that have historically had the given code.
   */
  public List<Region> getRegionByCode(String code);

  /**
   * Get the Region with the given code at the given date.
   * @param code the Region code.
   * @param date the date at which the code was valid
   * @return the Region with the given code at the given date.
   */
  public Region getRegionByCode(String code, Date date);

  /**
   * Get the Region with the given code at the given date.
   * @param code the Region code.
   * @param date the date at which the code was valid
   * @return the Region with the given code at the given date.
   */
  public Region getRegionByCode(String code, String date);
}
