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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.HexaposteJpaDao;
import fr.aesn.rade.persist.model.Hexaposte;
import fr.aesn.rade.service.HexaposteService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for Hexaposte.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor @Slf4j
public class HexaposteServiceImpl
  implements HexaposteService {
  /** Data Access Object for CirconscriptionBassin. */
  @Autowired @Setter
  private HexaposteJpaDao hexaposteJpaDao;

  /**
   * Get all current Hexaposte records for the given Code Postal.
   * @param codePostal Code Postal of the Commune 
   * @return a list of all Hexapost records for the given Code Postale.
   */
  public List<Hexaposte> getHexposteByCodePostal(final String codePostal) {
    log.debug("Hexaposte requested by Code Postal: CodePostal={}", codePostal);
    return hexaposteJpaDao.findByCodePostal(codePostal);
  }

  /**
   * Get all current Libelle d'acheminement for the given Code Postal.
   * @param codePostal Code Postal of the Commune 
   * @return a list of all Libelle d'acheminement for the given Code Postal.
   */
  public List<String> getLibelleAcheminementByCodePostal(final String codePostal) {
    log.debug("Libelle d'acheminement requested by Code Postal: CodePostal={}", codePostal);
    List<Hexaposte> list = getHexposteByCodePostal(codePostal);
    if (list == null) {
      return null;
    }
    List<String> libelle = new ArrayList<>(list.size());
    for (Hexaposte hexaposte : list) {
      libelle.add(hexaposte.getLibelleAcheminement());
    }
    return libelle;
  }
}
