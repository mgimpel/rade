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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.Resource;

/**
 * TODO
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TemporaryFileItemWriter<T>
  implements ItemWriter<T>, ItemStreamWriter<T>, StepExecutionListener {
  /** */
  private FlatFileItemWriter<T> delegate;
  /** */
  private Resource tmpResource;

  public void setDelegate(final FlatFileItemWriter<T> delegate) {
    this.delegate = delegate;
    delegate.setResource(tmpResource);
  }

  public void setTmpResource(final Resource tmpResource) {
    this.tmpResource = tmpResource;
    if (delegate != null) {
      delegate.setResource(tmpResource);
    }
  }

  /**
   * Process the supplied data element.
   * Will not be called with any null items in normal operation.
   * @param items the items to be written.
   * @throws Exception if the transformer or file output fail,
   * WriterNotOpenException if the writer has not been initialized.
   */
  @Override
  public void write(final List<? extends T> items) throws Exception {
    delegate.write(items);
  }

  /**
   * No-op.
   * @throws ItemStreamException if exception while closing the reader.
   */
  @Override
  public void close() throws ItemStreamException {
    delegate.close();
  }

  /**
   * Initialize the reader. This method may be called multiple times before
   * close is called.
   * @param context current step's ExecutionContext. Will be the
   * executionContext from the last run of the step on a restart.
   * @throws ItemStreamException if exception while initializing the reader.
   */
  @Override
  public void open(ExecutionContext context)
    throws ItemStreamException {
    delegate.open(context);
  }

  /**
   * Return empty ExecutionContext.
   * @param context to be updated
   * @throws ItemStreamException if exception while updating the reader.
   */
  @Override
  public void update(ExecutionContext context)
    throws ItemStreamException {
    delegate.update(context);
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
    // do nothing
    return null;
  }
}
