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
package fr.aesn.rade.webapp.mvc.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring MVC Controller for Rade Batch processing.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
@Controller
@RequestMapping(BatchController.REQUEST_MAPPING)
public class BatchController {
  /** RequestMapping for this Controller. */
  public static final String REQUEST_MAPPING = "/batch";
  /** I18n message source. */
  @Autowired
  private MessageSource messageSource;
  /** Spring Batch Job Launcher. */
  @Autowired
  private JobLauncher jobLauncher;
  /** Batch Info Job. */
  @Autowired
  @Qualifier("webInfoJob")
  private Job infoJob;
  /** Batch Import Communes Sandre Job. */
  @Autowired
  @Qualifier("webImportCommuneSandreJob")
  private Job sandreJob;
  /** Batch Import Delegation Job. */
  @Autowired
  @Qualifier("webImportDelegationJob")
  private Job delegationJob;

  /**
   * Main Batch Page mapping.
   * @param model MVC model passed to JSP.
   * @return View for the Batch Upload page
   */
  @GetMapping()
  public String home(final Model model) {
    model.addAttribute("titre", "Batch");
    return "batch";
  }

  /**
   * Info Batch Upload mapping.
   * @param locale locale in which to do the lookup.
   * @param model MVC model passed to JSP.
   * @return View for the Batch Upload page
   */
  @GetMapping("/info")
  public String infoGet(final Locale locale,
                        final Model model) {
    log.debug("Requesting /batch/info");
    model.addAttribute("titre", messageSource.getMessage("batchrequest.title.info", null, locale));
    model.addAttribute("postpath", "/batch/info");
    return "batchrequest";
  }

  /**
   * Upload file and run Info batch.
   * @param locale locale in which to do the lookup.
   * @param model MVC model passed to JSP.
   * @param file the HTTP submitted file. 
   * @return View for the page.
   * @throws IOException if there was a problem recovering and saving file.
   */
  @PostMapping("/info")
  public String infoPost(final Locale locale,
                         final Model model,
                         @RequestParam("file") final MultipartFile file)
    throws IOException {
    log.debug("Posting to /batch/info");
    model.addAttribute("titre", messageSource.getMessage("batchresult.title.info", null, locale));
    Path tmpFile = storeTempFile(file);
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", tmpFile.toUri().toString());
    jobBuilder.addString("auditAuteur", "WebBatch");
    jobBuilder.addDate("auditDate", new Date());
    jobBuilder.addString("auditNote", "Import " + file.getOriginalFilename());
    model.addAttribute("file", file);
    model.addAttribute("uri", tmpFile.toUri());
    JobExecution exec = runSynchronousJob(infoJob, jobBuilder.toJobParameters());
    if (exec != null) {
      model.addAttribute("params", exec.getJobParameters().getParameters());
      model.addAttribute("status", exec.getExitStatus());
    }
    return "batchresult";
  }

  /**
   * Sandre Batch Upload mapping.
   * @param locale locale in which to do the lookup.
   * @param model MVC model passed to JSP.
   * @return View for the Batch Upload page
   */
  @GetMapping("/sandre")
  public String sandreGet(final Locale locale,
                          final Model model) {
    log.debug("Requesting /batch/sandre");
    model.addAttribute("titre", messageSource.getMessage("batchrequest.title.sandre", null, locale));
    model.addAttribute("postpath", "/batch/sandre");
    return "batchrequest";
  }

  /**
   * Upload file and run Sandre import batch.
   * @param locale locale in which to do the lookup.
   * @param model MVC model passed to JSP.
   * @param file the HTTP submitted file. 
   * @return View for the page.
   * @throws IOException if there was a problem recovering and saving file.
   */
  @PostMapping("/sandre")
  public String sandrePost(final Locale locale,
                           final Model model,
                           @RequestParam("file") final MultipartFile file)
    throws IOException {
    log.debug("Posting to /batch/sandre");
    model.addAttribute("titre", messageSource.getMessage("batchresult.title.sandre", null, locale));
    Path tmpFile = storeTempFile(file);
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", tmpFile.toUri().toString());
    jobBuilder.addString("auditAuteur", "WebBatch");
    jobBuilder.addDate("auditDate", new Date());
    jobBuilder.addString("auditNote", "Import " + file.getOriginalFilename());
    model.addAttribute("file", file);
    model.addAttribute("uri", tmpFile.toUri());
    runAsynchronousJob(sandreJob, jobBuilder.toJobParameters());
    model.addAttribute("message", "For more details see <a href=\"../actuator/logfile\" target=\"_blank\">log file</a>");
    return "batchresult";
  }

