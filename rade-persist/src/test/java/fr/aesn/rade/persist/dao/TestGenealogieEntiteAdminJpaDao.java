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
package fr.aesn.rade.persist.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;

/**
 * JUnit Test for CommuneJpaDao.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenealogieEntiteAdminJpaDao extends AbstractTestJpaDao {
  /** DAO to be tested. */
  @Autowired
  private GenealogieEntiteAdminJpaDao jpaDao;

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
        .addScript("db/sql/insert-StatutModification.sql")
        .addScript("db/sql/insert-TypeEntiteAdmin.sql")
        .addScript("db/sql/insert-TypeGenealogieEntiteAdmin.sql")
        .addScript("db/sql/insert-TypeNomClair.sql")
        .addScript("db/sql/insert-Audit.sql")
        .addScript("db/sql/insert-CirconscriptionBassin.sql")
        .addScript("db/sql/insert-Region.sql")
        .addScript("db/sql/insert-RegionGenealogie.sql")
        .addScript("db/sql/insert-Departement.sql")
        .addScript("db/sql/insert-DepartementGenealogie.sql")
        .addScript("db/sql/insert-Commune-Test.sql")
        .addScript("db/sql/insert-CommuneGenealogie-Test.sql")
        .build();
  }

  /**
   * Test getting a the list of all Genealogies.
   */
  @Test
  public void testStep0GettingAll() {
    List<GenealogieEntiteAdmin> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    // Genealogies: 5 Communes + 72 Departements + 24 Regions = 101
    assertEquals(101, list.size());
    for (GenealogieEntiteAdmin obj : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    obj);
    }
  }

  /**
   * Test deleting only Commune Genealogies.
   */
  @Test
  public void testStep1DeleteAllCommune() {
    jpaDao.deleteAllCommune();
    List<GenealogieEntiteAdmin> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(96, list.size()); // 101 - 5
  }

  /**
   * Test deleting only Departement Genealogies.
   */
  @Test
  public void testStep2DeleteAllDepartement() {
    jpaDao.deleteAllDepartement();
    List<GenealogieEntiteAdmin> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(24, list.size()); // 96 - 72
  }

  /**
   * Test deleting only Region Genealogies.
   */
  @Test
  public void testStep3DeleteAllRegion() {
    jpaDao.deleteAllRegion();
    List<GenealogieEntiteAdmin> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(0, list.size()); // 24 - 24
  }
}
