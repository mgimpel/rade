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
package fr.aesn.rade.ws.impl;

import java.util.Collections;
import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.aesn.rade.service.HexaposteService;
import fr.aesn.rade.ws.HexaposteWebService;
import lombok.Setter;

/**
 * Hexaposte WebService Implementation.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@WebService(endpointInterface = "fr.aesn.rade.ws.HexaposteWebService",
            serviceName = "HexaposteWebService")
public class HexaposteWebServiceImpl
  implements HexaposteWebService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HexaposteWebServiceImpl.class);
  /** Hexaposte Service. */
  @Setter
  private HexaposteService hexaposteService;

  /**
   * Get all current Libelle d'acheminement for the given Code Postal.
   * @param codePostal Code Postal of the Commune 
   * @return a list of all Libelle d'acheminement for the given Code Postal.
   */
  @Override
  public List<String> getLibelleAcheminementByCodePostal(String codePostal) {
    log.info("Executing operation getLibelleAcheminementByCodePostal for code postale: {}", codePostal);
    if (hexaposteService == null) {
      log.error("Could not getLibelleAcheminementByCodePostal, service was null (configuration error)");
      return Collections.<String>emptyList();
    }
    return hexaposteService.getLibelleAcheminementByCodePostal(codePostal);
  }
}
