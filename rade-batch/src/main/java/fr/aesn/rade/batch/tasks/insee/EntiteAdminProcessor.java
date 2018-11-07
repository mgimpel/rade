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
package fr.aesn.rade.batch.tasks.insee;

import java.util.Date;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.service.AuditService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Process Entite Admin Read from INSEE file and add additional details to the
 * Entity (Audit details, Date of beginning of Validity, ...).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public abstract class EntiteAdminProcessor
  implements ItemProcessor<EntiteAdministrative, EntiteAdministrative> {
  /** Service for Audit. */
  @Autowired @Setter
  private AuditService auditService;

  /** Audit details to add to Entity. */
  protected Audit batchAudit;
  /** Date of beginning of Validity of Entity. */
  protected Date debutValidite;

  /**
   * Recover details from Database and Job Parameters Before Step Execution
   * starts.
   * @param stepExecution Spring Batch stepExecution Object.
   */
  public void beforeStep(StepExecution stepExecution) {
    this.debutValidite = stepExecution.getJobParameters().getDate("debutValidite", new Date());
    String auditAuteur = stepExecution.getJobParameters().getString("auditAuteur", "Batch");
    Date auditDate = stepExecution.getJobParameters().getDate("auditDate", new Date());
    String auditNote = stepExecution.getJobParameters().getString("auditNote", "Import Batch");
    Audit audit = new Audit();
    audit.setAuteur(auditAuteur);
    audit.setDate(auditDate);
    audit.setNote(auditNote);
    batchAudit = auditService.createAudit(audit);
    log.debug("Setting audit for step: {}", batchAudit);
  }

  /**
   * Process the given EntiteAdministrative (add additional details to the
   * Entity).
   * @param entiteAdmin the EntiteAdministrative to process.
   * @return the processed EntiteAdministrative.
   */
  @Override
  public EntiteAdministrative process(EntiteAdministrative entiteAdmin) {
    entiteAdmin.setAudit(batchAudit);
    entiteAdmin.setDebutValidite(debutValidite);
    entiteAdmin.setCommentaire("-");
    log.trace("Processing Entity: {}", entiteAdmin);
    return entiteAdmin;
  }
}
