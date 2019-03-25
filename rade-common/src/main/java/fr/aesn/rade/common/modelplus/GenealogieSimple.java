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

import java.util.Date;

import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeGenealogieEntiteAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Genealogie details.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@AllArgsConstructor
public class GenealogieSimple {
  /** Modification type. */
  @Getter
  private final TypeGenealogieEntiteAdmin typeModification;
  /** Modification comment. */
  @Getter
  private final String commentaire;
  /** Parent/Child Entity type. */
  @Getter
  private final TypeEntiteAdmin typeEntiteAdmin;
  /** Parent/Child Entity code. */
  @Getter
  private final String code;
  /** Parent/Child Entity name. */
  @Getter
  private final String nom;
  /** Parent/Child Entity start. */
  @Getter
  private final Date debutValidite;
  /** Parent/Child Entity end. */
  @Getter
  private final Date finValidite;

  /**
   * Constructor.
   * @param typeModification Modification type.
   * @param commentaire Modification comment.
   * @param code Parent/Child Entity INSEE code.
   * @param entity Parent/Child Entity.
   */
  public GenealogieSimple(final TypeGenealogieEntiteAdmin typeModification,
                          final String commentaire,
                          final String code,
                          final EntiteAdministrative entity) {
    this(typeModification,
         commentaire,
         entity.getTypeEntiteAdmin(),
         code,
         entity.getNomEnrichi(),
         entity.getDebutValidite(),
         entity.getFinValidite());
  }

  /**
   * Constructor.
   * @param typeModification Modification type.
   * @param commentaire Modification comment.
   * @param commune Parent/Child Entity.
   */
  public GenealogieSimple(final TypeGenealogieEntiteAdmin typeModification,
                          final String commentaire,
                          final Commune commune) {
    this(typeModification,
         commentaire,
         commune.getCodeInsee(),
         commune);
  }

  /**
   * Constructor.
   * @param typeModification Modification type.
   * @param commentaire Modification comment.
   * @param departement Parent/Child Entity.
   */
  public GenealogieSimple(final TypeGenealogieEntiteAdmin typeModification,
                          final String commentaire,
                          final Departement departement) {
    this(typeModification,
         commentaire,
         departement.getCodeInsee(),
         departement);
  }

  /**
   * Constructor.
   * @param typeModification Modification type.
   * @param commentaire Modification comment.
   * @param region Parent/Child Entity.
   */
  public GenealogieSimple(final TypeGenealogieEntiteAdmin typeModification,
                          final String commentaire,
                          final Region region) {
    this(typeModification,
         commentaire,
         region.getCodeInsee(),
         region);
  }
}
