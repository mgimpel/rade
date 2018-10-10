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
 * Hexaposte Entity.
 *
 * Référentiel des codes postaux et des codes CEDEX de France extraits des
 * données de production de La Poste.
 *
 * <pre>
 * CREATE TABLE ZR_HEXAPOSTE (
 *   IDENTIFIANT                  varchar(6)  NOT NULL PRIMARY KEY,
 *   CODE_INSEE_COMMUNE           varchar(5),
 *   LIBELLE_COMMUNE              varchar(38),
 *   INDICATEUR_PLURIDISTRIBUTION integer     NOT NULL,
 *   TYPE_CODE_POSTALE            varchar(1)  NOT NULL,
 *   LIBELLE_LIGNE5               varchar(38),
 *   CODE_POSTALE                 varchar(5)  NOT NULL,
 *   LIBELLE_ACHEMINEMENT         varchar(32) NOT NULL,
 *   CODE_INSEE_ANCIENNE_COMMUNE  varchar(5),
 *   CODE_MAJ                     varchar(1),
 *   CODE_ETENDU_ADRESSE          varchar(10),
 *   AUDIT_ID                     integer     NOT NULL,
 *   FOREIGN KEY(AUDIT_ID) REFERENCES ZR_AUDIT
 * );
 * </pre>
 *
 * Pour plus de détails, voir
 * https://www.fichiers-postaux.com/referentiel/Hexaposte%20NV%202011%20version%20nov%202014%20Amabis.pdf
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_HEXAPOSTE")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Hexaposte implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 5575799430436135655L;

  /** Identifiant adresse ligne 6 ou ligne 5/ligne 6. */
  @Size(max = 6)
  @Id
  @Column(name = "IDENTIFIANT", length = 6, nullable = false)
  private String identifiant;

  /** Code INSEE de la commune (Facultatif pour le CEDEX). */
  @Size(max = 5)
  @Column(name = "CODE_INSEE_COMMUNE", length = 5, nullable = true)
  private String codeInseeCommune;

  /** Libellé de la commune. */
  @Size(max = 38)
  @Column(name = "LIBELLE_COMMUNE", length = 38, nullable = true)
  private String libelleCommune;

  /** Indicateur de pluridistribution (0 ou 1). */
  @Column(name = "INDICATEUR_PLURIDISTRIBUTION", nullable = false)
  private Integer indicateurPluridistribution;

  /** Type de code postal - M (Ménage) ou C (CEDEX). */
  @Size(max = 1)
  @Column(name = "TYPE_CODE_POSTALE", length = 1, nullable = false)
  private String typeCodePostal;

  /** Libellé de la ligne 5 (facultatif). */
  @Size(max = 38)
  @Column(name = "LIBELLE_LIGNE5", length = 38, nullable = true)
  private String libelleLigne5;

  /** Code postal. */
  @Size(max = 5)
  @Column(name = "CODE_POSTALE", length = 5, nullable = false)
  private String codePostale;

  /** Libellé acheminement. */
  @Size(max = 32)
  @Column(name = "LIBELLE_ACHEMINEMENT", length = 32, nullable = false)
  private String libelleAcheminement;

  /** Code INSEE ancienne commune (facultatif). */
  @Size(max = 5)
  @Column(name = "CODE_INSEE_ANCIENNE_COMMUNE", length = 5, nullable = true)
  private String codeInseeAncienneCommune;

  /** Code de mise à jour - C (Création), M (Modification), S (Suppression) ou un espace. */
  @Size(max = 1)
  @Column(name = "CODE_MAJ", length = 1, nullable = true)
  private String codeMaJ;

  /** Code Etendu de l’Adresse (Uniquement sur CP Ménage). */
  @Size(max = 10)
  @Column(name = "CODE_ETENDU_ADRESSE", length = 10, nullable = true)
  private String CodeEtenduAdresse;

  /** Détails de Modification de l'entité. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUDIT_ID", nullable = false)
  private Audit audit;
}
