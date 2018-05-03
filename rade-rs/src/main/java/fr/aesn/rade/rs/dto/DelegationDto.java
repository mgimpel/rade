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

import fr.aesn.rade.persist.model.Delegation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Delegation DTO.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@XmlRootElement(name = "Delegation")
public class DelegationDto {
  private String code;
  private String libelle;
  private String acheminement;
  private String adresse1;
  private String adresse2;
  private String adresse3;
  private String adresse4;
  private String adresse5;
  private String codePostal;
  private String email;
  private String fax;
  private String siteWeb;
  private String telephone;
  private String telephone2;
  private String telephone3;

  protected void setAllfromEntity(Delegation delegation) {
    if (delegation == null) {
      return;
    }
    this.setCode(delegation.getCode());
    this.setLibelle(delegation.getLibelle());
    this.setAcheminement(delegation.getAcheminement());
    this.setAdresse1(delegation.getAdresse1());
    this.setAdresse2(delegation.getAdresse2());
    this.setAdresse3(delegation.getAdresse3());
    this.setAdresse4(delegation.getAdresse4());
    this.setAdresse5(delegation.getAdresse5());
    this.setCodePostal(delegation.getCodePostal());
    this.setEmail(delegation.getEmail());
    this.setFax(delegation.getFax());
    this.setSiteWeb(delegation.getSiteWeb());
    this.setTelephone(delegation.getTelephone());
    this.setTelephone2(delegation.getTelephone2());
    this.setTelephone3(delegation.getTelephone3());
  }

  public static DelegationDto fromEntity(Delegation delegation) {
    DelegationDto dto = new DelegationDto();
    dto.setAllfromEntity(delegation);
    return dto;
  }
}
