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

import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrv;
import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrvServiceLocator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory That builds and returns the Habilitations WebService.
 * NB: This WebService is based on Axis1, because Habilitations is so old that
 * it's WebServices are not compatible with more recent libraries such as
 * CXF and Axis2. The classes are automatically generated from the WSDL by
 * Maven during the build process.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public class HabilitationsServiceFactory {
  /** Default URL for Habilitations WebService. */
  public static final String DEFAULT_HOMOLOGATION_URL =
    "http://localhost:8080/Habilitations/services/HabilitationsUtilisateurService?wsdl";

  /** URL for Habilitations WebService. */
  @Getter @Setter
  private String habilitationsWsdlUrl = DEFAULT_HOMOLOGATION_URL;

  /** The Habilitation WebService that this factory provides. */
  private HabilitationsUtilisateurSrv habilitationsService = null;

  /**
   * Return the Habilitation WebService Stub.
   * If it is null (not yet been called), then try to instantiate it.
   * NB: to use a WSDL URL different from the default value, the
   * setHabilitationsWsdlUrl(String url) method must be called before this
   * method is called for the first time.
   * @return the Habilitation WebService Stub.
   */
  public HabilitationsUtilisateurSrv getHabilitationsService() {
    if (habilitationsService == null) {
      HabilitationsUtilisateurSrvServiceLocator locator =
        new HabilitationsUtilisateurSrvServiceLocator();
      locator.setHabilitationsUtilisateurServiceEndpointAddress(habilitationsWsdlUrl);
      try {
        habilitationsService = locator.getHabilitationsUtilisateurService();
      } catch (ServiceException e) {
        log.warn("Could not instanciate Habilitations WebService", e);
      }
    }
    return habilitationsService;
  }
}
