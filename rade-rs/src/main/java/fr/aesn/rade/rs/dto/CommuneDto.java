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
import fr.aesn.rade.persist.model.Commune;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Commune DTO.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "Commune")
public class CommuneDto
  extends EntiteAdministrativeDto {
  /** Code INSEE de la Commune. */
  private String codeInsee;
  /** Département auquel appartient la Commune. */
  private String departement;
  /** Bassin auquel appartient la Commune. */
  private CirconscriptionBassinDto circonscriptionBassin;
  /** Indicateur Urbain/Rural de la Commune. */
  private String indicateurUrbain;

  /**
   * Set all the variables in this DTO with values from the given Entity.
   * @param commune Entity used to initialize DTO.
   */
  protected void setAllfromCommuneEntity(final Commune commune) {
    if (commune == null) {
      return;
    }
    setAllfromEntity(commune);
    this.setCodeInsee(commune.getCodeInsee());
    this.setDepartement(commune.getDepartement());
    CirconscriptionBassin bassin = commune.getCirconscriptionBassin();
    this.setCirconscriptionBassin(bassin != null ? CirconscriptionBassinDto.fromEntity(bassin) : null);
    this.setIndicateurUrbain(commune.getIndicateurUrbain());
  }

  /**
   * Static factory for building DTO from it's associated Entity.
   * @param commune Entity used to build DTO.
   * @return new DTO built from it's associated Entity.
   */
  public static CommuneDto fromEntity(final Commune commune) {
    CommuneDto dto = new CommuneDto();
    dto.setAllfromCommuneEntity(commune);
    return dto;
  }
}
