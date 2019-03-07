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
package fr.aesn.rade.batch.tasks.misc;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.aesn.rade.batch.tasks.SpringBatchTestConfiguration;

/**
 * Test the Delegation Import Job.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDelegationExportBatch {
  /** Static Spring Configuration. */
  @Configuration
  @ImportResource(locations = "classpath*:batch-job-misc.xml")
  protected static class Config extends SpringBatchTestConfiguration {
  }
  /** In Memory Derby Database Instance. */
  protected static EmbeddedDatabase db;
  /** Spring Context. */
  @Autowired
  protected ApplicationContext context;
  /** JobLauncherTestUtils for JUnit to test Batch Jobs. */
  protected JobLauncherTestUtils jobLauncherTestUtils;

  /**
   * Set up the Test Environment.
   */
  @BeforeClass
  public static void setUpClass() {
    // create temporary database for Hibernate
    db = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.DERBY)
        .setScriptEncoding("windows-1252")
        .setName("testdb")
        .addScript("db/sql/create-tables.sql")
        .addScript("db/sql/insert-Delegation.sql")
        .build();
  }

  /**
   * Close the Test Environment cleanly.
   */
  @AfterClass
  public static void tearDownClass() {
    db.shutdown();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJobRepository(context.getBean("jobRepository", JobRepository.class));
    jobLauncherTestUtils.setJobLauncher(context.getBean("jobLauncher", JobLauncher.class));
    jobLauncherTestUtils.setJob(context.getBean("exportDelegationJob", Job.class));
  }

  /**
   * Close the Test Environment cleanly.
   */
  @After
  public void tearDown() {
  }

  /**
   * Test that the job exists.
   */
  @Test
  public void testJobExists() {
    assertNotNull("The job was not found",
                  context.getBean("exportDelegationJob", Job.class));
  }

  /**
   * Test the Batch Job execution.
   * @throws Exception exception launching job
   */
  @Test
  public void testExportDelegationJob() throws Exception {

    Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir")
                          + File.separator + "rade-test" + File.separator
                          + "batch" + File.separator)
                       .toAbsolutePath()
                       .normalize();
    if (!Files.exists(tmpDir)) {
      Files.createDirectories(tmpDir);
    }
    Path tmpFile = Files.createTempFile(tmpDir, "delegations.csv", null);
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("outputFile", tmpFile.toUri().toString());
    JobParameters jobParameters = jobBuilder.toJobParameters();
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
    assertEquals("Batch Failed to complete",
                 BatchStatus.COMPLETED, jobExecution.getStatus());
    assertTrue("Batch export file does not exist", Files.exists(tmpFile));
    assertEquals("Batch export file is wrong size", 1975, Files.size(tmpFile));
    try (Stream<String> lines = Files.lines(tmpFile)) {
      assertEquals("Batch export should have 8 lines (header + 7 delegations)",
                   8, lines.count());
    }
    assertTrue("Could not delete Batch file", Files.deleteIfExists(tmpFile));
  }
}
