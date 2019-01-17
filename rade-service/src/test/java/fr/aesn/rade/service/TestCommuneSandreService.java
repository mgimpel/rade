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

import fr.aesn.rade.persist.dao.CommuneSandreJpaDao;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.service.impl.CommuneSandreServiceImpl;

/**
 * JUnit Test for CommuneSandreService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCommuneSandreService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private CommuneSandreJpaDao jpaDao;
  /** Service to be tested. */
  private CommuneSandreService service;

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
        .addScript("db/sql/insert-CirconscriptionBassin.sql")
        .addScript("db/sql/insert-CommuneSandre-Test.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    service = new CommuneSandreServiceImpl();
    ((CommuneSandreServiceImpl)service).setCommuneSandreJpaDao(jpaDao);
  }

  /**
   * Test deleting all Commune Sandre.
   */
  @Test
  public void testDeleteAll() {
    List<CommuneSandre> list;
    list = jpaDao.findAll();
    assertNotNull("Service returned a null list", list);
    assertEquals("Wrong number of Commune Sandre returned", 101, list.size());
    service.deleteAll();
    list = jpaDao.findAll();
    assertNotNull("Service returned a null list", list);
    assertEquals("There should be no Commune Sandre left", 0, list.size());
  }
}
