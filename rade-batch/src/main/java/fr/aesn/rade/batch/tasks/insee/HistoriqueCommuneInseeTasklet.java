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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.service.AuditService;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.MetadataService;
import lombok.Setter;

/**
 * Commune History Tasklet.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class HistoriqueCommuneInseeTasklet
  implements Tasklet, StepExecutionListener {

  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HistoriqueCommuneInseeTasklet.class);
  /** Service for Audit. */
  @Autowired @Setter
  private AuditService auditService;
  /** Service for Commune. */
  @Autowired @Setter
  private CommuneService communeService;
  /** Service for Metadata. */
  @Autowired @Setter
  private MetadataService metadataService;
  /** */
  private HistoriqueCommuneInseeImportRules rules;

  /** Audit details to add to Entity. */
  protected Audit batchAudit;
  /** */
  private Date startDate;
  /** */
  private Date endDate;
  /** */
  private List<HistoriqueCommuneInseeModel> historique;


  /**
   * Recover details from Database and Job Parameters Before Step Execution
   * starts.
   * @param stepExecution Spring Batch stepExecution Object.
   */
  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    // Set Audit
    String auditAuteur = stepExecution.getJobParameters().getString("auditAuteur", "Batch");
    Date auditDate = stepExecution.getJobParameters().getDate("auditDate", new Date());
    String auditNote = stepExecution.getJobParameters().getString("auditNote", "Import Batch");
    Audit audit = new Audit();
    audit.setAuteur(auditAuteur);
    audit.setDate(auditDate);
    audit.setNote(auditNote);
    batchAudit = auditService.createAudit(audit);
    log.debug("Setting audit for step: {}", batchAudit);
    // Set ImportRules
    rules = new HistoriqueCommuneInseeImportRules();
    rules.setCommuneService(communeService);
    rules.setMetadataService(metadataService);
    rules.setBatchAudit(batchAudit);
    // Get other Job Parameters
    JobParameters params = stepExecution.getJobParameters();
    startDate = params.getDate("debutValidite", new Date());
    endDate = params.getDate("endDate", new Date());
    // Get History List
    Object param = stepExecution.getJobExecution()
                                .getExecutionContext()
                                .get("historyList");
    if (param instanceof List<?>) {
      historique = (List<HistoriqueCommuneInseeModel>) param;
    }
    log.debug("History List initial size {}.", historique.size());
  }

  /**
   * Given the current context in the form of a step contribution, do whatever
   * is necessary to process this unit inside a transaction.
   *
   * Implementations return RepeatStatus.FINISHED if finished. If not they
   * return RepeatStatus.CONTINUABLE. On failure throws an exception.
   *
   * @param contribution mutable state to be passed back to update the current
   * step execution.
   * @param chunkContext attributes shared between invocations but not between
   * restarts.
   */
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
    throws InvalidArgumentException {
    rules.processAllMod(historique, startDate, endDate);
    return RepeatStatus.FINISHED;
  }

  /**
   * Give a listener a chance to modify the exit status from a step.
   * The value returned will be combined with the normal exit status using
   * ExitStatus.and(ExitStatus).
   *
   * Called after execution of step's processing logic (both successful or
   * failed).
   *
   * @param stepExecution Spring Batch stepExecution Object.
   * @return an ExitStatus to combine with the normal value.
   * Return null to leave the old value unchanged.
   */
  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }
}
