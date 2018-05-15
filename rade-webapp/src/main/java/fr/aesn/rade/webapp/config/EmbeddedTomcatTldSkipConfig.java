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
package fr.aesn.rade.webapp.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for Embedded Tomcat used by Java Boot.
 * In particular it defines the jars to ignore for TLD scanning
 * (this is useful because for example Apache Derby defines multiple
 * localisation jars in it's manifest, and when the are not all on the
 * classpath, the logs are polluted with Stacktraces).
 * It can be activated by using the Spring Profile "test".
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Configuration
@Profile("test")
public class EmbeddedTomcatTldSkipConfig {

//  @Value("${tomcat.tldSkipPatterns}")
  private String[] tldSkipPatterns = {"derbyLocale_*.jar"};

  /**
   * Configures the Embedded Tomcat to ignore certain jars during scanning.
   * @return WebServerFactoryCustomizer for Tomcat.
   */
  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    return server -> server.addTldSkipPatterns(tldSkipPatterns);
  }
}
