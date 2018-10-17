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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity: Type de Généalogie d'Entité Administrative.
 * <pre>
 * CREATE TABLE ZR_TYPEGENEALOGIE (
 *   CODE          varchar(2)   NOT NULL PRIMARY KEY,
 *   LIBELLE_COURT varchar(20)  NOT NULL,
 *   LIBELLE_LONG  varchar(100) NOT NULL,
 *   STATUT_DEFAUT varchar(1),
 *   FOREIGN KEY(STATUT_DEFAUT) REFERENCES ZR_STATUTMODIF
 * );
 * </pre>
 * Défini une liste de différents types de généalogies, ex:
 * <table summary="">
 * <tr><th> Code </th><th> Libellé </th></tr>
 * <tr><td> 10   </td><td> Changement de nom </td></tr>
 * <tr><td> 11   </td><td> Changement de nom dû à une fusion (simple ou association) </td></tr>
 * <tr><td> 12   </td><td> Changement de nom dû à un rétablissement </td></tr>
 * <tr><td> 13   </td><td> Changement de nom dû au changement de nom du chef-lieu </td></tr>
 * <tr><td> 14   </td><td> Changement de nom dû au transfert du chef-lieu </td></tr>
 * <tr><td> 20   </td><td> Création </td></tr>
 * <tr><td> 21   </td><td> Rétablissement </td></tr>
 * <tr><td> ...  </td><td> ... </td></tr>
 * </table>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_TYPEGENEALOGIE")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class TypeGenealogieEntiteAdmin implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 885367586801528497L;

  /** Code du Type de généalogie d'entité administrative. */
  @Size(max = 3)
  @Id
  @Column(name = "CODE", length = 3, nullable = false)
  private String code;

  /** Libellé court du Type de généalogie d'entité administrative. */
  @Size(max = 20)
  @Column(name = "LIBELLE_COURT", length = 20, nullable = false)
  private String libelleCourt;

  /** Libellé long du Type de généalogie d'entité administrative. */
  @Size(max = 100)
  @Column(name = "LIBELLE_LONG", length = 100, nullable = false)
  private String libelleLong;

  /** Statut par défaut du Type de généalogie d'entité administrative. */
  @Size(max = 1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "STATUT_DEFAUT", nullable = true)
  private StatutModification statutParDefaut;
}
