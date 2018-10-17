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

import fr.aesn.rade.persist.model.CommuneSandre;

/**
 * JUnit Test for HexaposteJpaDao.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCommuneSandreJpaDao extends AbstractTestJpaDao {
  /** DAO to be tested. */
  @Autowired
  private CommuneSandreJpaDao jpaDao;

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
   * Test getting a the list of all Entity.
   */
  @Test
  public void testGettingEntityList() {
    List<CommuneSandre> list = jpaDao.findAll();
    assertNotNull("JpaDao returned a null list", list);
    assertEquals(100, list.size());
    for (CommuneSandre obj : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    obj);
    }
  }

  /**
   * Test existence of Entity.
   */
  @Test
  public void testExistsEntity() {
    assertTrue(jpaDao.existsById("01001"));
    assertFalse(jpaDao.existsById("abcde"));
    assertFalse(jpaDao.existsById("10000"));
  }

  /**
   * Test getting a Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingEntity() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Optional<CommuneSandre> result = jpaDao.findById("01001");
    assertTrue("Hibernate didn't return an Entity", result.isPresent());
    CommuneSandre commune = result.get();
    assertEquals("Hibernate returned a CommuneSandre, but the Code INSEE doesn't match",
                 "01001", commune.getCodeCommune());
    assertEquals("Hibernate returned a CommuneSandre, but the Libelle Commune doesn't match",
                 "L'ABERGEMENT-CLÉMENCIAT", commune.getLibelleCommune());
    assertEquals("Hibernate returned a CommuneSandre, but the Statut Commune doesn't match",
                 "Validé", commune.getStatutCommune());
    assertEquals("Hibernate returned a CommuneSandre, but the Date de Creation doesn't match",
                 sdf.parse("2002-01-01"), commune.getDateCreationCommune());
    assertEquals("Hibernate returned a CommuneSandre, but the Date de MaJ doesn't match",
                 sdf.parse("2016-01-01"), commune.getDateMajCommune());
    assertEquals("Hibernate returned a CommuneSandre, but the Code Bassin DCE doesn't match",
                 "D", commune.getCodeBassinDce());
    assertEquals("Hibernate returned a CommuneSandre, but the Code EU District doesn't match",
                 "EU35", commune.getCodeEuDistrict());
    assertEquals("Hibernate returned a CommuneSandre, but the Circonscription Bassin doesn't match",
                 "06", commune.getCirconscriptionBassin().getCode());
    assertEquals("Hibernate returned a CommuneSandre, but the Code Comite Bassin doesn't match",
                 "FR000006", commune.getCodeComiteBassin());
    assertEquals("Hibernate returned a CommuneSandre, but the Audit doesn't match",
                 1, commune.getAudit().getId().intValue());
  }

  /**
   */
  @Test
  public void testGettingEntitySearch() {
    CommuneSandre criteria = new CommuneSandre();
    criteria.setCodeCommune("01001");
    List<CommuneSandre> list = jpaDao.findAll(Example.of(criteria));
    assertEquals("", 1, list.size());
    CommuneSandre resultat = list.get(0);
    assertNotNull("", resultat);
  }
}
