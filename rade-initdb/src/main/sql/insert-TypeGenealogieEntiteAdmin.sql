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
-- Code INSEE
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('10', 'Changement de nom', 'Changement de nom', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('11', 'Changement de nom', 'Changement de nom dû à une fusion (simple ou association)', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('12', 'Changement de nom', 'Changement de nom dû à un rétablissement', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('13', 'Changement de nom', 'Changement de nom dû au changement de nom du chef-lieu', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('14', 'Changement de nom', 'Changement de nom dû au transfert du chef-lieu', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('20', 'Création', 'Création', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('21', 'Rétablissement', 'Rétablissement', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('22', 'Commune parcellé', 'Commune ayant donné des parcelles pour la création', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('23', 'Commune se séparant', 'Commune se séparant', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('24', 'Création', 'Création d''une fraction cantonale', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('30', 'Suppression', 'Suppression', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('31', 'Fusion', 'Fusion', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('32', 'Fusion', 'Commune absorbante de la fusion', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('33', 'Fusion', 'Fusion - association', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('34', 'Fusion', 'Commune absorbante de la fusion-association', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('35', 'Fusion', 'Fusion-association se transformant en fusion simple', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('36', 'Fusion', 'Commune-pôle de la fusion-association qui s''est transformée en fusion simple', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('37', 'Suppression', 'Suppression de la fraction cantonale', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('40', 'Changement de région', 'Changement de région', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('41', 'Changement de dépt.', 'Changement de département', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('42', 'Changement d''arr', 'Changement d''arrondissement', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('43', 'Changement de canton', 'Changement de canton', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('50', 'Transfert chef-lieu', 'Transfert de chef-lieu de commune', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('51', 'Transfert chef-lieu', 'Transfert de chef-lieu de canton', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('52', 'Transfert chef-lieu', 'Transfert de chef-lieu d''arrondissement', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('53', 'Transfert chef-lieu', 'Transfert de chef-lieu de département', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('54', 'Transfert chef-lieu', 'Transfert de chef-lieu de région', 'N');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('60', 'Cession de parcelles', 'Cession de parcelles avec incidence démographique', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('61', 'Cession de parcelles', 'Cession de parcelles sans incidence démographique', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('62', 'Récept. de parcelles', 'Réception de parcelles avec incidence démographique', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('63', 'Récept. de parcelles', 'Réception de parcelles sans incidence démographique', 'I');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('70', 'Changement de code', 'Changement de code', 'I');
-- Code AESN
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('X0', 'Correction manuelle', 'Correction manuelle', 'P');
INSERT INTO ZR_TYPEGENEALOGIE("CODE", "LIBELLE_COURT", "LIBELLE_LONG", "STATUT_DEFAUT") VALUES ('X1', 'Changement anticipé', 'Changement anticipé', 'P');
