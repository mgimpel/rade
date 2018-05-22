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

import fr.aesn.rade.persist.model.Region;

/**
 * JUnit Test for RegionJpaDao.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestRegionJpaDao extends AbstractTestJpaDao {
  /** DAO to be tested. */
  @Autowired
  private RegionJpaDao jpaDao;

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
        .addScript("db/sql/insert-Region.sql")
        .build();
  }

  /**
   * Test getting a the list of all Entity.
   */
  @Test
  public void testGettingEntityList() {
    List<Region> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(42, list.size());
    for (Region obj : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    obj);
    }
  }

  /**
   * Test existence of Entity.
   */
  @Test
  public void testExistsEntity() {
    assertTrue(jpaDao.existsById(1));
    assertTrue(jpaDao.existsById(42));
    assertFalse(jpaDao.existsById(43));
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
    Optional<Region> result = jpaDao.findById(1);
    assertTrue("Hibernate didn't return an Entity", result.isPresent());
    Region region = result.get();
    assertEquals("Hibernate returned a Region, but the Id doesn't match",
                 1, region.getId().intValue());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 sdf.parse("1999-01-01"), region.getDebutValidite());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 null, region.getFinValidite());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 null, region.getArticleEnrichi());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "GUADELOUPE", region.getNomMajuscule());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "Guadeloupe", region.getNomEnrichi());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "", region.getCommentaire());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "3", region.getTypeNomClair().getCode());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "REG", region.getTypeEntiteAdmin().getCode());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 1, region.getAudit().getId().intValue());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "01", region.getCodeInsee());
    assertEquals("Hibernate returned a Region, but a field doesn't match",
                 "97105", region.getChefLieu());
  }

  /**
   */
  @Test
  public void testGettingEntitySearch() {
    Region criteria = new Region();
    criteria.setId(1);
    List<Region> list = jpaDao.findAll(Example.of(criteria));
    assertEquals("", 1, list.size());
    Region resultat = list.get(0);
    assertNotNull("", resultat);
  }
}
