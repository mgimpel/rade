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
package fr.aesn.rade.persist.model;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * JUnit Test for Entity Region.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on a database with a specific schema,
 * it is worth testing that the Entity behaves properly with the underlying
 * database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestRegion extends AbstractTestEntity {
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
   * Test equality of Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testEquality() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    TypeEntiteAdmin type = new TypeEntiteAdmin();
    type.setCode("REG");
    type.setLibelleCourt("Région");
    TypeNomClair tncc = new TypeNomClair();
    tncc.setCode("3");
    tncc.setArticle("LA");
    tncc.setCharniere("DE LA");
    tncc.setArticleMaj(null);
    Audit audit = new Audit();
    audit.setId(1);
    audit.setAuteur("gimpelma");
    audit.setDate(sdf.parse("2018-04-01"));
    audit.setNote("Import initial");
    Region obj1 = new Region();
    Region obj2 = new Region();
    obj1.setId(1);
    obj1.setDebutValidite(sdf.parse("2018-04-01"));
    obj1.setFinValidite(null);
    obj1.setArticleEnrichi(null);
    obj1.setNomMajuscule("GUADELOUPE");
    obj1.setNomEnrichi("Guadeloupe");
    obj1.setCommentaire("");
    obj1.setTypeNomClair(tncc);
    obj1.setTypeEntiteAdmin(type);
    obj1.setAudit(audit);
    obj1.setCodeInsee("01");
    obj1.setChefLieu("97105");
    obj2.setId(1);
    obj2.setDebutValidite(sdf.parse("2018-04-01"));
    obj2.setFinValidite(null);
    obj2.setArticleEnrichi(null);
    obj2.setNomMajuscule("GUADELOUPE");
    obj2.setNomEnrichi("Guadeloupe");
    obj2.setCommentaire("");
    obj2.setTypeNomClair(tncc);
    obj2.setTypeEntiteAdmin(type);
    obj2.setAudit(audit);
    obj2.setCodeInsee("01");
    obj2.setChefLieu("97105");
    assertTrue("String should contain value", obj1.toString().contains("1"));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getDebutValidite().toString()));
    assertTrue("String should contain value", obj1.toString().contains("null"));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getNomMajuscule()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getNomEnrichi()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCommentaire()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTypeNomClair().getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAudit().getAuteur()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeInsee()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getChefLieu()));
    assertEquals("Object should be equal to itself", obj1, obj1);
    assertEquals("Identically created Objects are supposed to be equal", obj1, obj2);
    assertEquals("Identically created Objects are supposed to be equal", obj2, obj1);
    assertTrue("Identically created Objects are supposed to be equal", obj1.equals(obj2));
    assertTrue("Identically created Objects are supposed to be equal", obj2.equals(obj1));
    assertEquals("Identically created Objects are supposed to be equal", obj1.hashCode(), obj2.hashCode());
    assertTrue("Should be equal to itself", obj1.equals(obj1));
    assertFalse("Shouldn't be equals to null", obj1.equals(null));
    assertFalse("Shouldn't be equals to a different class", obj1.equals(new Object()));
  }

  /**
   * Test getting an Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingAnEntity() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Region region = entityManager.find(Region.class, 1);
    assertNotNull("Hibernate didn't return a Region", region);
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
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<Region> objs = entityManager.createQuery("FROM Region", Region.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 42, objs.size());
    for (Region obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
