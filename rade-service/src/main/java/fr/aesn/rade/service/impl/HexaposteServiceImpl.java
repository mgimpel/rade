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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.HexaposteJpaDao;
import fr.aesn.rade.persist.model.Hexaposte;
import fr.aesn.rade.service.HexaposteService;

/**
 * Service Implementation for Hexaposte.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
public class HexaposteServiceImpl
  implements HexaposteService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HexaposteServiceImpl.class);
  /** Data Access Object for CirconscriptionBassin. */
  @Autowired
  private HexaposteJpaDao hexaposteJpaDao;


  /**
   * Empty Constructor for Bean.
   */
  public HexaposteServiceImpl() {
    // Empty Constructor for Bean.
  }

  /**
   * Standard Constructor.
   * @param hexaposteJpaDao Data Access Object for Hexaposte.
   */
  public HexaposteServiceImpl(final HexaposteJpaDao hexaposteJpaDao) {
    setHexaposteJpaDao(hexaposteJpaDao);
  }

  /**
   * Sets the Data Access Object for Hexaposte.
   * @param hexaposteJpaDao Data Access Object for Hexaposte.
   */
  public void setHexaposteJpaDao(final HexaposteJpaDao hexaposteJpaDao) {
    this.hexaposteJpaDao = hexaposteJpaDao;
  }

  /**
   * Get all current Hexaposte records for the given Code Postale.
   * @param codePostale Code Postale of the Commune 
   * @return a list of all Hexaposte records for the given Code Postale.
   */
  public List<Hexaposte> getHexposteByCodePostale (final String codePostale) {
    log.debug("Hexaposte requested by Code Postale: CodePostale={}", codePostale);
    //TODO
    return hexaposteJpaDao.findAll();
  }

  /**
   * Get all current Libelle d'acheminement for the given Code Postale.
   * @param codePostale Code Postale of the Commune 
   * @return a list of all Libelle d'acheminement for the given Code Postale.
   */
  public List<String> getLibelleAcheminementByCodePostale (final String codePostale) {
    log.debug("Libelle d'acheminement requested by Code Postale: CodePostale={}", codePostale);
    //TODO
    return null;
  }
}
