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
package fr.aesn.rade.batch;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.MetadataService;

/**
 * Main class to launch Batch Job.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class RegionMapper implements FieldSetMapper<Region> {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(RegionMapper.class);
  /** Service for Metadata. */
  @Autowired
  private MetadataService metadataService;

  public void setMetadataService(MetadataService metadataService) {
    this.metadataService = metadataService;
  }

  @Override
  public Region mapFieldSet(FieldSet fieldSet) throws BindException {
    log.debug("Importing line: {}", fieldSet.toString());
    Map<String, TypeNomClair> tncc = metadataService.getTypeNomClairMap();
    Map<String, TypeEntiteAdmin> tea = metadataService.getTypeEntiteAdminMap();
    Date debutValidite = new Date();
    Audit audit = new Audit();
    //audit.setId(id);
    audit.setAuteur("Batch");
    audit.setDate(new Date());
    audit.setNote("Import Batch");
    Region reg = new Region();
    reg.setTypeEntiteAdmin(tea.get("REG"));
    reg.setCodeInsee(fieldSet.readString(0));
    reg.setChefLieu(fieldSet.readString(1));
    reg.setTypeNomClair(tncc.get(fieldSet.readString(2)));
    reg.setNomMajuscule(fieldSet.readString(3));
    reg.setNomEnrichi(fieldSet.readString(4));
    reg.setDebutValidite(debutValidite);
    reg.setAudit(audit);
    return reg;
  }
}
