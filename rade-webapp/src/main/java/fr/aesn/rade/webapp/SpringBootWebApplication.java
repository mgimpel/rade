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
package fr.aesn.rade.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Boot Launcher class.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
@SpringBootApplication
@ImportResource("classpath:application-context.xml")
@ComponentScan({"fr.aesn.rade.webapp.config",
                "fr.aesn.rade.webapp.controller"})
public class SpringBootWebApplication
  extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
    return application.sources(SpringBootWebApplication.class);
  }

  /**
   * Command line entrance.
   * @param args Command line arguments
   */
  public static void main(final String[] args) {
    log.info("Starting Rade ...");
    SpringApplication.run(SpringBootWebApplication.class, args);
  }
}
