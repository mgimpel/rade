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
  /** Code de la Délégation. */
  private String code;
  /** Libellé de la Délégation. */
  private String libelle;
  /** Acheminement de la Délégation. */
  private String acheminement;
  /** Adresse (ligne 1) de la Délégation. */
  private String adresse1;
  /** Adresse (ligne 2) de la Délégation. */
  private String adresse2;
  /** Adresse (ligne 3) de la Délégation. */
  private String adresse3;
  /** Adresse (ligne 4) de la Délégation. */
  private String adresse4;
  /** Adresse (ligne 5) de la Délégation. */
  private String adresse5;
  /** Code Postale de la Délégation. */
  private String codePostal;
  /** Adresse E-mail de la Délégation. */
  private String email;
  /** Numéro Fax de la Délégation. */
  private String fax;
  /** Site Web de la Délégation. */
  private String siteWeb;
  /** Numéro de Téléphone de la Délégation. */
  private String telephone;
  /** Numéro de Téléphone (2ème) de la Délégation. */
  private String telephone2;
  /** Numéro de Téléphone (3ème) de la Délégation. */
  private String telephone3;

  /**
   * Set all the variables in this DTO with values from the given Entity.
   * @param delegation Entity used to initialize DTO.
   */
  protected void setAllfromDelegationEntity(final Delegation delegation) {
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

  /**
   * Static factory for building DTO from it's associated Entity.
   * @param delegation Entity used to build DTO.
   * @return new DTO built from it's associated Entity.
   */
  public static DelegationDto fromEntity(final Delegation delegation) {
    DelegationDto dto = new DelegationDto();
    dto.setAllfromDelegationEntity(delegation);
    return dto;
  }
}
