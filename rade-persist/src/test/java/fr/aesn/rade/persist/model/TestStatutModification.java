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

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * JUnit Test for Entity StatutModification.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on a database with a specific schema,
 * it is worth testing that the Entity behaves properly with the underlying
 * database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestStatutModification extends AbstractTestEntity {
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
        .build();
  }

  /**
   * Test equality of Entity.
   */
  @Test
  public void testEquality() {
    StatutModification obj1 = new StatutModification();
    StatutModification obj2 = new StatutModification();
    obj1.setCode("I");
    obj1.setLibelleCourt("Identifié");
    obj1.setLibelleLong("Identifié (à traiter)");
    obj2.setCode("I");
    obj2.setLibelleCourt("Identifié");
    obj2.setLibelleLong("Identifié (à traiter)");
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleCourt()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleLong()));
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
   */
  @Test
  public void testGettingAnEntity() {
    StatutModification statutModification = entityManager.find(StatutModification.class, "I");
    assertNotNull("Hibernate didn't return a StatutModification", statutModification);
    assertEquals("Hibernate returned a StatutModification, but the Code doesn't match",
                 "I", statutModification.getCode());
    assertEquals("Hibernate returned a StatutModification, but the Label doesn't match",
                 "Identifié", statutModification.getLibelleCourt());
    assertEquals("Hibernate returned a StatutModification, but the Label doesn't match",
                 "Identifié (à traiter)", statutModification.getLibelleLong());
  }

  /**
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<StatutModification> objs = entityManager.createQuery("FROM StatutModification", StatutModification.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 3, objs.size());
    for (StatutModification obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
