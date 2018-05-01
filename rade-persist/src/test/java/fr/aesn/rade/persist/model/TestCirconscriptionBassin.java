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
 * JUnit Test for Entity CirconscriptionBassin.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on a database with a specific schema,
 * it is worth testing that the Entity behaves properly with the underlying
 * database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCirconscriptionBassin extends AbstractTestEntity {
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
        .build();
  }

  /**
   * Test equality of Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testEquality() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Audit audit = new Audit();
    audit.setId(1);
    audit.setAuteur("gimpelma");
    audit.setDate(sdf.parse("2018-04-01"));
    audit.setNote("Import initial");
    CirconscriptionBassin obj1 = new CirconscriptionBassin();
    CirconscriptionBassin obj2 = new CirconscriptionBassin();
    obj1.setCode("01");
    obj1.setLibelleCourt("AP");
    obj1.setLibelleLong("ARTOIS-PICARDIE");
    obj1.setAudit(audit);
    obj2.setCode("01");
    obj2.setLibelleCourt("AP");
    obj2.setLibelleLong("ARTOIS-PICARDIE");
    obj2.setAudit(audit);
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleCourt()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleLong()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAudit().getAuteur()));
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
    CirconscriptionBassin bassin = entityManager.find(CirconscriptionBassin.class, "01");
    assertNotNull("Hibernate didn't return a CirconscriptionBassin", bassin);
    assertEquals("Hibernate returned a CirconscriptionBassin, but the Id doesn't match",
                 "01", bassin.getCode());
    assertEquals("Hibernate returned a CirconscriptionBassin, but a field doesn't match",
                 "AP", bassin.getLibelleCourt());
    assertEquals("Hibernate returned a CirconscriptionBassin, but a field doesn't match",
                 "ARTOIS-PICARDIE", bassin.getLibelleLong());
    assertEquals("Hibernate returned a CirconscriptionBassin, but a field doesn't match",
                 1, bassin.getAudit().getId().intValue());
  }

  /**
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<CirconscriptionBassin> objs = entityManager.createQuery("FROM CirconscriptionBassin", CirconscriptionBassin.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 12, objs.size());
    for (CirconscriptionBassin obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
