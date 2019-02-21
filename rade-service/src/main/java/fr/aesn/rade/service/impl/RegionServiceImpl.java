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
package fr.aesn.rade.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.common.util.SharedBusinessRules;
import fr.aesn.rade.persist.dao.RegionJpaDao;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.service.RegionService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for Region.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor @Slf4j
public class RegionServiceImpl
  implements RegionService {
  /** Data Access Object for Region. */
  @Autowired @Setter
  private RegionJpaDao regionJpaDao;

  /**
   * List all Region.
   * @return a List of all the Region.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Region> getAllRegion() {
    log.debug("Region list requested");
    return regionJpaDao.findAll();
  }

  /**
   * List all Region valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Region.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Region> getAllRegion(final Date date) {
    log.debug("Region list requested for Date: date={}", date);
    List<Region> list = regionJpaDao.findAll();
    Date testDate = (date == null ? new Date() : date);
    list.removeIf(e -> !SharedBusinessRules.isEntiteAdministrativeValid(e, testDate));
    return list;
  }

  /**
   * Returns a Map of all Region indexed by ID.
   * @return a Map of all Region indexed by ID.
   */
  @Override
  public Map<Integer, Region> getRegionMap() {
    log.debug("Region map requested");
    List<Region> list = getAllRegion();
    HashMap<Integer, Region> map = new HashMap<>(list.size());
    for (Region item : list) {
      map.put(item.getId(), item);
    }
    return map;
  }

  /**
   * Returns a Map of all Region valid at the given date and indexed by code.
   * @param date the date at which the Regions are valid.
   * @return a Map of all Region indexed by code INSEE.
   */
  @Override
  public Map<String, Region> getRegionMap(final Date date) {
    log.debug("Region map requested for Date : date={}", date);
    List<Region> list = getAllRegion(date);
    HashMap<String, Region> map = new HashMap<>(list.size());
    for (Region item : list) {
      map.put(item.getCodeInsee(), item);
    }
    return map;
  }

  /**
   * Get the Region with the given ID.
   * @param id the Region ID.
   * @return the Region with the given ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Region getRegionById(final int id) {
    log.debug("Region requested by ID: ID={}", id);
    Optional<Region> result = regionJpaDao.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Get the Region with the given code.
   * @param code the Region code.
   * @return list of Regions that have historically had the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Region> getRegionByCode(final String code) {
    log.debug("Region requested by code: code={}", code);
    return regionJpaDao.findByCodeInsee(code);
    /*
    // Can also be done by using an Example:
    Region criteria = new Region();
    criteria.setCodeInsee(code);
    Example<Region> example = Example.of(criteria);
    return regionJpaDao.findAll(example);
    */
  }

  /**
   * Get the Region with the given code at the given date.
   * @param code the Region code.
   * @param date the date at which the code was valid
   * @return the Region with the given code at the given date.
   */
  @Override
  public Region getRegionByCode(final String code, final Date date) {
    log.debug("Region requested by code and date: code={}, date={}", code, date);
    List<Region> list = getRegionByCode(code);
    if (list == null) {
      return null;
    }
    Date testdate = (date == null ? new Date() : date);
    for (Region reg : list) {
      if (SharedBusinessRules.isEntiteAdministrativeValid(reg, testdate)) {
        // Suppose database correct (au plus 1 valeur valide)
          return reg;
      }
    }
    return null;
  }

  /**
   * Get the Region with the given code at the given date.
   * @param code the Region code.
   * @param date the date at which the code was valid
   * @return the Region with the given code at the given date.
   */
  @Override
  public Region getRegionByCode(final String code, final String date) {
    log.debug("Region requested by code and date: code={}, date={}", code, date);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return getRegionByCode(code, sdf.parse(date));
    } catch (ParseException e) {
      log.warn("Region requested by code and date: Exception parsing date {}", date, e);
      return null;
    }
  }

  /**
   * Invalidates the given region by setting the regions finValidite field to
   * the given date.
   * @param region the region to invalidate.
   * @param date the date of end of validity for the region.
   * @return the now invalidated region.
   */
  @Override
  @Transactional(readOnly = false)
  public Region invalidateRegion(final Region region, final Date date) {
    if ((region == null) || (date == null)) {
      return null;
    }
    Region oldRegion = getRegionById(region.getId());
    if (!(region.equals(oldRegion))
        || (oldRegion.getFinValidite() != null)) {
      // given region has other changes
      return null;
    }
    if (!(date.after(oldRegion.getDebutValidite()))) {
      // given end of validity if before regions beginning of validity
      return null;
    }
    oldRegion.setFinValidite(date);
    return regionJpaDao.save(oldRegion);
  }
}
