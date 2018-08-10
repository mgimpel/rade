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
package fr.aesn.rade.rs.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.TypeNomClair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entite Administrative DTO (for Communes, Departements and Regions).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
public abstract class EntiteAdministrativeDto {
  /** Date de début de Validité de l'entité. */
  private String debutValidite;
  /** Date de fin de Validité de l'entité. */
  private String finValidite;
  /** Article (enrichie) de l'entité. */
  private String articleEnrichi;
  /** Nom (en majuscule) de l'entité. */
  private String nomMajuscule;
  /** Nom (enrichie) de l'entité. */
  private String nomEnrichi;
  /** Commentaire. */
  private String commentaire;
  /** Type de Nom Clair (TNCC) de l'entité. */
  private TypeNomClairDto typeNomClair;

  /**
   * Set all the variables in this DTO with values from the given Entity.
   * @param entity Entity used to initialize DTO.
   */
  protected void setAllfromEntity(final EntiteAdministrative entity) {
    if (entity == null) {
      return;
    }
    Date date;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    date = entity.getDebutValidite();
    this.setDebutValidite(date != null ? sdf.format(date) : null);
    date = entity.getFinValidite();
    this.setFinValidite(date != null ? sdf.format(date) : null);
    this.setArticleEnrichi(entity.getArticleEnrichi());
    this.setNomMajuscule(entity.getNomMajuscule());
    this.setNomEnrichi(entity.getNomEnrichi());
    this.setCommentaire(entity.getCommentaire());
    TypeNomClair tncc = entity.getTypeNomClair();
    this.setTypeNomClair(tncc != null ? TypeNomClairDto.fromEntity(tncc) : null);
  }
}