  /**
   * Delegation Batch Upload mapping.
   * @param locale locale in which to do the lookup.
   * @param model MVC model passed to JSP.
   * @return View for the Batch Upload page
   */
  @GetMapping("/delegation")
  public String delegationGet(final Locale locale,
                              final Model model) {
    log.debug("Requesting /batch/delegation");
    model.addAttribute("titre", messageSource.getMessage("batchrequest.title.delegation", null, locale));
    model.addAttribute("postpath", "/batch/delegation");
    return "batchrequest";
  }

  /**
   * Upload file and run Delegation batch.
   * @param locale locale in which to do the lookup.
   * @param model MVC model passed to JSP.
   * @param file the HTTP submitted file. 
   * @return View for the page.
   * @throws IOException if there was a problem recovering and saving file.
   */
  @PostMapping("/delegation")
  public String delegationPost(final Locale locale,
                               final Model model,
                               @RequestParam("file") final MultipartFile file)
    throws IOException {
    log.debug("Posting to /batch/delegation");
    model.addAttribute("titre", messageSource.getMessage("batchresult.title.delegation", null, locale));
    Path tmpFile = storeTempFile(file);
    JobParametersBuilder jobBuilder = new JobParametersBuilder();
    jobBuilder.addString("inputFile", tmpFile.toUri().toString());
    model.addAttribute("file", file);
    model.addAttribute("uri", tmpFile.toUri());
    JobExecution exec = runSynchronousJob(delegationJob, jobBuilder.toJobParameters());
    if (exec != null) {
      model.addAttribute("params", exec.getJobParameters().getParameters());
      model.addAttribute("status", exec.getExitStatus());
    }
    model.addAttribute("message", "For more details see <a href=\"../actuator/logfile\" target=\"_blank\">log file</a>");
    return "batchresult";
  }

  /**
   * Run the given Spring Batch Job with the given Parameters in the current
   * Thread.
   * @param job Spring Batch Job
   * @param params Spring Batch Job Parameters
   * @return Spring Batch Job Execution details
   */
  private JobExecution runSynchronousJob(final Job job,
                                         final JobParameters params) {
    JobExecution exec = null;
    try {
      exec = jobLauncher.run(job, params);
      log.info("Job ({}) completed: {}", job.getName(), exec);
    } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
      log.warn("Exception running Job ({})", job.getName(), e);
    }
    return exec;
  }

  /**
   * Run the given Spring Batch Job with the given Parameters in a new Thread
   * so that the page request may return.
   * @param job Spring Batch Job
   * @param params Spring Batch Job Parameters
   */
  private void runAsynchronousJob(final Job job,
                                  final JobParameters params) {
    ForkJoinPool.commonPool().submit(() -> runSynchronousJob(job, params));
  }

  /**
   * Store the HTTP submitted file in a temporary location.
   * @param file the HTTP submitted file. 
   * @return the path to the temporary file.
   * @throws IOException if there was a problem recovering and saving file.
   */
  private static Path storeTempFile(final MultipartFile file)
    throws IOException {
    Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir")
                          + File.separator + "rade" + File.separator
                          + "uploads" + File.separator)
                       .toAbsolutePath()
                       .normalize();
    if (!Files.exists(tmpDir)) {
      log.info("Creating Temporary Directory: {}", tmpDir);
      Files.createDirectories(tmpDir);
    }
    Path tmpFile = Files.createTempFile(tmpDir, file.getOriginalFilename(), null);
    Files.copy(file.getInputStream(), tmpFile, StandardCopyOption.REPLACE_EXISTING);
    return tmpFile;
  }
}
