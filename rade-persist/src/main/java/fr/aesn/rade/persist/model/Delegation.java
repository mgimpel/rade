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
package fr.aesn.rade.persist.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Delegation Entity.
 * <pre>
 * CREATE TABLE ZR_DELEGATION (
 *   CODE         varchar(5)   NOT NULL PRIMARY KEY,
 *   LIBELLE      varchar(60)  NOT NULL,
 *   ACHEMINEMENT varchar(255) NOT NULL,
 *   ADRESSE1     varchar(255),
 *   ADRESSE2     varchar(255),
 *   ADRESSE3     varchar(255),
 *   ADRESSE4     varchar(255),
 *   ADRESSE5     varchar(255),
 *   CODE_POSTAL  varchar(5)   NOT NULL,
 *   EMAIL        varchar(255),
 *   FAX          varchar(255),
 *   SITEWEB      varchar(255),
 *   TELEPHONE    varchar(255) NOT NULL,
 *   TELEPHONE2   varchar(255),
 *   TELEPHONE3   varchar(255)
 * );
 * </pre>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_DELEGATION")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Delegation implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 6620777417437552115L;

  /** Code de la Délégation. */
  @Size(max = 5)
  @Id
  @Column(name = "CODE", length = 5, nullable = false)
  private String code;

  /** Libellé de la Délégation. */
  @Size(max = 60)
  @Column(name = "LIBELLE", length = 60, nullable = false)
  private String libelle;

  /** Acheminement de la Délégation. */
  @Size(max = 255)
  @Column(name = "ACHEMINEMENT", length = 255, nullable = false)
  private String acheminement;

  /** Adresse (ligne 1) de la Délégation. */
  @Size(max = 255)
  @Column(name = "ADRESSE1", length = 255, nullable = false)
  private String adresse1;

  /** Adresse (ligne 2) de la Délégation. */
  @Size(max = 255)
  @Column(name = "ADRESSE2", length = 255)
  private String adresse2;

  /** Adresse (ligne 3) de la Délégation. */
  @Size(max = 255)
  @Column(name = "ADRESSE3", length = 255)
  private String adresse3;

  /** Adresse (ligne 4) de la Délégation. */
  @Size(max = 255)
  @Column(name = "ADRESSE4", length = 255)
  private String adresse4;

  /** Adresse (ligne 5) de la Délégation. */
  @Size(max = 255)
  @Column(name = "ADRESSE5", length = 255)
  private String adresse5;

  /** Code Postale de la Délégation. */
  @Size(max = 5)
  @Column(name = "CODE_POSTAL", length = 5, nullable = false)
  private String codePostal;

  /** Adresse E-mail de la Délégation. */
  @Size(max = 255)
  @Column(name = "EMAIL", length = 255)
  private String email;

  /** Numéro Fax de la Délégation. */
  @Size(max = 255)
  @Column(name = "FAX", length = 255)
  private String fax;

  /** Numéro Fax de la Délégation. */
  @Size(max = 255)
  @Column(name = "SITEWEB", length = 255)
  private String siteWeb;

  /** Numéro de Téléphone de la Délégation. */
  @Size(max = 255)
  @Column(name = "TELEPHONE", length = 255, nullable = false)
  private String telephone;

  /** Numéro de Téléphone (2ème) de la Délégation. */
  @Size(max = 255)
  @Column(name = "TELEPHONE2", length = 255)
  private String telephone2;

  /** Numéro de Téléphone (3ème) de la Délégation. */
  @Size(max = 255)
  @Column(name = "TELEPHONE3", length = 255)
  private String telephone3;
}
