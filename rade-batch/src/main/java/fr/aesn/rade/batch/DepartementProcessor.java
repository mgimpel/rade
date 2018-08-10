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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.service.DepartementService;
import lombok.Setter;

/**
 * Process Region Read from INSEE file and add additional details to the
 * Entity (Audit details, Date of beginning of Validity, ...).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class DepartementProcessor
  extends EntiteAdminProcessor {

  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(DepartementProcessor.class);
  /** Service for Metadata. */
  @Autowired @Setter
  private DepartementService deptService;

  /** Map of valid regions currently in the Database. */
  private Map<String, Departement> deptMap;

  /**
   * Recover details from Database and Job Parameters Before Step Execution
   * starts. 
   * @param stepExecution Spring Batch stepExecution Object.
   */
  @BeforeStep
  @Override
  public void beforeStep(StepExecution stepExecution) {
    super.beforeStep(stepExecution);
    deptMap = deptService.getDepartementMap(debutValidite);
    log.info("BeforeStep initialisation completed ({} depts currently in DB)",
             deptMap.size());
  }

  @AfterStep
  public ExitStatus afterStep(StepExecution stepExecution) {
    for (Departement dept : deptMap.values()) {
      log.info("Removing departement {}: not present in new file",
               dept.getCodeInsee());
      deptService.invalidateDepartement(dept, debutValidite);
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
    if (!(entiteAdmin instanceof Departement)) {
      return null;
    }
    Departement dept = (Departement) entiteAdmin;
    Departement oldDept = deptMap.get(dept.getCodeInsee());
    if (oldDept != null) {
      deptMap.remove(dept.getCodeInsee());
      if ((dept.getRegion().equals(oldDept.getRegion()))
          && (dept.getChefLieu().equals(oldDept.getChefLieu()))
          && (dept.getTypeNomClair().equals(oldDept.getTypeNomClair()))
          && (dept.getNomMajuscule().equals(oldDept.getNomMajuscule()))
          && (dept.getNomEnrichi().equals(oldDept.getNomEnrichi()))) {
        log.info("Processing departement {}: Already exists - filtering out",
                 dept.getCodeInsee());
        return null;
      } else {
        log.info("Processing departement {}: Already exists but changed - updating",
                 dept.getCodeInsee());
        deptService.invalidateDepartement(oldDept, debutValidite);
        return super.process(dept);
      }
    } else {
      log.info("Processing departement {}: Adding new region",
               dept.getCodeInsee());
      return super.process(dept);
    }
  }
}
