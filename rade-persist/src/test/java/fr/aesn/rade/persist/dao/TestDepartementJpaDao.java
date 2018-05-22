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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.model.Departement;

/**
 * JUnit Test for DepartementJpaDao.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestDepartementJpaDao extends AbstractTestJpaDao {
  /** DAO to be tested. */
  @Autowired
  private DepartementJpaDao jpaDao;

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
        .addScript("db/sql/insert-StatutModification.sql")
        .addScript("db/sql/insert-TypeGenealogieEntiteAdmin.sql")
        .addScript("db/sql/insert-Audit.sql")
        .addScript("db/sql/insert-Region.sql")
        .addScript("db/sql/insert-RegionGenealogie.sql")
        .addScript("db/sql/insert-Departement.sql")
        .addScript("db/sql/insert-DepartementGenealogie.sql")
        .build();
  }

  /**
   * Test getting a the list of all Entity.
   */
  @Test
  public void testGettingEntityList() {
    List<Departement> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(173, list.size());
    for (Departement obj : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    obj);
    }
  }

  /**
   * Test existence of Entity.
   */
  @Test
  public void testExistsEntity() {
    assertTrue(jpaDao.existsById(101));
    assertTrue(jpaDao.existsById(201));
    assertFalse(jpaDao.existsById(300));
    assertFalse(jpaDao.existsById(0));
    assertFalse(jpaDao.existsById(-1));
  }

  /**
   * Test getting a Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingEntity() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Optional<Departement> result = jpaDao.findById(197);
    assertTrue("Hibernate didn't return an Entity", result.isPresent());
    Departement dept = result.get();
    assertEquals("Hibernate returned a Departement, but the Id doesn't match",
                 197, dept.getId().intValue());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 sdf.parse("1999-01-01"), dept.getDebutValidite());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 null, dept.getFinValidite());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 null, dept.getArticleEnrichi());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "GUADELOUPE", dept.getNomMajuscule());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "Guadeloupe", dept.getNomEnrichi());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "", dept.getCommentaire());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "3", dept.getTypeNomClair().getCode());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "DEP", dept.getTypeEntiteAdmin().getCode());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 1, dept.getAudit().getId().intValue());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "971", dept.getCodeInsee());
    assertEquals("vdept returned a Departement, but a field doesn't match",
                 "97105", dept.getChefLieu());
    assertEquals("vdept returned a Departement, but a field doesn't match",
                 "01", dept.getRegion());
  }

  /**
   */
  @Test
  public void testGettingEntitySearch() {
      Departement criteria = new Departement();
    criteria.setId(197);
    List<Departement> list = jpaDao.findAll(Example.of(criteria));
    assertEquals("", 1, list.size());
    Departement resultat = list.get(0);
    assertNotNull("", resultat);
  }
}
