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

import javax.xml.bind.annotation.XmlRootElement;

import fr.aesn.rade.persist.model.TypeNomClair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Type Nom Clair (TNCC) DTO.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "TypeNomClair")
public class TypeNomClairDto {
  private String code;
  private String article;
  private String charniere;
  private String articleMaj;

  /**
   * Set all the variables in this DTO with values from the given Entity.
   * @param tncc Entity used to initialize DTO.
   */
  protected void setAllfromEntity(TypeNomClair tncc) {
    if (tncc == null) {
      return;
    }
    this.setCode(tncc.getCode());
    this.setArticle(tncc.getArticle());
    this.setCharniere(tncc.getCharniere());
    this.setArticleMaj(tncc.getArticleMaj());
  }

  /**
   * Static factory for building DTO from it's associated Entity.
   * @param tncc Entity used to build DTO.
   * @return new DTO built from it's associated Entity.
   */
  public static TypeNomClairDto fromEntity(TypeNomClair tncc) {
    TypeNomClairDto dto = new TypeNomClairDto();
    dto.setAllfromEntity(tncc);
    return dto;
  }
}
