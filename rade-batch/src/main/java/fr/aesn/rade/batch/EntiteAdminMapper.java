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
package fr.aesn.rade.batch;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.MetadataService;
import lombok.Setter;

/**
 * Abstact FieldSetMapper for Mapping INSEE files.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public abstract class EntiteAdminMapper {
  /** Service for Metadata. */
  @Autowired @Setter
  private MetadataService metadataService;
  /** Map to TypeNomClair from database. */
  private Map<String, TypeNomClair> tncc;
  /** Map of TypeEntiteAdmin from database. */
  private Map<String, TypeEntiteAdmin> tea;

  /**
   * Returns the TypeNomClair for the given code.
   * @param code the TNCC code
   * @return the TypeNomClair.
   */
  protected TypeNomClair getTypeNomClair(final String code) {
    if (tncc == null) {
      tncc = metadataService.getTypeNomClairMap();
    }
    return tncc.get(code);
  }

  /**
   * Returns the TypeEntiteAdmin for the given code.
   * @param code the TypeEntiteAdmin code
   * @return the TypeEntiteAdmin.
   */
  protected TypeEntiteAdmin getTypeEntiteAdmin(final String code) {
    if (tea == null) {
      tea = metadataService.getTypeEntiteAdminMap();
    }
    return tea.get(code);
  }
}
