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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
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

  public static final String DEFAULT_JOB_NAME = "importRegionJob";
  public static final String DEFAULT_INPUT_FILE = "classpath:insee/reg2018.txt";
  public static final String OPTION_HELP    = "help";
  public static final String OPTION_VERSION = "version";
  public static final String OPTION_JOB     = "job";
  public static final String OPTION_INPUT   = "input";
  public static final String OPTION_DATE    = "date";

  /**
   * Builds CLI Options.
   * @return CLI Options.
   */
  public static Options cliOptions() {
    Option help = new Option("h", OPTION_HELP, false,
                             "print this message");
    Option version = new Option("v", OPTION_VERSION, false,
                                "print the version information and exit");
    Option job = Option.builder("j").longOpt(OPTION_JOB)
                                    .hasArg().argName("name")
                                    .desc("execute the given job")
                                    .build();
    Option input = Option.builder("i").longOpt(OPTION_INPUT)
                                      .hasArg().argName("file")
                                      .desc("use the given file as input")
                                      .build();
    Option date = Option.builder("d").longOpt(OPTION_DATE)
                                     .hasArg().argName("date")
                                     .desc("the date of beginning of validity")
                                     .build();
    Options options = new Options();
    options.addOption(help);
    options.addOption(version);
    options.addOption(job);
    options.addOption(input);
    options.addOption(date);
    return options;
  }

  /**
   * Command line entrance.
   * @param args Command line arguments
   */
  public static void main(final String[] args) {
    // Load Spring Context
    ApplicationContext context = new ClassPathXmlApplicationContext("batch-context.xml");

    // Parse Command line
    CommandLineParser parser = new DefaultParser();
    CommandLine line = null;
    try {
      line = parser.parse(cliOptions(), args);
    } catch(org.apache.commons.cli.ParseException e) {
      log.error("Parsing failed. Reason: {}", e.getMessage());
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar rade-batch.jar", cliOptions());
      return;
    }
    if (line.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar rade-batch.jar", cliOptions());
      return;
    }
    if (line.hasOption("version")) {
      log.info("Version");
      return;
    }
    String jobName = line.hasOption("job") ? line.getOptionValue("job") : DEFAULT_JOB_NAME;
    String inputFile = line.hasOption("input") ? line.getOptionValue("input") : DEFAULT_INPUT_FILE;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date debutValidite;
    if (line.hasOption("date")) {
      try {
        debutValidite = sdf.parse(line.getOptionValue("date"));
      } catch (ParseException e) {
        log.warn("Exception parsing date {}", line.getOptionValue("date"));
        debutValidite = new Date();
      }
    } else {
      debutValidite = new Date();
    }

    // Launch Job
    JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
    Job job = context.getBean(jobName, Job.class);
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", inputFile);
    jobBuilder.addDate("debutValidite", debutValidite);
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
