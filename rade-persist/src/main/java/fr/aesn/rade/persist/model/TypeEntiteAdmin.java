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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity: Type Entité Administrative.
 * <pre>
 * CREATE TABLE ZR_TYPEENTITEADMIN (
 *   CODE          varchar(3)  NOT NULL PRIMARY KEY,
 *   LIBELLE_COURT varchar(20) NOT NULL
 * );
 * </pre>
 * Défini une liste de différents types d'entités administratives, ex:
 * <table style="border: 1px solid #888; border-spacing: 20px 0px;" summary="">
 * <tr><th> Code </th><th> Libellé </th></tr>
 * <tr><td> COM  </td><td> Commune </td></tr>
 * <tr><td> ARR  </td><td> Arrondissement </td></tr>
 * <tr><td> CAN  </td><td> Canton </td></tr>
 * <tr><td> DEP  </td><td> Département </td></tr>
 * <tr><td> REG  </td><td> Région </td></tr>
 * </table>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_TYPEENTITEADMIN")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class TypeEntiteAdmin implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = -6351694759103307417L;

  /**
   * Code du Type d'entité administrative (ex: COM, ARR, CAN, DEP, REG).
   */
  @Size(max = 3)
  @Id
  @Column(name = "CODE", length = 3, nullable = false)
  private String code;

  /**
   * Libellé court du Type d'entité administrative
   * (ex: Commune, Arrondissement, Canton, Département, Région).
   */
  @Size(max = 20)
  @Column(name = "LIBELLE_COURT", length = 20, nullable = false)
  private String libelleCourt;
}
