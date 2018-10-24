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
import org.mockito.ArgumentCaptor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

/**
 * JUnit Test for ContextListItemWriter.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestContextListItemWriter {
  /**
   * Test the ContextListItemWriter.
   */
  @Test
  public void testContextListItemWriter() {
    String[] arr1 = {"a", "b", "c"};
    String[] arr2 = {"d", "e"};
    ContextListItemWriter<String> writer = new ContextListItemWriter<>();
    writer.write(Arrays.asList(arr1));
    writer.write(Arrays.asList(arr2));
    ExecutionContext ec = mock(ExecutionContext.class);
    JobExecution je = mock(JobExecution.class);
    when(je.getExecutionContext()).thenReturn(ec);
    StepExecution se = mock(StepExecution.class);
    when(se.getJobExecution()).thenReturn(je);
    @SuppressWarnings("unchecked")
    ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
    assertNull(writer.afterStep(se));
    verify(ec).put(eq(ContextListItemWriter.DEFAULT_CONTEXT_PARAM_NAME),
                   captor.capture());
    List<String> capturedArgument = captor.getValue();
    assertEquals(5, capturedArgument.size());
    assertEquals(arr1[0], capturedArgument.get(0));
    assertEquals(arr1[1], capturedArgument.get(1));
    assertEquals(arr1[2], capturedArgument.get(2));
    assertEquals(arr2[0], capturedArgument.get(3));
    assertEquals(arr2[1], capturedArgument.get(4));
  }
}
