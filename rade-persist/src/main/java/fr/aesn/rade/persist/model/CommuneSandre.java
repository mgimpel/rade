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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * CommuneSandre Entity.
 *
 * Référentiel des Communes associées à leur circonscription bassin.
 *
 * <pre>
 * CREATE TABLE ZR_COMMUNESANDRE (
 *   CODE_COMMUNE           varchar(5)  NOT NULL PRIMARY KEY,
 *   LIBELLE_COMMUNE        varchar(45) NOT NULL,
 *   STATUT_COMMUNE         varchar(20) NOT NULL,
 *   DATE_CREATION_COMMUNE  date,
 *   DATE_MAJ_COMMUNE       date        NOT NULL,
 *   CODE_BASSIN_DCE        varchar(2)  NOT NULL,
 *   CODE_EU_DISTRICT       varchar(24) NOT NULL,
 *   CIRCONSCRIPTION_BASSIN varchar(2)  NOT NULL,
 *   CODE_COMITE_BASSIN     varchar(8)  NOT NULL,
 *   AUDIT_ID               integer     NOT NULL,
 *   FOREIGN KEY(CIRCONSCRIPTION_BASSIN) REFERENCES ZR_BASSIN,
 *   FOREIGN KEY(AUDIT_ID)               REFERENCES ZR_AUDIT
 * );
 * </pre>
 *
 * Pour plus de détails, voir:
 * <ul>
 * <li>Page Sandre: http://www.sandre.eaufrance.fr/atlas/srv/fre/catalog.search#/metadata/689a5b99-8d4e-488d-9305-c970b18ad64c</li>
 * <li>Description du Modèle de données: http://services.sandre.eaufrance.fr/telechargement/geo/COM/sandre_sc_referentiels_com_1.pdf</li>
 * <li>Web Service fournissant le CSV: https://api.sandre.eaufrance.fr/referentiels/v1/commune.csv?compress=true</li>
 * </ul>
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_COMMUNESANDRE")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class CommuneSandre implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 4704884751127866416L;

  /** Code INSEE de la commune. */
  @Size(max = 5)
  @Id
  @Column(name = "CODE_COMMUNE", length = 5, nullable = false)
  private String codeCommune;

  /** Libellé de la commune. */
  @Size(max = 45) // D'après la spec max=35, mais 51513 s'appelle SAINT-REMY-EN-BOUZEMONT-SAINT-GENEST-ET-ISSON
  @Column(name = "LIBELLE_COMMUNE", length = 45, nullable = true)
  private String libelleCommune;

  /** Statut de la commune. */
  @Size(max = 20)
  @Column(name = "STATUT_COMMUNE", length = 20, nullable = true)
  private String statutCommune;

  /** Date de création de la commune. */
  @Temporal(TemporalType.DATE)
  @Column(name = "DATE_CREATION_COMMUNE", nullable = true) // Obligatoire d'après la spec, mais vide parfois dans l'extrait Sandre
  private Date dateCreationCommune;

  /** Date de dernière mise à jour de la commune. */
  @Temporal(TemporalType.DATE)
  @Column(name = "DATE_MAJ_COMMUNE", nullable = false)
  private Date dateMajCommune;

  /** Code du bassin DCE. */
  @Size(max = 2)
  @Column(name = "CODE_BASSIN_DCE", length = 2, nullable = false)
  private String codeBassinDce;

  /** Code du district. */
  @Size(max = 24)
  @Column(name = "CODE_EU_DISTRICT", length = 24, nullable = true)
  private String codeEuDistrict;

  /** Numéro de la circonscription administrative de bassin. */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "CIRCONSCRIPTION_BASSIN", nullable = true)
  private CirconscriptionBassin circonscriptionBassin;

  /** Code du comité de bassin. */
  @Size(max = 8)
  @Column(name = "CODE_COMITE_BASSIN", length = 8, nullable = false)
  private String codeComiteBassin;

  /** Détails de Modification de l'entité. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUDIT_ID", nullable = false)
  private Audit audit;
}
