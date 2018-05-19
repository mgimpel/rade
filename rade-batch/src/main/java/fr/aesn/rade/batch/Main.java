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
package fr.aesn.rade.batch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main class to launch Batch Job.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class Main {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("batch-context.xml");

    String jobName;
    if (args.length > 0) {
      jobName = args[0];
    } else {
      jobName = "importRegionJob";
    }
    String inputFile;
    if (args.length > 1) {
        inputFile = args[1];
      } else {
        inputFile = "classpath:insee/reg2018.txt";
      }
    
    JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
    Job job = context.getBean(jobName, Job.class);

    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", inputFile);
    jobBuilder.addDate("debutValidite", new Date());
    jobBuilder.addString("auditAuteur", "Batch");
    jobBuilder.addDate("auditDate", new Date());
    jobBuilder.addString("auditNote", "Import " + inputFile);
    JobParameters jobParameters = jobBuilder.toJobParameters();

    try {
      JobExecution execution = jobLauncher.run(job, jobParameters);
      log.info("Job Exit Status : {}", execution.getStatus());
    } catch (JobExecutionException e) {
      log.error("Job failed", e);
    }
  }
}
