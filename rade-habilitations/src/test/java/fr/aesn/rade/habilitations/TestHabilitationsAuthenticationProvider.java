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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import fr.aesn.rade.habilitations.ws.HabilitationsUtilisateurSrv;
import org.junit.Ignore;

/**
 * Unit Tests for the Habilitations Authentication Provider.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestHabilitationsAuthenticationProvider {

  /** Habilitation Authentication Provider to test */
  private static HabilitationsAuthenticationProvider provider =
    new HabilitationsAuthenticationProvider();

  @Test(expected = BadCredentialsException.class)
  public void testAuthenticationNullToken() {
    // NB: Authentication is done in the additionalAuthenticationChecks method
    provider.additionalAuthenticationChecks(new User("user", "password", new ArrayList<>()),
                                            null);
    assertTrue(false); // Should never run because exception thrown above
  }

  @Test @Ignore 
  public void testAuthenticationSuccess() throws RemoteException {
    // NB: Authentication is done in the additionalAuthenticationChecks method
    HabilitationsUtilisateurSrv service = mock(HabilitationsUtilisateurSrv.class);
    when(service.authentification("user", "password")).thenReturn(true);
    provider.setHabilitationsService(service);
    provider.additionalAuthenticationChecks(new User("user", "password", new ArrayList<>()),
                                            new UsernamePasswordAuthenticationToken("user", "password", new ArrayList<>()));
    verify(service, times(1)).authentification(ArgumentMatchers.eq("user"), ArgumentMatchers.eq("password"));
  }
}
