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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.service.AuditService;
import lombok.Setter;

public class EntiteAdminProcessor
  implements ItemProcessor<EntiteAdministrative, EntiteAdministrative> {

  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(EntiteAdminProcessor.class);
  /** Service for Metadata. */
  @Autowired @Setter
  private AuditService auditService;

  private Audit audit;
  private Date debutValidite;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    Audit audit = new Audit();
    audit.setAuteur("Batch");
    audit.setDate(new Date());
    audit.setNote("Import Batch");
    this.audit = auditService.createAudit(audit);
    this.debutValidite = new Date();
    log.info("Setting audit for step: {}", this.audit);
  }

  @Override
  public EntiteAdministrative process(EntiteAdministrative entiteAdmin) {
    entiteAdmin.setAudit(audit);
    entiteAdmin.setDebutValidite(debutValidite);
    entiteAdmin.setCommentaire("-");
    log.info("Processing Entity: {}", entiteAdmin);
    return entiteAdmin;
  }
}
