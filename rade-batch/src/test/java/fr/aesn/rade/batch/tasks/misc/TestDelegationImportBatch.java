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

import java.util.List;
import java.util.Optional;

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
import fr.aesn.rade.persist.dao.DelegationJpaDao;
import fr.aesn.rade.persist.model.Delegation;

/**
 * Test the Delegation Import Job.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDelegationImportBatch {
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
  /** JPA DAO used to check data imported by Batch. */
  @Autowired
  protected DelegationJpaDao delegationJpaDao;
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
    jobLauncherTestUtils.setJob(context.getBean("importDelegationJob", Job.class));
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
                  context.getBean("importDelegationJob", Job.class));
  }

  /**
   * Test the Batch Job execution.
   * @throws Exception exception launching job
   */
  @Test
  public void testImportDelegationJob() throws Exception {
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", "classpath:batchfiles/misc/delegation.csv");
    JobParameters jobParameters = jobBuilder.toJobParameters();
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
    assertEquals("Batch Failed to complete",
                 BatchStatus.COMPLETED, jobExecution.getStatus());
    List<Delegation> list = delegationJpaDao.findAll();
    assertEquals("Batch imported the wrong number of lines",
                 7, list.size());
    Optional<Delegation> opt = delegationJpaDao.findById("SIEGE");
    assertTrue("Batch didn't import SIEGE", opt.isPresent());
    Delegation delegation = opt.get();
    assertNotNull("The batch imported SIEGE is null", delegation);
    assertEquals("Hibernate returned a Delegation, but the ID doesn't match",
                 "SIEGE", delegation.getCode());
    assertEquals("Hibernate returned a Delegation, but the Libelle doesn't match",
                 "SIEGE DFIR", delegation.getLibelle());
    assertEquals("Hibernate returned a Delegation, but the Acheminement doesn't match",
                 "Nanterre Cedex", delegation.getAcheminement());
    assertEquals("Hibernate returned a Delegation, but the Addresse1 doesn't match",
                 "Agence de l'eau Seine-Normandie", delegation.getAdresse1());
    assertEquals("Hibernate returned a Delegation, but the Addresse2 doesn't match",
                 "Siège DFIR", delegation.getAdresse2());
    assertEquals("Hibernate returned a Delegation, but the Addresse3 doesn't match",
                 "51, rue Salvador Allende", delegation.getAdresse3());
    assertEquals("Hibernate returned a Delegation, but the Addresse4 doesn't match",
                 "", delegation.getAdresse4());
    assertEquals("Hibernate returned a Delegation, but the Addresse5 doesn't match",
                 "Nanterre", delegation.getAdresse5());
    assertEquals("Hibernate returned a Delegation, but the Code Postal doesn't match",
                 "92027", delegation.getCodePostal());
    assertEquals("Hibernate returned a Delegation, but the E-mail doesn't match",
                 "xxx", delegation.getEmail());
    assertEquals("Hibernate returned a Delegation, but the Fax doesn't match",
                 "01 41 20 16 09", delegation.getFax());
    assertEquals("Hibernate returned a Delegation, but the Site Web doesn't match",
                 "http://www.eau-seine-normandie.fr/", delegation.getSiteWeb());
    assertEquals("Hibernate returned a Delegation, but the Telephone doesn't match",
                 "01 41 20 16 00", delegation.getTelephone());
    assertEquals("Hibernate returned a Delegation, but the Telephone2 doesn't match",
                 "", delegation.getTelephone2());
    assertEquals("Hibernate returned a Delegation, but the Telephone3 doesn't match",
                 "", delegation.getTelephone3());
  }
}
