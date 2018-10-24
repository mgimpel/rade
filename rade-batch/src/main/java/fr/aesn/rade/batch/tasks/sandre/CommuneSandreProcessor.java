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
package fr.aesn.rade.batch.tasks.sandre;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.service.AuditService;
import lombok.Setter;

/**
 * Process CommuneSandre Read from CommuneSandre file and add additional
 * details to the Entity (Audit details, ...).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class CommuneSandreProcessor
  implements ItemProcessor<CommuneSandre, CommuneSandre> {

  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(CommuneSandreProcessor.class);
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
    JobParameters params = stepExecution.getJobParameters();
    Audit audit = new Audit();
    audit.setAuteur(params.getString("auditAuteur", "Batch"));
    audit.setDate(params.getDate("auditDate", new Date()));
    audit.setNote(params.getString("auditNote", "Import Batch"));
    batchAudit = auditService.createAudit(audit);
    log.debug("Setting audit for step: {}", batchAudit);
  }

  /**
   * Process the given CommuneSandre (add additional details to the
   * Entity).
   * @param commune the CommuneSandre to process.
   * @return the processed CommuneSandre.
   */
  @Override
  public CommuneSandre process(CommuneSandre commune) {
    commune.setAudit(batchAudit);
    log.debug("Processing Entity: {}", commune);
    return commune;
  }
}
