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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

/**
 * JUnit Test for ContextListItemReader.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestContextListItemReader {
  /**
   * Test the ContextListItemReader.
   */
  @Test
  public void testContextListItemReader() {
    String[] arr = {"a", "b", "c"};
    ContextListItemReader<String> reader = new ContextListItemReader<>();
    reader.beforeStep(buildMockStepExecution(arr));
    assertEquals(reader.read(), arr[0]);
    assertEquals(reader.read(), arr[1]);
    assertEquals(reader.read(), arr[2]);
    assertNull(reader.read());
  }

  /**
   * Test what happens when a List of the wrong type is given.
   */
  @Test(expected = ClassCastException.class)
  public void testClassCastException() {
    String[] arr = {"a", "b", "c"};
    ContextListItemReader<Integer> reader = new ContextListItemReader<>();
    reader.beforeStep(buildMockStepExecution(arr));
    Integer i = reader.read(); // throws ClassCastException here
    assertNotNull(i);
  }

  /**
   * Test ContextListItemReader.afterStep returns null to leave the old value
   * unchanged.
   */
  @Test
  public void testAfterStep() {
    ContextListItemReader<Integer> reader = new ContextListItemReader<>();
    assertNull(reader.afterStep(mock(StepExecution.class)));
  }

  /**
   * Build a Mock Batch StepExecution with given List as Context parameter.
   * @param list the list to put into the Batch Context.
   * @return Mock Batch StepExecution with given List as Context parameter.
   */
  private StepExecution buildMockStepExecution(List<?> list) {
    ExecutionContext ec = mock(ExecutionContext.class);
    when(ec.get(ContextListItemReader.DEFAULT_CONTEXT_PARAM_NAME)).thenReturn(list);
    JobExecution je = mock(JobExecution.class);
    when(je.getExecutionContext()).thenReturn(ec);
    StepExecution se = mock(StepExecution.class);
    when(se.getJobExecution()).thenReturn(je);
    return se;
  }

  /**
   * Build a Mock Batch StepExecution with given List as Context parameter.
   * @param list the list to put into the Batch Context.
   * @return Mock Batch StepExecution with given List as Context parameter.
   */
  private StepExecution buildMockStepExecution(Object[] list) {
    return buildMockStepExecution(Arrays.asList(list));
  }
}
