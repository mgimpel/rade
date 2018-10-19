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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.common.util.SharedBusinessRules;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.service.CommuneService;

/**
 * Service Implementation for Commune.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
public class CommuneServiceImpl
  implements CommuneService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(CommuneServiceImpl.class);
  /** Data Access Object for Commune. */
  @Autowired
  private CommuneJpaDao communeJpaDao;

  /**
   * Empty Constructor for Bean.
   */
  public CommuneServiceImpl() {
    // Empty Constructor for Bean.
  }

  /**
   * Standard Constructor.
   * @param communeJpaDao Data Access Object for Commune.
   */
  public CommuneServiceImpl(final CommuneJpaDao communeJpaDao) {
    setCommuneJpaDao(communeJpaDao);
  }

  /**
   * Sets the Data Access Object for Commune.
   * @param communeJpaDao Data Access Object for Commune.
   */
  public void setCommuneJpaDao(final CommuneJpaDao communeJpaDao) {
    this.communeJpaDao = communeJpaDao;
  }

  /**
   * List all Commune.
   * @return a List of all the Commune.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getAllCommune() {
    log.debug("Commune list requested");
    return communeJpaDao.findAll();
  }

  /**
   * List all Commune valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Commune.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getAllCommune(final Date date) {
    log.debug("Commune list requested for Date: date={}", date);
    return communeJpaDao.findAllValidOnDate(date);
  }

  /**
   * Returns a Map of all Commune indexed by ID.
   * @return a Map of all Commune indexed by ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<Integer, Commune> getCommuneMap() {
    log.debug("Commune map requested");
    List<Commune> list = getAllCommune();
    HashMap<Integer, Commune> map = new HashMap<>(list.size());
    for (Commune item : list) {
      map.put(item.getId(), item);
    }
    return map;
  }

  /**
   * Get the Commune with the given ID.
   * @param id the Commune ID.
   * @return the Commune with the given ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Commune getCommuneById(final int id) {
    log.debug("Commune requested by ID: ID={}", id);
    Optional<Commune> result = communeJpaDao.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Get the Commune with the given code.
   * @param code the Commune code.
   * @return list of Communes that have historically had the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getCommuneByCode(final String code) {
    log.debug("Commune requested by code: code={}", code);
    return communeJpaDao.findByCodeInsee(code);
    /*
    // Can also be done by using an Example:
    Commune criteria = new Commune();
    criteria.setCodeInsee(code);
    Example<Commune> example = Example.of(criteria);
    return communeJpaDao.findAll(example);
    */
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public Commune getCommuneByCode(final String code, final Date date) {
    log.debug("Commune requested by code and date: code={}, date={}", code, date);
    List<Commune> list = getCommuneByCode(code);
    if (list == null) {
      return null;
    }
    Date testdate = date == null ? new Date() : date;
    for (Commune commune : list) {
      if (SharedBusinessRules.isEntiteAdministrativeValid(commune, testdate)) {
        // Suppose database correct (au plus 1 valeur valide)
        return commune;
      }
    }
    return null;
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public Commune getCommuneByCode(final String code, final String date) {
    log.debug("Commune requested by code and date: code={}, date={}", code, date);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return getCommuneByCode(code, sdf.parse(date));
    } catch (ParseException e) {
      log.warn("Commune requested by code and date: Exception parsing date {}", date, e);
      return null;
    }
  }
}
