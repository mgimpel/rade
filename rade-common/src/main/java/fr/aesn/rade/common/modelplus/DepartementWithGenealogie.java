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
package fr.aesn.rade.common.modelplus;

import java.util.HashMap;
import java.util.Map;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.TypeGenealogieEntiteAdmin;
import lombok.Getter;

/**
 * @author Marc Gimpel (mgimpel@gmail.com)
 *
 */
public class DepartementWithGenealogie {
  /** The Enhanced Commune details. */
  @Getter
  private final Departement departement;
  /** Map of Parent Communes and modification details, indexed by codeINSEE. */
  @Getter
  private final Map<String, GenealogieSimple> parents;
  /** Map of Child Communes and modification details, indexed by codeINSEE. */
  @Getter
  private final Map<String, GenealogieSimple> enfants;

  /**
   * Constructor.
   * @param departement
   */
  public DepartementWithGenealogie(Departement departement) {
    this.departement = departement;
    parents = new HashMap<>();
    enfants = new HashMap<>();
  }

  /**
   * Add a parent genealogie detail.
   * @param type Modification type of the parent Commune.
   * @param entity Parent Commune Entity.
   * @throws InvalidArgumentException if an argument was null.
   */
  public void addParent(TypeGenealogieEntiteAdmin type,
                        Departement entity)
    throws InvalidArgumentException {
    if (type == null || entity == null) {
      throw new InvalidArgumentException("Mandatory argument was null.");
    }
    if (!"DEP".equals(type.getCode())) {
      throw new InvalidArgumentException("Incoherant Genealogie type.");
    }
    parents.put(entity.getCodeInsee(),
                new GenealogieSimple(type, null, entity));
  }

  /**
   * Add a child genealogie detail.
   * @param type Modification type of the child Commune.
   * @param entity Child Commune Entity.
   * @throws InvalidArgumentException if an argument was null.
   */
  public void addEnfant(TypeGenealogieEntiteAdmin type,
                        Departement entity)
    throws InvalidArgumentException {
    if (type == null || entity == null) {
      throw new InvalidArgumentException("Mandatory argument was null.");
    }
    if (!"DEP".equals(type.getCode())) {
      throw new InvalidArgumentException("Incoherant Genealogie type.");
    }
    enfants.put(entity.getCodeInsee(),
                new GenealogieSimple(type, null, entity));
  }
}
