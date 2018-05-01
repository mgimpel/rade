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
import java.util.Map;

import fr.aesn.rade.persist.model.StatutModification;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeGenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;

/**
 * Service Interface for Metadata.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface MetadataService {
    /**
     * Gets the TypeNomClair for the given code.
     * @param code the code of the TypeNomClair.
     * @return the TypeNomClair for the given code.
     */
    public TypeNomClair getTypeNomClair(String code);

    /**
     * Gets a list of all the TypeNomClair.
     * @return a list of all the TypeNomClair.
     */
    public List<TypeNomClair> getTypeNomClairList();

    /**
     * Gets a map of all the TypeNomClair indexed by the code.
     * @return a map of all the TypeNomClair indexed by the code.
     */
    public Map<String, TypeNomClair> getTypeNomClairMap();

    /**
     * Gets the TypeEntiteAdmin for the given code.
     * @param code the code of the TypeEntiteAdmin.
     * @return the TypeEntiteAdmin for the given code.
     */
    public TypeEntiteAdmin getTypeEntiteAdmin(String code);

    /**
     * Gets a list of all the TypeEntiteAdmin.
     * @return a list of all the TypeEntiteAdmin.
     */
    public List<TypeEntiteAdmin> getTypeEntiteAdminList();

    /**
     * Gets a map of all the TypeEntiteAdmin indexed by the code.
     * @return a map of all the TypeEntiteAdmin indexed by the code.
     */
    public Map<String, TypeEntiteAdmin> getTypeEntiteAdminMap();

    /**
     * Gets the TypeGenealogieEntiteAdmin for the given code.
     * @param code the code of the TypeGenealogieEntiteAdmin.
     * @return the TypeGenealogieEntiteAdmin for the given code.
     */
    public TypeGenealogieEntiteAdmin getTypeGenealogieEntiteAdmin(String code);

    /**
     * Gets a list of all the TypeGenealogieEntiteAdmin.
     * @return a list of all the TypeGenealogieEntiteAdmin.
     */
    public List<TypeGenealogieEntiteAdmin> getTypeGenealogieEntiteAdminList();

    /**
     * Gets a map of all the TypeGenealogieEntiteAdmin indexed by the code.
     * @return a map of all the TypeGenealogieEntiteAdmin indexed by the code.
     */

    public Map<String, TypeGenealogieEntiteAdmin> getTypeGenealogieEntiteAdminMap();
    /**
     * Gets the StatutModification for the given code.
     * @param code the code of the StatutModification.
     * @return the StatutModification for the given code.
     */
    public StatutModification getStatutModification(String code);

    /**
     * Gets a list of all the StatutModification.
     * @return a list of all the StatutModification.
     */
    public List<StatutModification> getStatutModificationList();

    /**
     * Gets a map of all the StatutModification indexed by the code.
     * @return a map of all the StatutModification indexed by the code.
     */
    public Map<String, StatutModification> getStatutModificationMap();
}
