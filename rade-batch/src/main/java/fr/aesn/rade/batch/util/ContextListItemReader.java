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

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import lombok.Setter;

/**
 * ItemReader that reads items from a list that is provided by the Batch
 * Context.
 * The list can be placed in the Batch Context by a ContextListItemWriter
 * during a previous step of the current Batch Job.
 *
 * NB: As the items are kept in Memory this ItemReader is not appropriate
 * for large volumes of data.
 *
 * Originally inspired from this article:
 * https://stackoverflow.com/questions/33427434/pass-current-step-output-to-next-step-and-write-to-flatfile
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class ContextListItemReader<T>
  implements ItemReader<T>, StepExecutionListener {
  /** The default name of the Batch Context parameter with the list. */
  public static final String DEFAULT_CONTEXT_PARAM_NAME = "itemList";
  /** The name of the Batch Context parameter with the list. */
  @Setter
  private String name = DEFAULT_CONTEXT_PARAM_NAME;
  /** The list from the Context to read from. */
  private List<T> list;
  /** The pointer to the current element in the list. */
  private int pointer = 0;

  /**
   * Reads a piece of input data and advance to the next one.
   * Implementations must return null at the end of the input data set.
   * NB: if the list is not of type List<T>, the caller will get a
   * ClassCastException after calling read().
   * @return T the item to be processed
   */
  @Override
  public T read() {
    if (list != null && pointer < list.size()) {
      return list.get(pointer++);
    }
    return null;
  }

  /**
   * Initialize the state of the listener with the StepExecution from the
   * current scope.
   * @param stepExecution the StepExecution from the current scope.
   */
  @Override
  @SuppressWarnings("unchecked") // Casting to (List<T>) causes unchecked cast warning
  public void beforeStep(final StepExecution stepExecution) {
    Object param = stepExecution.getJobExecution()
                                .getExecutionContext()
                                .get(name);
    if (param instanceof List<?>) {
      list = (List<T>) param;
    }
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
    return null;
  }
}
