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

import fr.aesn.rade.persist.model.Delegation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * List of Delegation DTOs.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "Delegations")
public class DelegationListDto {
  /** Delegation List. */
  @XmlElement(name = "delegations")
  private List<DelegationDto> delegations;

  /**
   * Static factory for building DTO List from it's associated Entity List.
   * @param delegations Entity List used to build DTO.
   * @return new DTO List built from it's associated Entity List.
   */
  public static DelegationListDto fromEntityList(final List<Delegation> delegations) {
    List<DelegationDto> dtos = new ArrayList<>(delegations.size());
    for (Delegation delegation : delegations) {
      if (delegation != null) {
        dtos.add(DelegationDto.fromEntity(delegation));
      }
    }
    DelegationListDto dto = new DelegationListDto();
    dto.setDelegations(dtos);
    return dto;
  }
}
