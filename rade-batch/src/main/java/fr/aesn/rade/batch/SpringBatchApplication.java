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

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

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
import org.springframework.context.support.FileSystemXmlApplicationContext;

import fr.aesn.rade.common.util.Version;

/**
 * Main class to launch Batch Job.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class SpringBatchApplication {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(SpringBatchApplication.class);

  public static final int DEFAULT_LINE_WIDTH = 120;
  public static final String DEFAULT_CONFIG_FILE = "classpath:batch-context.xml";
  public static final String DEFAULT_JOB_NAME    = "noJob";
  public static final String DEFAULT_INPUT_FILE  = "file:input.csv";
  public static final String OPTION_HELP    = "help";
  public static final String OPTION_VERSION = "version";
  public static final String OPTION_CONFIG  = "config";
  public static final String OPTION_LIST    = "list";
  public static final String OPTION_JOB     = "job";
  public static final String OPTION_INPUT   = "input";
  public static final String OPTION_DATE    = "date";

  /**
   * Builds CLI Options.
   * @return CLI Options.
   */
  public static Options buildCliOptions() {
    Options options = new Options();
    options.addOption(Option.builder("h").longOpt(OPTION_HELP)
                            .hasArg(false)
                            .desc("print this message")
                            .build());
    options.addOption(Option.builder("v").longOpt(OPTION_VERSION)
                            .hasArg(false)
                            .desc("print the version information")
                            .build());
    options.addOption(Option.builder("c").longOpt(OPTION_CONFIG)
                            .hasArg().argName("configfile")
                            .desc("use the give Spring configuration file")
                            .build());
    options.addOption(Option.builder("l").longOpt(OPTION_LIST)
                            .hasArg(false)
                            .desc("print a list of available jobs")
                            .build());
    options.addOption(Option.builder("j").longOpt(OPTION_JOB)
                            .hasArg().argName("jobname")
                            .desc("execute the given job")
                            .build());
    options.addOption(Option.builder("i").longOpt(OPTION_INPUT)
                            .hasArg().argName("filename")
                            .desc("use the given file as input")
                            .build());
    options.addOption(Option.builder("d").longOpt(OPTION_DATE)
                            .hasArg().argName("date")
                            .desc("the start date (yyyy-MM-dd)")
                            .build());
    options.addOption(Option.builder("P").argName( "property=value" )
                            .hasArgs().numberOfArgs(2)
                            .valueSeparator()
                            .desc("sets given job property with the given value")
                            .build());
    return options;
  }

  /**
   * Print Help message to Console.
   * @param cliOptions CLI Options.
   */
  public static void printHelp(Options cliOptions) {
    HelpFormatter formatter = new HelpFormatter();
    String syntax = "java -jar rade-batch.jar";
    String header = "Rade Batch Script Executor\n\n";
    String footer = "\nFor details, see https://github.com/mgimpel/rade";
    formatter.printHelp(DEFAULT_LINE_WIDTH, syntax, header, cliOptions, footer, true);
  }

  /**
   * Print message to Console.
   * @param msg the message.
   */
  public static void printMsg(String msg) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printWrapped(new PrintWriter(System.out, true), DEFAULT_LINE_WIDTH, msg);
  }

  /**
   * Command line entrance.
   * @param args Command line arguments
   */
  public static void main(final String[] args) {
    // Parse Command line
    CommandLineParser parser = new DefaultParser();
    CommandLine line = null;
    Options cliOptions = buildCliOptions();
    try {
      line = parser.parse(cliOptions, args);
    } catch(org.apache.commons.cli.ParseException e) {
      log.error("Parsing failed. Reason: {}", e.getMessage());
      printHelp(cliOptions);
      return;
    }
    if (line.hasOption(OPTION_HELP)) {
      printHelp(cliOptions);
      return;
    }
    if (line.hasOption(OPTION_VERSION)) {
      String version = "Rade Batch Scripts Version: " + Version.PROJECT_VERSION
                     + " (Build date: " + Version.BUILD_TIMESTAMP + ")";
      printMsg(version);
      return;
    }
    String configFile = line.hasOption(OPTION_CONFIG) ? line.getOptionValue(OPTION_CONFIG) : DEFAULT_CONFIG_FILE;
    ApplicationContext context = new FileSystemXmlApplicationContext(configFile);
    log.debug("Loaded Spring Application Context: {}", context);
    Properties properties = line.getOptionProperties("P");
    log.debug("Application Properties: {}", properties);
    if (line.hasOption(OPTION_LIST)) {
      String[] jobs = context.getBeanNamesForType(Job.class);
      String msg = "Rade Batch Script Executor has found the following jobs:\n\n "
                 + String.join("\n ", jobs);
      printMsg(msg);
      return;
    }
    String jobName = line.hasOption(OPTION_JOB) ? line.getOptionValue(OPTION_JOB) : DEFAULT_JOB_NAME;
    if (DEFAULT_JOB_NAME.equals(jobName)) {
      printHelp(cliOptions);
      return;
    }
    String inputFile = line.hasOption(OPTION_INPUT) ? line.getOptionValue(OPTION_INPUT) : DEFAULT_INPUT_FILE;
    Date debutValidite;
    if (line.hasOption(OPTION_DATE)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        debutValidite = sdf.parse(line.getOptionValue(OPTION_DATE));
      } catch (ParseException e) {
        log.warn("Exception parsing date {}", line.getOptionValue(OPTION_DATE));
        debutValidite = new Date();
      }
    } else {
      debutValidite = new Date();
    }
    log.debug("Parsed Command line: config={}, job={}, input={}, date={}, other properties={}",
              configFile, jobName, inputFile, debutValidite.toString(), properties.size());
    // Launch Job
    JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
    Job job = context.getBean(jobName, Job.class);
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", inputFile);
    jobBuilder.addDate("debutValidite", debutValidite);
    jobBuilder.addString("auditAuteur", "Batch");
    jobBuilder.addDate("auditDate", new Date());
    jobBuilder.addString("auditNote", "Import " + inputFile);
    String property;
    for (Object item : Collections.list(properties.propertyNames())) {
      property = (String) item;
      jobBuilder.addString(property, properties.getProperty(property));
    }
    JobParameters jobParameters = jobBuilder.toJobParameters();
    try {
      JobExecution execution = jobLauncher.run(job, jobParameters);
      log.info("Job Exit Status : {}", execution.getStatus());
    } catch (JobExecutionException e) {
      log.error("Job failed", e);
    }
  }
}
