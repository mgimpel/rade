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
 * JUnit Test for Entity TypeNomClair.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on a database with a specific schema,
 * it is worth testing that the Entity behaves properly with the underlying
 * database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestTypeNomClair extends AbstractTestEntity {
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
        .addScript("db/sql/insert-TypeNomClair.sql")
        .build();
  }

  /**
   * Test equality of Entity.
   */
  @Test
  public void testEquality() {
    TypeNomClair obj1 = new TypeNomClair();
    TypeNomClair obj2 = new TypeNomClair();
    obj1.setCode("8");;
    obj1.setArticle("LOS");
    obj1.setCharniere("DE LOS");
    obj1.setArticleMaj(null);
    obj2.setCode("8");
    obj2.setArticle("LOS");
    obj2.setCharniere("DE LOS");
    obj2.setArticleMaj(null);
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getArticle()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCharniere()));
    assertTrue("String should contain value", obj1.toString().contains("null"));
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
    TypeNomClair typeNomClair = entityManager.find(TypeNomClair.class, "8");
    assertNotNull("Hibernate didn't return a TypeNomClair", typeNomClair);
    assertEquals("Hibernate returned a TypeNomClair, but the Code doesn't match",
                 "8", typeNomClair.getCode());
    assertEquals("Hibernate returned a TypeNomClair, but the Article doesn't match",
                 "LOS", typeNomClair.getArticle());
    assertEquals("Hibernate returned a TypeNomClair, but the Charniere doesn't match",
                 "DE LOS", typeNomClair.getCharniere());
    assertNull("Hibernate returned a TypeNomClair, but the Article Majuscule doesn't match",
               typeNomClair.getArticleMaj());
  }

  /**
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<TypeNomClair> objs = entityManager.createQuery("FROM TypeNomClair", TypeNomClair.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 9, objs.size());
    for (TypeNomClair obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
