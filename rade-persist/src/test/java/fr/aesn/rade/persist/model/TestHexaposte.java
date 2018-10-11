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
 * JUnit Test for Entity Hexaposte.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on an existing database with a
 * specific schema, it is worth testing that the Entity behaves properly
 * with the underlying database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestHexaposte extends AbstractTestEntity {
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
        .addScript("db/sql/insert-Hexaposte-Test.sql")
        .build();
  }

  /**
   * Test equality of Entity.
   */
  @Test
  public void testEquality() {
    Hexaposte obj1 = new Hexaposte();
    Hexaposte obj2 = new Hexaposte();
    obj1.setIdentifiant("2204");
    obj1.setCodeInseeCommune("01001");
    obj1.setLibelleCommune("L ABERGEMENT CLEMENCIAT");
    obj1.setIndicateurPluridistribution(0);
    obj1.setTypeCodePostal("M");
    obj1.setLibelleLigne5(null);
    obj1.setCodePostal("01400");
    obj1.setLibelleAcheminement("L ABERGEMENT CLEMENCIAT");
    obj1.setCodeInseeAncienneCommune(null);
    obj1.setCodeMaJ(null);
    obj1.setCodeEtenduAdresse(null);
    obj2.setIdentifiant("2204");
    obj2.setCodeInseeCommune("01001");
    obj2.setLibelleCommune("L ABERGEMENT CLEMENCIAT");
    obj2.setIndicateurPluridistribution(0);
    obj2.setTypeCodePostal("M");
    obj2.setLibelleLigne5(null);
    obj2.setCodePostal("01400");
    obj2.setLibelleAcheminement("L ABERGEMENT CLEMENCIAT");
    obj2.setCodeInseeAncienneCommune(null);
    obj2.setCodeMaJ(null);
    obj2.setCodeEtenduAdresse(null);

    assertTrue("String should contain value", obj1.toString().contains(obj1.getIdentifiant()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeInseeCommune()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleCommune()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getIndicateurPluridistribution().toString()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTypeCodePostal()));
    assertTrue("String should contain value", obj1.toString().contains("null"));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodePostal()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleAcheminement()));
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
    Hexaposte hexaposte = entityManager.find(Hexaposte.class, "2204");
    assertNotNull("Hibernate didn't return a Delegation", hexaposte);
    assertEquals("Hibernate returned a Hexaposte, but the ID doesn't match",
                 "2204", hexaposte.getIdentifiant());
    assertEquals("Hibernate returned a Hexaposte, but the Code INSEE doesn't match",
                 "01001", hexaposte.getCodeInseeCommune());
    assertEquals("Hibernate returned a Hexaposte, but the Libelle Commune doesn't match",
                 "L ABERGEMENT CLEMENCIAT", hexaposte.getLibelleCommune());
    assertEquals("Hibernate returned a Hexaposte, but the Indicateur Pluridistribution doesn't match",
                 0, hexaposte.getIndicateurPluridistribution().intValue());
    assertEquals("Hibernate returned a Hexaposte, but the Type Code Postal doesn't match",
                 "M", hexaposte.getTypeCodePostal());
    assertNull("Hibernate returned a Hexaposte, but the Libelle Ligne 5 doesn't match",
               hexaposte.getLibelleLigne5());
    assertEquals("Hibernate returned a Hexaposte, but the Code Postal doesn't match",
                 "01400", hexaposte.getCodePostal());
    assertEquals("Hibernate returned a Hexaposte, but the Libelle Acheminement doesn't match",
                 "L ABERGEMENT CLEMENCIAT", hexaposte.getLibelleAcheminement());
    assertNull("Hibernate returned a Hexaposte, but the Code INSEE old Commune doesn't match",
               hexaposte.getCodeInseeAncienneCommune());
    assertNull("Hibernate returned a Hexaposte, but the Code MaJ doesn't match",
               hexaposte.getCodeMaJ());
    assertNull("Hibernate returned a Hexaposte, but the Code Etendu Adresse doesn't match",
               hexaposte.getCodeEtenduAdresse());
  }

  /**
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<Hexaposte> objs = entityManager.createQuery("FROM Hexaposte", Hexaposte.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 10, objs.size());
    for (Hexaposte obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
