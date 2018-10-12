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

import fr.aesn.rade.persist.model.Departement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Departement DTO.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "Departement")
public class DepartementDto
  extends EntiteAdministrativeDto {
  /** Code INSEE du département. */
  private String codeInsee;
  /** Chef-lieu du département. */
  private String chefLieu;
  /** Région à laquelle appartient le département. */
  private String region;

  /**
   * Set all the variables in this DTO with values from the given Entity.
   * @param dept Entity used to initialize DTO.
   */
  protected void setAllfromDepartementEntity(final Departement dept) {
    if (dept == null) {
      return;
    }
    setAllfromEntity(dept);
    this.setCodeInsee(dept.getCodeInsee());
    this.setChefLieu(dept.getChefLieu());
    this.setRegion(dept.getRegion());
  }

  /**
   * Static factory for building DTO from it's associated Entity.
   * @param dept Entity used to build DTO.
   * @return new DTO built from it's associated Entity.
   */
  public static DepartementDto fromEntity(final Departement dept) {
    DepartementDto dto = new DepartementDto();
    dto.setAllfromDepartementEntity(dept);
    return dto;
  }
}
