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
package fr.aesn.rade.batch.chunk;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Hexaposte;
import fr.aesn.rade.service.AuditService;
import lombok.Setter;

/**
 * Process Hexaposte Read from Hexaposte file and add additional details to the
 * Entity (Audit details, ...).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class HexaposteProcessor
  implements ItemProcessor<Hexaposte, Hexaposte> {

  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HexaposteProcessor.class);
  /** Service for Audit. */
  @Autowired @Setter
  private AuditService auditService;

  /** Audit details to add to Entity. */
  protected Audit batchAudit;

  /**
   * Recover details from Database and Job Parameters Before Step Execution
   * starts.
   * @param stepExecution Spring Batch stepExecution Object.
   */
  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
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
   * Process the given Hexaposte (add additional details to the
   * Entity).
   * @param hexaposte the Hexaposte to process.
   * @return the processed Hexaposte.
   */
  @Override
  public Hexaposte process(Hexaposte hexaposte) {
    hexaposte.setAudit(batchAudit);
    log.debug("Processing Entity: {}", hexaposte);
    return hexaposte;
  }
}
