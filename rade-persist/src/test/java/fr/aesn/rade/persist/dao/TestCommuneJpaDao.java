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

import fr.aesn.rade.persist.model.Commune;

/**
 * JUnit Test for CommuneJpaDao.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCommuneJpaDao extends AbstractTestJpaDao {
  /** DAO to be tested. */
  @Autowired
  private CommuneJpaDao jpaDao;

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
   * Test getting a the list of all Entity.
   */
  @Test
  public void testGettingEntityList() {
    List<Commune> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(632, list.size());
    for (Commune obj : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    obj);
    }
  }

  /**
   * Test existence of Entity.
   */
  @Test
  public void testExistsEntity() {
    assertTrue(jpaDao.existsById(134726));
    assertTrue(jpaDao.existsById(135357));
    assertFalse(jpaDao.existsById(200000));
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
    Optional<Commune> result = jpaDao.findById(135233);
    assertTrue("Hibernate didn't return an Entity", result.isPresent());
    Commune com = result.get();
    assertEquals("Hibernate returned a Commune, but the Id doesn't match",
                 135233, com.getId().intValue());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 sdf.parse("2018-01-01"), com.getDebutValidite());
    assertNull("Hibernate returned a Commune, but a field doesn't match",
               com.getFinValidite());
    assertNull("Hibernate returned a Commune, but a field doesn't match",
               com.getArticleEnrichi());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "BASSE-TERRE", com.getNomMajuscule());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "Basse-Terre", com.getNomEnrichi());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "", com.getCommentaire());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "0", com.getTypeNomClair().getCode());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "COM", com.getTypeEntiteAdmin().getCode());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 1, com.getAudit().getId().intValue());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "97105", com.getCodeInsee());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "O", com.getIndicateurUrbain());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "01", com.getCirconscriptionBassin().getCode());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "971", com.getDepartement());
  }

  /**
   */
  @Test
  public void testGettingEntitySearch() {
      Commune criteria = new Commune();
    criteria.setId(135233);
    List<Commune> list = jpaDao.findAll(Example.of(criteria));
    assertEquals("", 1, list.size());
    Commune resultat = list.get(0);
    assertNotNull("", resultat);
  }
}
