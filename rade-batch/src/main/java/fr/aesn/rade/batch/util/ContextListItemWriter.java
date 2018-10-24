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

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import lombok.Setter;

/**
 * ItemWriter that writes items to a list that is then provided to the Batch
 * Context.
 * The list can be recovered from the Batch Context by a ContextListItemReader
 * during a following step of the current Batch Job.
 *
 * NB: As the items are kept in Memory this ItemWriter is not appropriate
 * for large volumes of data.
 *
 * Originally inspired from this article:
 * https://stackoverflow.com/questions/33427434/pass-current-step-output-to-next-step-and-write-to-flatfile
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class ContextListItemWriter<T>
  implements ItemWriter<T>, StepExecutionListener {
  /** The default name of the Batch Context parameter with the list. */
  public static final String DEFAULT_CONTEXT_PARAM_NAME = "itemList";
  /** The name of the Batch Context parameter with the list. */
  @Setter
  private String name = DEFAULT_CONTEXT_PARAM_NAME;
  /** The list to write to, that will be placed in the Batch Context. */
  private List<T> list = new ArrayList<T>();

  /**
   * Process the supplied data element.
   * Will not be called with any null items in normal operation.
   * @param items the items to be written.
   */
  @Override
  public void write(final List<? extends T> items) {
    for (T item : items) {
      list.add(item);
    }
  }

  /**
   * Initialize the state of the listener with the StepExecution from the
   * current scope.
   * @param stepExecution the StepExecution from the current scope.
   */
  @Override
  public void beforeStep(final StepExecution stepExecution) {
    // do nothing
  }

  /**
   * Give a listener a chance to modify the exit status from a step.
   * The value returned will be combined with the normal exit status.
   * @param stepExecution the StepExecution from the current scope.
   * @return an ExitStatus to combine with the normal value.
   * Return null to leave the old value unchanged.
   */
  @Override
  public ExitStatus afterStep(final StepExecution stepExecution) {
    stepExecution.getJobExecution().getExecutionContext().put(name, list);
    return null;
  }
}
