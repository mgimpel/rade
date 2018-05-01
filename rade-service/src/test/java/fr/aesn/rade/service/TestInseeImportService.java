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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.DepartementJpaDao;
import fr.aesn.rade.persist.dao.RegionJpaDao;
import fr.aesn.rade.service.impl.InseeImportServiceImpl;

/**
 * JUnit Test for InseeImportService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestInseeImportService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private RegionJpaDao regionJpaDao;
  /** DAO for the Service to be tested. */
  @Autowired
  private DepartementJpaDao departementJpaDao;
  /** DAO for the Service to be tested. */
  @Autowired
  private CommuneJpaDao communeJpaDao;
  /** Service to be tested. */
  private InseeImportService inseeImportService;

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
        .addScript("db/sql/insert-TypeEntiteAdmin.sql")
        .addScript("db/sql/insert-TypeNomClair.sql")
        .addScript("db/sql/insert-Audit.sql")
        .addScript("db/sql/insert-CirconscriptionBassin.sql")
        .addScript("db/sql/insert-Region.sql")
        .addScript("db/sql/insert-Departement.sql")
        .addScript("db/sql/insert-Commune-Test.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    inseeImportService = new InseeImportServiceImpl();
    ((InseeImportServiceImpl)inseeImportService).setRegionJpaDao(regionJpaDao);
    ((InseeImportServiceImpl)inseeImportService).setDepartementJpaDao(departementJpaDao);
    ((InseeImportServiceImpl)inseeImportService).setCommuneJpaDao(communeJpaDao);
  }

  @Test
  public void testParseInseeFile() throws IOException {
    testParseInseeFile("insee/reg2018.txt", 19, 5);
    testParseInseeFile("insee/depts2018.txt", 102, 6);
    testParseInseeFile("insee/comsimp2018.txt", 35358, 12);
    testParseInseeFile("insee/historiq2018.txt", 16291, 23);
  }

  public void testParseInseeFile(String file, int rows, int cols) throws IOException {
    ClassLoader cl = getClass().getClassLoader();
    BufferedReader br = new BufferedReader(new InputStreamReader(cl.getResourceAsStream(file)));
    List<String[]> list = InseeImportServiceImpl.TabSeparatedValueToList(br);
    assertNotNull("Couldn't find INSEE file " + file, list);
    assertEquals("Wrong number of rows for file " + file, rows, list.size());
    for (String[] line : list) {
      assertEquals("Wrong number of cols for file " + file, cols, line.length);
    }
  }
}
