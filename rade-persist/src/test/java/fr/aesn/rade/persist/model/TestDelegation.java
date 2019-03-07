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
 * JUnit Test for Entity Delegation.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on an existing database with a
 * specific schema, it is worth testing that the Entity behaves properly
 * with the underlying database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestDelegation extends AbstractTestEntity {
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
        .addScript("db/sql/insert-Delegation.sql")
        .build();
  }

  /**
   * Test equality of Entity.
   */
  @Test
  public void testEquality() {
    Delegation obj1 = new Delegation();
    Delegation obj2 = new Delegation();
    obj1.setCode("0PPC_");
    obj1.setLibelle("DPPC");
    obj1.setAcheminement("xxx0");
    obj1.setAdresse1("xxx1");
    obj1.setAdresse2("xxx2");
    obj1.setAdresse3("xxx3");
    obj1.setAdresse4("xxx4");
    obj1.setAdresse5("xxx5");
    obj1.setCodePostal("xxx6");
    obj1.setEmail("xxx7");
    obj1.setFax("xxx8");
    obj1.setSiteWeb("xxx0");
    obj1.setTelephone("0X XX XX XX X1");
    obj1.setTelephone2("0X XX XX XX X2");
    obj1.setTelephone3("0X XX XX XX X3");
    obj2.setCode("0PPC_");
    obj2.setLibelle("DPPC");
    obj2.setAcheminement("xxx0");
    obj2.setAdresse1("xxx1");
    obj2.setAdresse2("xxx2");
    obj2.setAdresse3("xxx3");
    obj2.setAdresse4("xxx4");
    obj2.setAdresse5("xxx5");
    obj2.setCodePostal("xxx6");
    obj2.setEmail("xxx7");
    obj2.setFax("xxx8");
    obj2.setSiteWeb("xxx0");
    obj2.setTelephone("0X XX XX XX X1");
    obj2.setTelephone2("0X XX XX XX X2");
    obj2.setTelephone3("0X XX XX XX X3");
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelle()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAdresse1()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAdresse2()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAdresse3()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAdresse4()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAdresse5()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodePostal()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getEmail()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getFax()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getSiteWeb()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTelephone()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTelephone2()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTelephone3()));
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
    Delegation delegation = entityManager.find(Delegation.class, "SIEGE");
    assertNotNull("Hibernate didn't return a Delegation", delegation);
    assertEquals("Hibernate returned a Delegation, but the ID doesn't match",
                 "SIEGE", delegation.getCode());
    assertEquals("Hibernate returned a Delegation, but the Libelle doesn't match",
                 "SIEGE DFIR", delegation.getLibelle());
    assertEquals("Hibernate returned a Delegation, but the Acheminement doesn't match",
                 "Nanterre Cedex", delegation.getAcheminement());
    assertEquals("Hibernate returned a Delegation, but the Addresse1 doesn't match",
                 "Agence de l'eau Seine-Normandie", delegation.getAdresse1());
    assertEquals("Hibernate returned a Delegation, but the Addresse2 doesn't match",
                 "Siège DFIR", delegation.getAdresse2());
    assertEquals("Hibernate returned a Delegation, but the Addresse3 doesn't match",
                 "51, rue Salvador Allende", delegation.getAdresse3());
    assertEquals("Hibernate returned a Delegation, but the Addresse4 doesn't match",
                 "", delegation.getAdresse4());
    assertEquals("Hibernate returned a Delegation, but the Addresse5 doesn't match",
                 "Nanterre", delegation.getAdresse5());
    assertEquals("Hibernate returned a Delegation, but the Code Postal doesn't match",
                 "92027", delegation.getCodePostal());
    assertEquals("Hibernate returned a Delegation, but the E-mail doesn't match",
                 "xxx", delegation.getEmail());
    assertEquals("Hibernate returned a Delegation, but the Fax doesn't match",
                 "01 41 20 16 09", delegation.getFax());
    assertEquals("Hibernate returned a Delegation, but the Site Web doesn't match",
                 "http://www.eau-seine-normandie.fr/", delegation.getSiteWeb());
    assertEquals("Hibernate returned a Delegation, but the Telephone doesn't match",
                 "01 41 20 16 00", delegation.getTelephone());
    assertEquals("Hibernate returned a Delegation, but the Telephone2 doesn't match",
                 "", delegation.getTelephone2());
    assertEquals("Hibernate returned a Delegation, but the Telephone3 doesn't match",
                 "", delegation.getTelephone3());
  }

  /**
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<Delegation> objs = entityManager.createQuery("FROM Delegation", Delegation.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 7, objs.size());
    for (Delegation obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
