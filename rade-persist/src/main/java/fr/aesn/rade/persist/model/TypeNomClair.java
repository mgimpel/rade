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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity: Type Nom en Clair de l'Entité Administrative.
 * <pre>
 * CREATE TABLE ZR_TYPENOMCLAIR (
 *   CODE       varchar(1) NOT NULL PRIMARY KEY,
 *   ARTICLE    varchar(5),
 *   CHARNIERE  varchar(6),
 *   ARTICLEMAJ varchar(5)
 * );
 * </pre>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_TYPENOMCLAIR")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
@Builder @AllArgsConstructor(staticName = "of")
public class TypeNomClair implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = -2207036818162244651L;

  /** Code du nom en clair de l'entité administrative. */
  @Size(max = 1)
  @Id
  @Column(name = "CODE", length = 1, nullable = false)
  private String code;

  /** Article du nom en clair de l'entité administrative. */
  @Size(max = 5)
  @Column(name = "ARTICLE", length = 5, nullable = true)
  private String article;

  /** Charnière du nom en clair de l'entité administrative. */
  @Size(max = 6)
  @Column(name = "CHARNIERE", length = 6, nullable = true)
  private String charniere;

  /** Article Majuscule du nom en clair de l'entité administrative. */
  @Size(max = 5)
  @Column(name = "ARTICLEMAJ", length = 5, nullable = true)
  private String articleMaj;
}
