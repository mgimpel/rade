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

-- TODO: Automatically generate ddl from jpa annotations

-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_DELEGATION : Délégation
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_DELEGATION (
  CODE         varchar(5)   NOT NULL PRIMARY KEY,
  LIBELLE      varchar(60)  NOT NULL,
  ACHEMINEMENT varchar(255) NOT NULL,
  ADRESSE1     varchar(255),
  ADRESSE2     varchar(255),
  ADRESSE3     varchar(255),
  ADRESSE4     varchar(255),
  ADRESSE5     varchar(255),
  CODE_POSTAL  varchar(5)   NOT NULL,
  EMAIL        varchar(255),
  FAX          varchar(255),
  SITEWEB      varchar(255),
  TELEPHONE    varchar(255) NOT NULL,
  TELEPHONE2   varchar(255),
  TELEPHONE3   varchar(255)
);
/*
COMMENT ON TABLE ZR_DELEGATION
  IS 'Table for Delegation';
*/

-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_AUDIT : Trace des modifications
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_AUDIT (
  ZR_AUDIT_ID       integer       NOT NULL PRIMARY KEY,
  ZR_AUDIT_AUTEUR   varchar(8)    NOT NULL,
  ZR_AUDIT_DATE     date          NOT NULL,
  ZR_AUDIT_NOTE     varchar(4000)
);
/*
COMMENT ON TABLE ZR_AUDIT
  IS 'Table de suivie des modifications';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_STMCOMUN : Statut de modification de commune
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_STMCOMUN (
  ZR_CSTMCOMUN    varchar(1)   NOT NULL PRIMARY KEY,
  ZR_LCSTATMODCOM varchar(25)  NOT NULL,
  ZR_LLSTATMODCOM varchar(250) NOT NULL
);
/*
COMMENT ON TABLE ZR_STMCOMUN IS
  'Entité [STATUT MODIFICATION DE COMMUNE]';
COMMENT ON COLUMN ZR_STMCOMUN.ZR_CSTMCOMUN IS
  '[Code statut de modification commune] de l''entité [STATUT MODIFICATION DE COMMUNE]';
COMMENT ON COLUMN ZR_STMCOMUN.ZR_LCSTATMODCOM IS
  '[Libellé court statut de modification commune] de l''entité [STATUT MODIFICATION DE COMMUNE]';
COMMENT ON COLUMN ZR_STMCOMUN.ZR_LLSTATMODCOM IS
  '[Libellé long statut de modification commune] de l''entité [STATUT MODIFICATION DE COMMUNE]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_NOMCLAIR : Type nom en clair entité administrative
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_NOMCLAIR (
  ZR_CNOMCLAIR   varchar(1) NOT NULL PRIMARY KEY,
  ZR_ARTICLE     varchar(5),
  ZR_CHARNIERE   varchar(6),
  ZR_ARTICLEEADM varchar(5)
);
/*
COMMENT ON TABLE ZR_NOMCLAIR IS
  'Entité [TYPE NOM EN CLAIR ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_NOMCLAIR.ZR_CNOMCLAIR IS
  '[code utilisation nom en clair entité administrative] de l''entité [TYPE NOM EN CLAIR ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_NOMCLAIR.ZR_ARTICLE IS
  '[article nom en clair entité administrative] de l''entité [TYPE NOM EN CLAIR ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_NOMCLAIR.ZR_CHARNIERE IS
  '[charnière nom en clair entité administrative] de l''entité [TYPE NOM EN CLAIR ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_NOMCLAIR.ZR_ARTICLEEADM IS
  '[article entité administrative majuscules] de l''entité [TYPE NOM EN CLAIR ENTITE ADMINISTRATIVE]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_TYPEEADM : Type d'entité administrative
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_TYPEEADM (
  ZR_CTYPEEADM varchar(3)  NOT NULL PRIMARY KEY,
  ZR_LCTENTADM varchar(20) NOT NULL
);
/*
COMMENT ON TABLE ZR_TYPEEADM IS
  'Entité [TYPE D''ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_TYPEEADM.ZR_CTYPEEADM IS
  '[Code type d''entité administrative] de l''entité [TYPE D''ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_TYPEEADM.ZR_LCTENTADM IS
  '[Libellé court type d''entité administrative] de l''entité [TYPE D''ENTITE ADMINISTRATIVE]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_GENEADM : Type de Généalogie d'entité administrative
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_GENEADM (
  ZR_CGENEADM_NID          varchar(2)   NOT NULL PRIMARY KEY,
  ZR_LCTGENEADM            varchar(20)  NOT NULL,
  ZR_LLTGENEADM            varchar(100) NOT NULL,
  ZR_GENEADM_R_ZR_STMCOMUN varchar(1),
  FOREIGN KEY(ZR_GENEADM_R_ZR_STMCOMUN) REFERENCES ZR_STMCOMUN
);
/*
COMMENT ON TABLE ZR_GENEADM IS
  'Entité [TYPE DE GENEALOGIE D''ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_GENEADM.ZR_CGENEADM_NID IS
  '[Code type de genealogie entité administrative] de l''entité [TYPE DE GENEALOGIE D''ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_GENEADM.ZR_LCTGENEADM IS
  '[Libellé court type de genealogie entité administrative] de l''entité [TYPE DE GENEALOGIE D''ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_GENEADM.ZR_LLTGENEADM IS
  '[Libellé long type de genealogie entité administrative] de l''entité [TYPE DE GENEALOGIE D''ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_GENEADM.ZR_GENEADM_R_ZR_STMCOMUN IS
  '[Code statut de modification commune] vers l''entité [STATUT MODIFICATION DE COMMUNE] par l''association [STATUT PAR DEFAUT DU TYPE DE GENEALOGIE]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_AEAU : Circonscription de bassin
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_AEAU (
  ZR_CAEAU    varchar(2)  NOT NULL PRIMARY KEY,
  ZR_LCBASSIN varchar(5)  NOT NULL,
  ZR_LLBASSIN varchar(70) NOT NULL,
  ZR_AUDIT_ID integer     NOT NULL,
  FOREIGN KEY(ZR_AUDIT_ID) REFERENCES ZR_AUDIT
);
/*
COMMENT ON TABLE ZR_AEAU IS
  'Entité [CIRCONSCRIPTION  DE BASSIN]';
COMMENT ON COLUMN ZR_AEAU.ZR_CAEAU IS
  '[code INSEE bassin] de l''entité [CIRCONSCRIPTION  DE BASSIN]';
COMMENT ON COLUMN ZR_AEAU.ZR_LCBASSIN IS
  '[Libellé court bassin] de l''entité [CIRCONSCRIPTION  DE BASSIN]';
COMMENT ON COLUMN ZR_AEAU.ZR_LLBASSIN IS
  '[Libellé  long bassin] de l''entité [CIRCONSCRIPTION  DE BASSIN]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_EADM : Entité administrative (Commune, Département ou Région)
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_EADM (
  ZR_EADM_NID           integer       NOT NULL PRIMARY KEY,
  ZR_DDEBUVALEADM       date          NOT NULL,
  ZR_DFINVALEADM        date,
  ZR_ARTIENREADM        varchar(5),
  ZR_LNOMEADM           varchar(70)   NOT NULL,
  ZR_LENIINSEEADM       varchar(70)   NOT NULL,
  ZR_COMMENTEADM        varchar(4000) NOT NULL,
  ZR_EADM_R_ZR_TYPEEADM varchar(3)    NOT NULL,
  ZR_EADM_R_ZR_NOMCLAIR varchar(1),
  ZR_AUDIT_ID           integer       NOT NULL,
  FOREIGN KEY(ZR_EADM_R_ZR_NOMCLAIR) REFERENCES ZR_NOMCLAIR,
  FOREIGN KEY(ZR_EADM_R_ZR_TYPEEADM) REFERENCES ZR_TYPEEADM,
  FOREIGN KEY(ZR_AUDIT_ID) REFERENCES ZR_AUDIT
);
/*
COMMENT ON TABLE ZR_EADM IS
  'Entité [ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_EADM.ZR_NVERSEADM IS
  '';
COMMENT ON COLUMN ZR_EADM.ZR_DFINVALEADM IS
  'Renseigné manuellement lors d''une mise à jour sinon mis à jour automatiquement';
COMMENT ON COLUMN ZR_EADM.ZR_DATECREA IS
  'Date à laquelle l''entité a été créée (différent de la date de création de la version). Cette date est la date de début de validité de la première version de l''entité.';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_REGION : Region
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_REGION (
  ZR_CINSEREGI            varchar(2)  NOT NULL,
  ZR_EADM_NID             integer     NOT NULL PRIMARY KEY,
  ZR_REGION_R_ZR_CNCDINSE varchar(10) NOT NULL,
--  FOREIGN KEY(ZR_REGION_R_ZR_CNCDINSE) REFERENCES ZR_COMMUNE,
  FOREIGN KEY(ZR_EADM_NID)             REFERENCES ZR_EADM
);
/*
COMMENT ON TABLE ZR_REGION IS
  'Entité [REGION]';
COMMENT ON COLUMN ZR_REGION.ZR_CINSEREGI IS
  '[code INSEE région] de l''entité [REGION]';
COMMENT ON COLUMN ZR_REGION.ZR_EADM_NID IS
  '[Identifiant entité administrative] vers l''entité [ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_REGION.ZR_REGION_R_ZR_CNCDINSE IS
  '[Identifiant INSEE Commune] vers l''entité [CODE INSEE COMMUNE] par l''association [COMMUNE CHEF LIEU DE REGION]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_DEPT : Département
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_DEPT (
  ZR_CINSEDEPT          varchar(3)  NOT NULL,
  ZR_EADM_NID           integer     NOT NULL PRIMARY KEY,
  ZR_DEPT_R_ZR_REGION   integer     NOT NULL,
  ZR_DEPT_R_ZR_CNCDINSE varchar(10) NOT NULL,
--  FOREIGN KEY(ZR_DEPT_R_ZR_CNCDINSE) REFERENCES ZR_COMMUNE,
  FOREIGN KEY(ZR_EADM_NID)           REFERENCES ZR_EADM,
  FOREIGN KEY(ZR_DEPT_R_ZR_REGION)   REFERENCES ZR_REGION
);
/*
COMMENT ON TABLE ZR_DEPT IS
  'Entité [DEPARTEMENT]';
COMMENT ON COLUMN ZR_DEPT.ZR_CINSEDEPT IS
  '[code INSEE département] de l''entité [DEPARTEMENT]';
COMMENT ON COLUMN ZR_DEPT.ZR_EADM_NID IS
  '[Identifiant entité administrative] vers l''entité [ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_DEPT.ZR_DEPT_R_ZR_CNCDINSE IS
  '[Identifiant INSEE Commune] vers l''entité [CODE INSEE COMMUNE] par l''association [COMMUNE CHEF LIEU DE DEPARTEMENT]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_COMMUNE : Commune
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_COMMUNE (
  ZR_BURBAIN_RU            varchar(1)  NOT NULL,
  ZR_EADM_NID              integer     NOT NULL PRIMARY KEY,
  ZR_COMMUNE_R_ZR_CNCDINSE varchar(10) NOT NULL,
  ZR_COMMUNE_R_ZR_AEAU     varchar(2)  NOT NULL,
  ZR_COMMUNE_R_ZR_DPT      integer     NOT NULL,
  FOREIGN KEY(ZR_COMMUNE_R_ZR_AEAU)     REFERENCES ZR_AEAU,
  FOREIGN KEY(ZR_EADM_NID)              REFERENCES ZR_EADM,
  FOREIGN KEY(ZR_COMMUNE_R_ZR_DPT)      REFERENCES ZR_DEPT
);
/*
COMMENT ON TABLE ZR_COMMUNE IS
  'Entité [COMMUNE]';
COMMENT ON COLUMN ZR_COMMUNE.ZR_BURBAIN_RU IS
  '[Indicateur Urbaine/Rurale] de l''entité [COMMUNE]';
COMMENT ON COLUMN ZR_COMMUNE.ZR_EADM_NID IS
  '[Identifiant entité administrative] vers l''entité [ENTITE ADMINISTRATIVE]';
COMMENT ON COLUMN ZR_COMMUNE.ZR_COMMUNE_R_ZR_CNCDINSE IS
  '[Identifiant INSEE Commune] vers l''entité [CODE INSEE COMMUNE] par l''association [CODE INSEE DE LA COMMUNE]';
COMMENT ON COLUMN ZR_COMMUNE.ZR_COMMUNE_R_ZR_AEAU IS
  '[Code INSEE bassin] vers l''entité [CIRCONSCRIPTION DE BASSIN] par l''association [APPARTENANCE COMMUNE BASSIN]';
COMMENT ON COLUMN ZR_COMMUNE.ZR_DDEBVALI IS
  '[Date de début de validité] vers l''entité [DISTRICT DE BASSIN DCE] par l''association [COMMUNE APPARTIENT AU DISTRICT]';
COMMENT ON COLUMN ZR_COMMUNE_R_ZR_DPT IS
  '[Identifiant entité administrative] vers l''entité [DEPARTEMENT] par l''association [COMMUNE APPARTIENT A DEPARTEMENT]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_ENTGEN : Association sur la généalogie de l'entité administrative
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_ENTGEN (
  ZR_ENTGEN_R_ZR_EADM_PAR integer       NOT NULL,
  ZR_ENTGEN_R_ZR_EADM_ENF integer       NOT NULL,
  ZR_ENTGEN_R_ZR_GENEADM  varchar(2)    NOT NULL,
  ZR_LCOMMODEADM          varchar(2000),
  PRIMARY KEY (ZR_ENTGEN_R_ZR_EADM_PAR, ZR_ENTGEN_R_ZR_EADM_ENF),
  FOREIGN KEY (ZR_ENTGEN_R_ZR_EADM_PAR) REFERENCES ZR_EADM,
  FOREIGN KEY (ZR_ENTGEN_R_ZR_EADM_ENF) REFERENCES ZR_EADM,
  FOREIGN KEY (ZR_ENTGEN_R_ZR_GENEADM) REFERENCES ZR_GENEADM  
);
/*
COMMENT ON TABLE ZR_ENTGEN IS
  'Association [GENEALOGIE DE L''ENTITE]';
COMMENT ON COLUMN ZR_ENTGEN.ZR_ENTGEN_R_ZR_EADM_PAR IS
  '[Identifiant entité administrative] vers l''entité [ENTITE ADMINISTRATIVE] par le rôle [ENTITE PARENT] de l''association [GENEALOGIE DE L''ENTITE]';
COMMENT ON COLUMN ZR_ENTGEN.ZR_ENTGEN_R_ZR_EADM_ENF IS
  '[Identifiant entité administrative] vers l''entité [ENTITE ADMINISTRATIVE] par le rôle [ENTITE ENFANT] de l''association [GENEALOGIE DE L''ENTITE]';
COMMENT ON COLUMN ZR_ENTGEN.ZR_ENTGEN_R_ZR_GENEADM IS
  '[Code type de genealogie entité administrative] vers l''entité [TYPE DE GENEALOGIE D''ENTITE ADMINISTRATIVE] par le rôle [TYPE DE GENEALOGIE] de l''association [GENEALOGIE DE L''ENTITE]';
COMMENT ON COLUMN ZR_ENTGEN.ZR_LCOMMODEADM IS
  '[Commentaire sur la modification de l''entité] de l''association [GENEALOGIE DE L''ENTITE]';
*/
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
-- ZR_EVTCTROL : Evenement commune a controler
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
CREATE TABLE ZR_EVTCTROL (
  ZR_EVTCTROL_NID            integer      NOT NULL PRIMARY KEY,
  ZR_BORIGINE                varchar(1)   NOT NULL,
  ZR_DCREAEVT                date         NOT NULL,
  ZR_LEVTCOMN                varchar(70),
  ZR_LEVTDESC                varchar(350),
  ZR_EVTCTROL_R_ZR_COMMUNE   integer,
  ZR_DSTATUT                 date         NOT NULL,
  ZR_EVTCTROL_R_ZR_STMCOMUN  varchar(1)   NOT NULL,
  ZR_EVTCTROL_R_ZR_GENEADM   varchar(2)   NOT NULL,
  ZR_AUDIT_ID                integer      NOT NULL,
  FOREIGN KEY(ZR_EVTCTROL_R_ZR_COMMUNE) REFERENCES ZR_COMMUNE,
  FOREIGN KEY(ZR_EVTCTROL_R_ZR_GENEADM) REFERENCES ZR_GENEADM,
  FOREIGN KEY(ZR_EVTCTROL_R_ZR_STMCOMUN) REFERENCES ZR_STMCOMUN
);
/*
COMMENT ON TABLE ZR_EVTCTROL IS
  'Entité [EVENEMENT COMMUNE A CONTROLER]';
COMMENT ON COLUMN ZR_EVTCTROL.ZR_DATECREA IS
  'Date à laquelle l''entité a été créée (différent de la date de création de la version). Cette date est la date de début de validité de la première version de l''entité.';
*/
