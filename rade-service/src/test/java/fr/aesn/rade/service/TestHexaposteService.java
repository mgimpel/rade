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

import java.util.List;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.dao.HexaposteJpaDao;
import fr.aesn.rade.persist.model.Hexaposte;
import fr.aesn.rade.service.impl.HexaposteServiceImpl;

/**
 * JUnit Test for HexaposteService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestHexaposteService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private HexaposteJpaDao jpaDao;
  /** Service  to be tested. */
  private HexaposteService service;

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
        .addScript("db/sql/insert-Audit.sql")
        .addScript("db/sql/insert-Hexaposte-Test.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    service = new HexaposteServiceImpl();
    ((HexaposteServiceImpl)service).setHexaposteJpaDao(jpaDao);
  }

  /**
   * Test getting a the list of Hexaposte using Code Postal.
   */
  @Test
  public void testGetHexaposteByCodePostal() {
    List<Hexaposte> list = service.getHexposteByCodePostal("01400");
    assertNotNull("Service returned a null list", list);
    assertEquals(1, list.size());
    Hexaposte hexaposte = list.get(0);
    assertEquals("Hibernate returned a Hexaposte, but the ID doesn't match",
                 "2204", hexaposte.getIdentifiant());
    assertEquals("Hibernate returned a Hexaposte, but the Code INSEE doesn't match",
                 "01001", hexaposte.getCodeInseeCommune());
    assertEquals("Hibernate returned a Hexaposte, but the Libelle Commune doesn't match",
                 "L ABERGEMENT CLEMENCIAT", hexaposte.getLibelleCommune());
    assertEquals("Hibernate returned a Hexaposte, but the Indicateur Pluridistribution doesn't match",
                 0, hexaposte.getIndicateurPluridistribution().intValue());
    assertEquals("Hibernate returned a Hexaposte, but the Type Code Postal doesn't match",
                 "M", hexaposte.getTypeCodePostal());
    assertNull("Hibernate returned a Hexaposte, but the Libelle Ligne 5 doesn't match",
               hexaposte.getLibelleLigne5());
    assertEquals("Hibernate returned a Hexaposte, but the Code Postal doesn't match",
                 "01400", hexaposte.getCodePostal());
    assertEquals("Hibernate returned a Hexaposte, but the Libelle Acheminement doesn't match",
                 "L ABERGEMENT CLEMENCIAT", hexaposte.getLibelleAcheminement());
    assertNull("Hibernate returned a Hexaposte, but the Code INSEE old Commune doesn't match",
               hexaposte.getCodeInseeAncienneCommune());
    assertNull("Hibernate returned a Hexaposte, but the Code MaJ doesn't match",
               hexaposte.getCodeMaJ());
    assertNull("Hibernate returned a Hexaposte, but the Code Etendu Adresse doesn't match",
               hexaposte.getCodeEtenduAdresse());
  }

  /**
   * Test getting a the list of Libelle Acheminement using Code Postal.
   */
  @Test
  public void testGetLibelleAcheminementByCodePostal() {
    List<String> list = service.getLibelleAcheminementByCodePostal("01400");
    assertNotNull("Service returned a null list", list);
    assertEquals(1, list.size());
    String libelle = list.get(0);
    assertEquals("Hibernate returned a Libelle Acheminement but it doesn't match",
                 "L ABERGEMENT CLEMENCIAT", libelle);
  }
}
