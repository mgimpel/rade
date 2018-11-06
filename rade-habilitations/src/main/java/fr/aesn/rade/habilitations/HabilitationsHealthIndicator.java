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
package fr.aesn.rade.habilitations;

import java.rmi.RemoteException;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrv;
import lombok.Setter;

/**
 * Spring Boot Actuator Health Indicator for the Habilitation WebService.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class HabilitationsHealthIndicator
  implements HealthIndicator {
  /** The WebService Factory that provides the Service Stub. */
  @Setter
  private HabilitationsServiceFactory factory;
  /** The WebService Stub that queries the Habilitations server. */
  private HabilitationsUtilisateurSrv service;

  /**
   * Return an indication of health of the Habilitation WebService.
   * @return the health status of the Habilitation WebService.
   */
  @Override
  public Health health() {
    //TODO Status=DEGRADED
    // When the habilitation WebService is down the whole application is not
    // down, just the Web UI (the WebServices are still working).
    // As such it would be better to define a status "DEGRADED" with
    // Health.status("DEGRADED").build() but for this to work one muts also
    // define the following application property:
    // management.health.status.order=DOWN,OUT_OF_SERVICE,UNKNOWN,DEGRADED,UP
    if (factory == null) {
      return Health.down()
                   .withDetail("error msg", "WebService factory is null")
                   .build();
    }
    if (service == null) {
      service = factory.getHabilitationsService();
    }
    try {
      if (service != null && "mail".equals(service.getAttributCourriel())) {
        return Health.up()
                     .withDetail("url", factory.getHabilitationsWsdlUrl())
                     .build();
      }
    } catch (RemoteException e) {
      return Health.down(e)
                   .withDetail("url", factory.getHabilitationsWsdlUrl())
                   .withDetail("error msg", "WebService Exception")
                   .build();
    }
    return Health.down()
                 .withDetail("url", factory.getHabilitationsWsdlUrl())
                 .withDetail("error msg", "WebService returned wrong value")
                 .build();
  }
}
