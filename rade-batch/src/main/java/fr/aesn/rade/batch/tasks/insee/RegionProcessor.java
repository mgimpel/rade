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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;

import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.service.RegionService;
import lombok.Setter;

/**
 * Process Region Read from INSEE file and add additional details to the
 * Entity (Audit details, Date of beginning of Validity, ...).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class RegionProcessor
  extends EntiteAdminProcessor {

  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(RegionProcessor.class);
  /** Service for Region. */
  @Autowired @Setter
  private RegionService regionService;

  /** Map of valid regions currently in the Database. */
  private Map<String, Region> regionMap;

  /**
   * Recover details from Database and Job Parameters Before Step Execution
   * starts.
   * @param stepExecution Spring Batch stepExecution Object.
   */
  @BeforeStep
  @Override
  public void beforeStep(StepExecution stepExecution) {
    super.beforeStep(stepExecution);
    regionMap = regionService.getRegionMap(debutValidite);
    log.info("BeforeStep initialisation completed ({} regions currently in DB)",
             regionMap.size());
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
    for (Region region : regionMap.values()) {
      log.info("Removing region {}: not present in new file",
               region.getCodeInsee());
      regionService.invalidateRegion(region, debutValidite);
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
    if (!(entiteAdmin instanceof Region)) {
      return null;
    }
    Region region = (Region) entiteAdmin;
    Region oldRegion = regionMap.get(region.getCodeInsee());
    if (oldRegion != null) {
      regionMap.remove(region.getCodeInsee());
      if ((region.getChefLieu().equals(oldRegion.getChefLieu()))
          && (region.getTypeNomClair().equals(oldRegion.getTypeNomClair()))
          && (region.getNomMajuscule().equals(oldRegion.getNomMajuscule()))
          && (region.getNomEnrichi().equals(oldRegion.getNomEnrichi()))) {
        log.info("Processing region {}: Already exists - filtering out",
                 region.getCodeInsee());
        return null;
      } else {
        log.info("Processing region {}: Already exists but changed - updating",
                 region.getCodeInsee());
        regionService.invalidateRegion(oldRegion, debutValidite);
        return super.process(region);
      }
    } else {
      log.info("Processing region {}: Adding new region",
               region.getCodeInsee());
      return super.process(region);
    }
  }
}
