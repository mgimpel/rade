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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity: Region.
 * <pre>
 * CREATE TABLE ZR_REGION (
 *   ID        integer     NOT NULL PRIMARY KEY,
 *   CODE      varchar(2)  NOT NULL,
 *   CHEF_LIEU varchar(10) NOT NULL,
 *   FOREIGN KEY(ID)        REFERENCES ZR_ENTITEADMIN
 * );
 * </pre>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_REGION")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper = true) @EqualsAndHashCode(callSuper = true)
public class Region extends EntiteAdministrative {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = -2035518317003975459L;

  /* Id inherited from parent class */
//  @Id
//  @Column(name = "ID", nullable = false)
//  private int id;

  /** Code INSEE de la région. */
  @Size(max = 2)
  @Column(name = "CODE", length = 2, nullable = false)
  private String codeInsee;

  /** Chef-lieu de la région. */
  @Size(max = 10)
  @Column(name = "CHEF_LIEU", length = 10, nullable = false)
  private String chefLieu;
}
