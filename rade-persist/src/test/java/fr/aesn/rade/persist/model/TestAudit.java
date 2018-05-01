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
 * JUnit Test for Entity Audit.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on a database with a specific schema,
 * it is worth testing that the Entity behaves properly with the underlying
 * database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestAudit extends AbstractTestEntity {
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
        .build();
  }

  /**
   * Test equality of Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testEquality() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Audit obj1 = new Audit();
    Audit obj2 = new Audit();
    obj1.setId(1);
    obj1.setAuteur("gimpelma");
    obj1.setDate(sdf.parse("2018-04-01"));
    obj1.setNote("Import initial");
    obj2.setId(1);
    obj2.setAuteur("gimpelma");
    obj2.setDate(sdf.parse("2018-04-01"));
    obj2.setNote("Import initial");
    assertTrue("String should contain value", obj1.toString().contains("1"));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAuteur()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getDate().toString()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getNote()));
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
    Audit audit = entityManager.find(Audit.class, 1);
    assertNotNull("Hibernate didn't return a Audit", audit);
    assertEquals("Hibernate returned a Audit, but the Id doesn't match",
                 1, audit.getId().intValue());
    assertEquals("Hibernate returned a Audit, but the Author doesn't match",
                 "gimpelma", audit.getAuteur());
    assertEquals("Hibernate returned a Audit, but the Date doesn't match",
                 sdf.parse("2018-04-01"), audit.getDate());
    assertEquals("Hibernate returned a Audit, but the Author doesn't match",
                 "Import initial", audit.getNote());
  }

  /**
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<Audit> objs = entityManager.createQuery("FROM Audit", Audit.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 1, objs.size());
    for (Audit obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
