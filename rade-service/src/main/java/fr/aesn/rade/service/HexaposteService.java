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

import java.util.List;

import fr.aesn.rade.persist.model.Hexaposte;

/**
 * Service Interface for Hexaposte.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface HexaposteService {
    /**
     * Get all current Hexaposte records for the given Code Postal.
     * @param codePostal Code Postal of the Commune 
     * @return a list of all Hexaposte records for the given Code Postal.
     */
    public List<Hexaposte> getHexposteByCodePostal(String codePostal);

    /**
     * Get all current Libelle d'acheminement for the given Code Postal.
     * @param codePostal Code Postal of the Commune 
     * @return a list of all Libelle d'acheminement for the given Code Postal.
     */
    public List<String> getLibelleAcheminementByCodePostal(String codePostal);
}
