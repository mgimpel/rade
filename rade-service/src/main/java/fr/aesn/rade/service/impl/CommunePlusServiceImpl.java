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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.CommuneSandreJpaDao;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.service.CommunePlusService;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Service Implementation for Commune.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor
public class CommunePlusServiceImpl
  implements CommunePlusService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(CommunePlusServiceImpl.class);
  /** Data Access Object for Commune. */
  @Autowired @Setter
  private CommuneJpaDao communeJpaDao;
  /** Data Access Object for CommuneSandre. */
  @Autowired @Setter
  private CommuneSandreJpaDao communeSandreJpaDao;

  /**
   * List all Commune valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Commune.
   */
  @Override
  @Transactional(readOnly = true)
  public List<CommunePlus> getAllCommune(final Date date) {
    log.debug("Commune list requested for Date: date={}", date);
    Date testdate = (date == null ? new Date() : date);
    List<Commune> listInsee = communeJpaDao.findAllValidOnDate(testdate);
    List<CommuneSandre> listSandre = communeSandreJpaDao.findAllValidOnDate(testdate);
    HashMap<String, CommuneSandre> map = new HashMap<>(listSandre.size());
    for (CommuneSandre item : listSandre) {
      map.put(item.getCodeCommune(), item);
    }
    String code;
    CommunePlus commune;
    CommuneSandre sandre;
    List<CommunePlus> result = new ArrayList<>(listInsee.size());
    for (Commune item : listInsee) {
      if (item != null) {
        code = item.getCodeInsee();
        try {
          commune = new CommunePlus(code, testdate);
          commune.setCommuneInsee(item);
          sandre = map.get(code);
          if (sandre != null) {
            commune.setCommuneSandre(sandre);
          }
          result.add(commune);
        }
        catch (InvalidArgumentException e) {
          log.warn("Error assembling CommunePlus for Commune {} on {}", code, testdate, e);
        }
      }
    }
    return result;
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public CommunePlus getCommuneByCode(final String code, final Date date) {
    log.debug("Commune requested by code and date: code={}, date={}", code, date);
    if (code == null) {
      return null;
    }
    Date testdate = (date == null ? new Date() : date);
    Commune insee = communeJpaDao.findByCodeInseeValidOnDate(code, testdate);
    if (insee == null) {
      return null;
    }
    CommuneSandre sandre = communeSandreJpaDao.findByCodeInseeValidOnDate(code, testdate);
    CommunePlus result = new CommunePlus(code, testdate);
    try {
      result.setCommuneInsee(insee);
      if (sandre != null) {
        result.setCommuneSandre(sandre);
      }
    }
    catch (InvalidArgumentException e) {
      log.warn("Error assembling CommunePlus for Commune {} on {}", code, testdate, e);
    }
    return result;
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public CommunePlus getCommuneByCode(final String code, final String date) {
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
