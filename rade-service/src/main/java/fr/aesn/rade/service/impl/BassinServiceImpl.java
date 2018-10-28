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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.CirconscriptionBassinJpaDao;
import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.service.BassinService;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Service Implementation for Circonscription Bassin.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor
public class BassinServiceImpl
  implements BassinService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(BassinServiceImpl.class);
  /** Data Access Object for CirconscriptionBassin. */
  @Autowired @Setter
  private CirconscriptionBassinJpaDao circonscriptionBassinJpaDao;

  @Override
  @Transactional(readOnly = true)
  public List<CirconscriptionBassin> getAllBassin() {
    log.debug("Bassin list requested");
    return circonscriptionBassinJpaDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, CirconscriptionBassin> getBassinMap() {
    log.debug("Bassin map requested");
    List<CirconscriptionBassin> list = getAllBassin();
    HashMap<String, CirconscriptionBassin> map = new HashMap<>(list.size());
    for (CirconscriptionBassin item : list) {
      map.put(item.getCode(), item);
    }
    return map;
  }

  @Override
  @Transactional(readOnly = true)
  public CirconscriptionBassin getBassinByCode(final String code) {
    log.debug("Bassin requested by ID: code={}", code);
    Optional<CirconscriptionBassin> result = circonscriptionBassinJpaDao.findById(code);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
