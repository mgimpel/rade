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

import fr.aesn.rade.persist.model.CirconscriptionBassin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Circonscription Bassin DTO.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "CirconscriptionBassin")
public class CirconscriptionBassinDto {
  /** Code du Bassin. */
  private String code;
  /** Libellé Court du Bassin. */
  private String libelleCourt;
  /** Libellé Long du Bassin. */
  private String libelleLong;

  /**
   * Set all the variables in this DTO with values from the given Entity.
   * @param bassin Entity used to initialize DTO.
   */
  protected void setAllfromCirconscriptionBassinEntity(final CirconscriptionBassin bassin) {
    if (bassin == null) {
      return;
    }
    this.setCode(bassin.getCode());
    this.setLibelleCourt(bassin.getLibelleCourt());
    this.setLibelleLong(bassin.getLibelleLong());
  }

  /**
   * Static factory for building DTO from it's associated Entity.
   * @param bassin Entity used to build DTO.
   * @return new DTO built from it's associated Entity.
   */
  public static CirconscriptionBassinDto fromEntity(final CirconscriptionBassin bassin) {
    CirconscriptionBassinDto dto = new CirconscriptionBassinDto();
    dto.setAllfromCirconscriptionBassinEntity(bassin);
    return dto;
  }
}
