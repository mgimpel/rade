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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity: Evenement Entité Administrative à controler.
 * <pre>
 * CREATE TABLE ZR_EVENEMENT (
 *   ID            integer      NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
 *   DESCRIPTION   varchar(350),
 *   ENTITE_ADMIN  integer,
 *   STATUT_MODIF  varchar(1)   NOT NULL,
 *   TYPE_MODIF    varchar(2)   NOT NULL,
 *   AUDIT_ID      integer      NOT NULL,
 *   ZR_DSTATUT    date         NOT NULL,
 *   ZR_BORIGINE   varchar(1)   NOT NULL,
 *   ZR_DCREAEVT   date         NOT NULL,
 *   ZR_LEVTCOMN   varchar(70),
 *   FOREIGN KEY(ENTITE_ADMIN) REFERENCES ZR_ENTITEADMIN,
 *   FOREIGN KEY(TYPE_MODIF)   REFERENCES ZR_TYPEGENEALOGIE,
 *   FOREIGN KEY(STATUT_MODIF) REFERENCES ZR_STATUTMODIF,
 *   FOREIGN KEY(AUDIT_ID)     REFERENCES ZR_AUDIT
 * );
 * </pre>
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Entity
@Table(name = "ZR_EVENEMENT")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Evenement implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = -7180527530682012730L;

  /** Identifiant de l'évenement. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence_Evenement")
  @SequenceGenerator(name = "id_Sequence_Evenement", sequenceName = "evenement_seq")
  @Column(name = "ID", nullable = false)
  private Integer id;

  /** Description de l'évenement. */
  @Size(max = 350)
  @Column(name = "DESCRIPTION", length = 350, nullable = true)
  private String description;

  /** Entité Administrative à laquelle l'évenement est rattaché. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ENTITE_ADMIN", nullable = true)
  private EntiteAdministrative entiteAdmin;

  /** Statut de Modification de l'évenement. */
  @Size(max = 1)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "STATUT_MODIF", nullable = true)
  private StatutModification statutModification;

  /** Type de Modification de l'évenement. */
  @Size(max = 2)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "TYPE_MODIF", nullable = true)
  private TypeGenealogieEntiteAdmin typeModification;

  /** Détails de Modification de l'évenement. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUDIT_ID", nullable = false)
  private Audit audit;
}
