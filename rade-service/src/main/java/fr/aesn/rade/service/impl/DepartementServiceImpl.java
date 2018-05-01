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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.DepartementJpaDao;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.service.DepartementService;

/**
 * Service Implementation for Departement.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
public class DepartementServiceImpl
  implements DepartementService {
  /** SLF4J Logger. */
  private static final Logger logger =
    LoggerFactory.getLogger(DepartementServiceImpl.class);
  /** Data Access Object for Delegation. */
  private DepartementJpaDao departementJpaDao;

  /**
   * Empty Constructor for Bean.
   */
  public DepartementServiceImpl() {
    // Empty Constructor for Bean.
  }

  /**
   * Standard Constructor.
   * @param DepartementJpaDao Data Access Object for Departement.
   */
  public DepartementServiceImpl(final DepartementJpaDao DepartementJpaDao) {
    setDepartementJpaDao(DepartementJpaDao);
  }

  /**
   * Sets the Data Access Object for Departement.
   * @param DepartementJpaDao Data Access Object for Departement.
   */
  public void setDepartementJpaDao(final DepartementJpaDao DepartementJpaDao) {
    this.departementJpaDao = DepartementJpaDao;
  }

  /**
   * List all Departement.
   * @return a List of all the Departement.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Departement> getAllDepartement() {
    logger.info("Departement list requested");
    return departementJpaDao.findAll();
  }

  /**
   * List all Departement valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Departement.
   */
  public List<Departement> getAllDepartement(final Date date) {
    logger.info("Departement list requested for Date: date={}", date);
    List<Departement> list = departementJpaDao.findAll();
    list.removeIf(e -> !SharedBusinessRules.isEntiteAdministrativeValid(e, date));
    return list;
  }

  /**
   * Returns a Map of all Departement indexed by ID.
   * @return a Map of all Departement indexed by ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<Integer, Departement> getDepartementMap() {
    logger.info("Departement map requested");
    List<Departement> list = getAllDepartement();
    HashMap<Integer, Departement> map = new HashMap<Integer, Departement>(list.size());
    for (Departement item : list) {
      map.put(item.getId(), item);
    }
    return map;
  }

  /**
   * Get the Departement with the given ID.
   * @param id the Departement ID.
   * @return the Departement with the given ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Departement getDepartementById(final int id) {
    logger.info("Departement requested by ID: ID={}", id);
    Optional<Departement> result = departementJpaDao.findById(id);
    if (result.isPresent()) {
      return result.get();
    }
    else {
      return null;
    }
  }

  /**
   * Get the Departement with the given code.
   * @param code the Departement code.
   * @return list of Departements that have historically had the given code.
   */
  public List<Departement> getDepartementByCode(final String code) {
    logger.info("Departement requested by code: code={}", code);
    return departementJpaDao.findByCodeInsee(code);
    /*
    // Can also be done by using an Example:
    Departement criteria = new Departement();
    criteria.setCodeInsee(code);
    Example<Departement> example = Example.of(criteria);
    return departementJpaDao.findAll(example);
    */
  }

  /**
   * Get the Departement with the given code at the given date.
   * @param code the Departement code.
   * @param date the date at which the code was valid
   * @return the Departement with the given code at the given date.
   */
  public Departement getDepartementByCode(final String code, final Date date) {
    logger.info("Departement requested by code and date: code={}, date={}", code, date);
    List<Departement> list = getDepartementByCode(code);
    if (list == null) {
      return null;
    }
    Date testdate = date == null ? new Date() : date;
    for (Departement dept : list) {
      if (SharedBusinessRules.isEntiteAdministrativeValid(dept, testdate)) {
        // Suppose database correct (au plus 1 valeur valide)
        return dept;
      }
    }
    return null;
  }

  /**
   * Get the Departement with the given code at the given date.
   * @param code the Departement code.
   * @param date the date at which the code was valid
   * @return the Departement with the given code at the given date.
   */
  public Departement getDepartementByCode(final String code, final String date) {
    logger.info("Departement requested by code and date: code={}, date={}", code, date);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return getDepartementByCode(code, sdf.parse(date));
    }
    catch (ParseException e) {
      logger.warn("Departement requested by code and date: Exception parsing date", e);
      return null;
    }
  }
}
