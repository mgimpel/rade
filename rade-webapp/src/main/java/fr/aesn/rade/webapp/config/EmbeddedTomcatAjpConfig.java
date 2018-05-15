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

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for Embedded Tomcat used by Java Boot.
 * In particular it adds an AJP Connector, allowing one to put a Apache HTTPD
 * reverse-proxy front end.
 * It can be activated by using the Spring Profile "ajp".
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Configuration
@Profile("ajp")
public class EmbeddedTomcatAjpConfig {

  /**
   * AJP port to use (value defined in Spring Boot configuration file
   * application.properties or defaults to 8009).
   */
  @Value("${tomcat.ajp.port:8009}")
  private int ajpPort;

  /**
   * Configures the Embedded Tomcat by adding an AJP Connector.
   * @return WebServerFactoryCustomizer for Tomcat.
   */
  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    return server -> server.addAdditionalTomcatConnectors(ajpConnector(ajpPort));
  }

  /**
   * Returns a Tomcat AJP Connector listening on the given port.
   * @param ajpPort the port for the Connector to use.
   * @return a Tomcat AJP Connector.
   */
  private final Connector ajpConnector(final int ajpPort) {
    Connector connector = new Connector("AJP/1.3");
    connector.setScheme("http");
    connector.setPort(ajpPort);
    connector.setSecure(false);
    connector.setAllowTrace(false);
    return connector;
  }
}
