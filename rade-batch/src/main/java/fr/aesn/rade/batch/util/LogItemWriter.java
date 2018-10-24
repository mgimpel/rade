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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

/**
 * ItemWriter that writes items to the logger.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class LogItemWriter<T>
  implements ItemWriter<T> {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(LogItemWriter.class);

  /**
   * Process the supplied data element.
   * Will not be called with any null items in normal operation.
   * @param items the items to be written.
   */
  @Override
  public void write(final List<? extends T> items) {
    for (T item : items) {
      log.info("Written: {}", item);
    }
  }
}
