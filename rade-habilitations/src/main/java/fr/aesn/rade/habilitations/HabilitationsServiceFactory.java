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

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrv;
import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrvServiceLocator;

/**
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class HabilitationsServiceFactory {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HabilitationsServiceFactory.class);

  /**
   * URL for Habilitations WebService.
   * (Default value corresponds to the Homolgation Environment - Should not be hardcoded)
   * */
  private String habilitationsWsdlUrl = "http://10.81.62.22:8180/Habilitations/services/HabilitationsUtilisateurService?wsdl";

  /** The Habilitation WebService that this factory provides. */
  private HabilitationsUtilisateurSrv habilitationsService = null;

  /**
   * Return the Habilitation WebService Stub.
   * If it is null (not yet been called), then try to instantiate it.
   * @return the Habilitation WebService Stub.
   */
  public HabilitationsUtilisateurSrv getHabilitationsService() {
    if (habilitationsService == null) {
      HabilitationsUtilisateurSrvServiceLocator locator = new HabilitationsUtilisateurSrvServiceLocator();
      locator.setHabilitationsUtilisateurServiceEndpointAddress(habilitationsWsdlUrl);
      try {
        habilitationsService = locator.getHabilitationsUtilisateurService();
      } catch (ServiceException e) {
        log.warn("Could not instanciate Habilitations WebService", e);
      }
    }
    return habilitationsService;
  }

  /**
   * Sets the URL for Habilitations WebService (replaces default value).
   * This must be set before getHabilitationsService() is called.
   * @param habilitationsWsdlUrl URL for Habilitations WebService.
   */
  public void setHabilitationsWsdlUrl(String habilitationsWsdlUrl) {
    this.habilitationsWsdlUrl = habilitationsWsdlUrl;
  }
}
