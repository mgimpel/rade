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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * Entity: Commune.
 * <pre>
 * CREATE TABLE ZR_COMMUNE (
 *   ID           integer     NOT NULL PRIMARY KEY,
 *   CODE         varchar(10) NOT NULL,
 *   DEPT         varchar(3)  NOT NULL,
 *   BASSIN       varchar(2)  NOT NULL,
 *   URBAIN_RURAL varchar(1)  NOT NULL,
 *   FOREIGN KEY(BASSIN) REFERENCES ZR_BASSIN,
 *   FOREIGN KEY(ID)     REFERENCES ZR_ENTITEADMIN
 * );
 * </pre>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_COMMUNE")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper = true) @EqualsAndHashCode(callSuper = true)
public class Commune extends EntiteAdministrative {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 704275233608860782L;

  /* Id inherited from parent class */
//  @Id
//  @Column(name = "ID", nullable = false)
//  private int id;

  /** Code INSEE de la Commune. */
  @Size(max = 10)
  @Column(name = "CODE", length = 10, nullable = false)
  private String codeInsee;

  /** Département auquel appartient la Commune. */
  @Size(max = 3)
  @Column(name = "DEPT", length = 3, nullable = false)
  private String departement;

  /** Bassin auquel appartient la Commune. */
  @Size(max = 2)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BASSIN", nullable = true)
  private CirconscriptionBassin circonscriptionBassin;

  /** Indicateur Urbain/Rural de la Commune. */
  @Size(max = 1)
  @Column(name = "URBAIN_RURAL", length = 1, nullable = false)
  private String indicateurUrbain;
}
