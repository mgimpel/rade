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
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.aesn.rade.batch.tasks.SpringBatchTestConfiguration;
import fr.aesn.rade.persist.dao.DelegationJpaDao;

/**
 * Test the Delegation Import Job.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDeleteAllDelegationTasklet {
  /** Static Spring Configuration. */
  @Configuration
  protected static class Config extends SpringBatchTestConfiguration {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;
    @Bean
    protected Job deleteAllDelegationJob(@Qualifier("deleteAllDelegationStep") Step step) {
      return jobs.get("deleteAllDelegationJob").start(step).build();
    }
    @Bean
    protected Step deleteAllDelegationStep(@Qualifier("deleteAllDelegationTasklet") Tasklet tasklet) {
      return steps.get("deleteAllDelegationStep").tasklet(tasklet).build();
    }
    @Bean
    protected Tasklet deleteAllDelegationTasklet() {
      return new DeleteAllDelegationTasklet();
    }
  }
  /** In Memory Derby Database Instance. */
  protected static EmbeddedDatabase db;
  /** DAO to be tested. */
  @Autowired
  private DelegationJpaDao jpaDao;
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
        .setScriptEncoding("UTF-8")
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
    jobLauncherTestUtils.setJob(context.getBean("deleteAllDelegationJob", Job.class));
  }

  /**
   * Close the Test Environment cleanly.
   */
  @After
  public void tearDown() {
  }

  /**
   * Test the Batch Job execution.
   * @throws Exception exception launching job
   */
  @Test
  public void testDeleteAllDelegationJob() throws Exception {
    assertEquals(7, jpaDao.count());
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    JobParameters jobParameters = jobBuilder.toJobParameters();
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
    assertEquals("Batch Failed to complete",
                 BatchStatus.COMPLETED, jobExecution.getStatus());
    assertEquals(0, jpaDao.count());
  }
}
