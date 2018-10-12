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
package fr.aesn.rade.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Hexaposte Web Service.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@WebService
public interface HexaposteWebService {
    /**
     * Get all current Libelle d'acheminement for the given Code Postal.
     * @param codePostal Code Postal of the Commune 
     * @return a list of all Libelle d'acheminement for the given Code Postal.
     */
  @WebMethod
  public List<String> getLibelleAcheminementByCodePostal(String codePostal);
}
