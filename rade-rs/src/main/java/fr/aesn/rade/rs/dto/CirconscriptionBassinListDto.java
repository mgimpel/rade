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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fr.aesn.rade.persist.model.CirconscriptionBassin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * List of Circonscription Bassin DTOs.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "CirconscriptionBassins")
public class CirconscriptionBassinListDto {
  @XmlElement(name = "bassins")
  private List<CirconscriptionBassinDto> bassins;

  /**
   * Static factory for building DTO List from it's associated Entity List.
   * @param bassins Entity List used to build DTO.
   * @return new DTO List built from it's associated Entity List.
   */
  public static CirconscriptionBassinListDto fromEntityList(List<CirconscriptionBassin> bassins) {
    List<CirconscriptionBassinDto> dtos = new ArrayList<>(bassins.size());
    for (CirconscriptionBassin bassin : bassins) {
      dtos.add(CirconscriptionBassinDto.fromEntity(bassin));
    }
    CirconscriptionBassinListDto dto = new CirconscriptionBassinListDto();
    dto.setBassins(dtos);
    return dto;
  }
}
