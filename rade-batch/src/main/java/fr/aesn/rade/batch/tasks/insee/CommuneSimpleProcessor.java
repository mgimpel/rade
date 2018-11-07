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

import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.service.CommuneService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Process Commune Read from INSEE file and add additional details to the
 * Entity (Audit details, Date of beginning of Validity, ...).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public class CommuneSimpleProcessor
  extends EntiteAdminProcessor {
  /** Service for Commune. */
  @Autowired @Setter
  private CommuneService communeService;

  /** Map of valid regions currently in the Database. */
  private Map<String, Commune> communeMap;

  /**
   * Recover details from Database and Job Parameters Before Step Execution
   * starts. 
   * @param stepExecution Spring Batch stepExecution Object.
   */
  @BeforeStep
  @Override
  public void beforeStep(StepExecution stepExecution) {
    super.beforeStep(stepExecution);
    communeMap = communeService.getCommuneMap(debutValidite);
    log.info("BeforeStep initialisation completed ({} commune currently in DB)",
             communeMap.size());
  }

  /**
   * Give a listener a chance to modify the exit status from a step.
   * The value returned will be combined with the normal exit status using
   * ExitStatus.and(ExitStatus).
   * @param stepExecution Spring Batch stepExecution Object.
   * @return an ExitStatus to combine with the normal value.
   * Return null to leave the old value unchanged.
   */
  @AfterStep
  public ExitStatus afterStep(StepExecution stepExecution) {
    for (Commune commune : communeMap.values()) {
      log.info("Removing communes {}: not present in new file",
               commune.getCodeInsee());
      communeService.invalidateCommune(commune, debutValidite);
    }
    log.info("AfterStep cleanup completed");
    return null;
  }

  /**
   * Process the given EntiteAdministrative (filter out if identical region
   * already exists, add additional details to new or changed Entity).
   * @param entiteAdmin the EntiteAdministrative to process.
   * @return the processed EntiteAdministrative.
   */
  @Override
  public EntiteAdministrative process(EntiteAdministrative entiteAdmin) {
    if (!(entiteAdmin instanceof Commune)) {
      return null;
    }
    Commune commune = (Commune) entiteAdmin;
    Commune oldCommune = communeMap.get(commune.getCodeInsee());
    if (oldCommune != null) {
      communeMap.remove(commune.getCodeInsee());
      if ((commune.getDepartement().equals(oldCommune.getDepartement()))
          && (commune.getTypeNomClair().equals(oldCommune.getTypeNomClair()))
          && (commune.getNomMajuscule().equals(oldCommune.getNomMajuscule()))
          && (commune.getArticleEnrichi().equals(oldCommune.getArticleEnrichi()))
          && (commune.getNomEnrichi().equals(oldCommune.getNomEnrichi()))) {
        log.debug("Processing commune {}: Already exists - filtering out",
                 commune.getCodeInsee());
        return null;
      } else {
        log.debug("Processing commune {}: Already exists but changed - updating",
                 commune.getCodeInsee());
        communeService.invalidateCommune(oldCommune, debutValidite);
        return super.process(commune);
      }
    } else {
      log.debug("Processing commune {}: Adding new commune",
               commune.getCodeInsee());
      return super.process(commune);
    }
  }
}
