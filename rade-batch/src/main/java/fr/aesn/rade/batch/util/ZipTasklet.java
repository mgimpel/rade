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
package fr.aesn.rade.batch.util;

import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * Zip Tasklet that takes a file (typically a CSV file just generated) and zips
 * it.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public class ZipTasklet
  implements Tasklet {
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
    throws Exception {
    log.info("Zipping Tasklet");
    String zipFilePath = "file.zip";
    try {
      ZipOutputStream zop = new ZipOutputStream(new FileOutputStream(zipFilePath));
      ZipEntry entry = new ZipEntry("file.csv"); //TODO
      zop.putNextEntry(entry);
      zop.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return RepeatStatus.FINISHED;
  }
}
