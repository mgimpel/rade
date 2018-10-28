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
package fr.aesn.rade.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.dao.DelegationJpaDao;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.service.impl.DelegationServiceImpl;

/**
 * JUnit Test for DelegationService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestDelegationService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private DelegationJpaDao jpaDao;
  /** Service  to be tested. */
  private DelegationService service;

  /**
   * Set up the Test Environment.
   */
  @BeforeClass
  public static void setUpClass() {
    // create temporary database for Hibernate
    db = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.DERBY)
        .setScriptEncoding("UTF-8")
        .setName("testdb")
        .addScript("db/sql/create-tables.sql")
        .addScript("db/sql/insert-Delegation.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    service = new DelegationServiceImpl();
    ((DelegationServiceImpl)service).setDelegationJpaDao(jpaDao);
  }

  /**
   * Test getting a the list of all Delegations.
   */
  @Test
  public void testGettingDelegationList() {
    List<Delegation> list = service.getAllDelegation();
    assertNotNull("delegationService returned a null list", list);
    assertEquals(9, list.size());
    for (Delegation delegation : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    delegation);
    }
  }

  /**
   * Test getting a Delegation Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingDelegation() throws ParseException {
    Delegation delegation = service.getDelegationById("PPC");
    assertNotNull("Hibernate didn't return a Delegation", delegation);
    // Test IdentiteTiers fields
    assertEquals("Hibernate returned a Delegation, but the ID doesn't match",
                 "PPC", delegation.getCode());
    assertEquals("Hibernate returned a Delegation, but the Libelle doesn't match",
                 "Direction territoriale Paris Petite Couronne", delegation.getLibelle());
    assertEquals("Hibernate returned a Delegation, but the Acheminement doesn't match",
                 "Nanterre Cedex - France", delegation.getAcheminement());
    assertEquals("Hibernate returned a Delegation, but the Addresse1 doesn't match",
                 "Agence de l'eau Seine-Normandie", delegation.getAdresse1());
    assertEquals("Hibernate returned a Delegation, but the Addresse2 doesn't match",
                 "Direction territoriale Paris Petite Couronne", delegation.getAdresse2());
    assertEquals("Hibernate returned a Delegation, but the Addresse3 doesn't match",
                 "51, rue Salvador Allende", delegation.getAdresse3());
    assertEquals("Hibernate returned a Delegation, but the Addresse4 doesn't match",
                 "", delegation.getAdresse4());
    assertEquals("Hibernate returned a Delegation, but the Addresse5 doesn't match",
                 "", delegation.getAdresse5());
    assertEquals("Hibernate returned a Delegation, but the Code Postal doesn't match",
                 "92027", delegation.getCodePostal());
    assertEquals("Hibernate returned a Delegation, but the E-mail doesn't match",
                 "xxx", delegation.getEmail());
    assertEquals("Hibernate returned a Delegation, but the Fax doesn't match",
                 "01 41 20 16 09", delegation.getFax());
    assertEquals("Hibernate returned a Delegation, but the Site Web doesn't match",
                 "http://www.eau-seine-normandie.fr/", delegation.getSiteWeb());
    assertEquals("Hibernate returned a Delegation, but the Telephone doesn't match",
                 "01 41 20 16 00", delegation.getTelephone());
    assertEquals("Hibernate returned a Delegation, but the Telephone2 doesn't match",
                 "", delegation.getTelephone2());
    assertEquals("Hibernate returned a Delegation, but the Telephone3 doesn't match",
                 "", delegation.getTelephone3());
  }
}
