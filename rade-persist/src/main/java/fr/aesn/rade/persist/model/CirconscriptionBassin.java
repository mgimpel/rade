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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity: CirconscriptionBassin.
 * <pre>
 * CREATE TABLE ZR_BASSIN (
 *   CODE          varchar(2)  NOT NULL PRIMARY KEY,
 *   LIBELLE_COURT varchar(5)  NOT NULL,
 *   LIBELLE_LONG  varchar(70) NOT NULL,
 *   AUDIT_ID      integer     NOT NULL,
 *   FOREIGN KEY(AUDIT_ID) REFERENCES ZR_AUDIT
 * );
 * </pre>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_BASSIN")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class CirconscriptionBassin implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 8540064762489843689L;

  /** Code du Bassin. */
  @Size(max = 2)
  @Id
  @Column(name = "CODE", length = 2, nullable = false)
  private String code;

  /** Libellé Court du Bassin. */
  @Size(max = 5)
  @Column(name = "LIBELLE_COURT", length = 5, nullable = false)
  private String libelleCourt;

  /** Libellé Long du Bassin. */
  @Size(max = 70)
  @Column(name = "LIBELLE_LONG", length = 70, nullable = false)
  private String libelleLong;

  /** Détails de Modification de l'entité. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUDIT_ID", nullable = false)
  private Audit audit;
}
