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
 * Entity: Statut Modification.
 * <pre>
 * CREATE TABLE ZR_STATUTMODIF (
 *   CODE          varchar(1)   NOT NULL PRIMARY KEY,
 *   LIBELLE_COURT varchar(25)  NOT NULL,
 *   LIBELLE_LONG  varchar(250) NOT NULL
 * );
 * </pre>
 * Le statut désigne l'action à engager suite à un événement, ex:
 * <table summary="">
 * <tr><th> Code </th><th> Libellé </th></tr>
 * <tr><td> I    </td><td> Identifié (à traiter) </td></tr>
 * <tr><td> P    </td><td> Prise en compte </td></tr>
 * <tr><td> N    </td><td> Non concerné </td></tr>
 * </table>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_STATUTMODIF")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class StatutModification implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 6818270193927456285L;

  /** Code du Statut de Modification. */
  @Size(max = 1)
  @Id
  @Column(name = "CODE", length = 1, nullable = false)
  private String code;

  /** Libellé court du Statut de Modification. */
  @Size(max = 25)
  @Column(name = "LIBELLE_COURT", length = 25, nullable = false)
  private String libelleCourt;

  /** Libellé court du Statut de Modification. */
  @Size(max = 250)
  @Column(name = "LIBELLE_LONG", length = 250, nullable = false)
  private String libelleLong;
}
