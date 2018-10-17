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
 * JUnit Test for Entity CommuneSandre.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on an existing database with a
 * specific schema, it is worth testing that the Entity behaves properly
 * with the underlying database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCommuneSandre extends AbstractTestEntity {
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
    CirconscriptionBassin bassin = new CirconscriptionBassin();
    bassin.setCode("06");
    bassin.setLibelleCourt("RMED");
    bassin.setLibelleLong("RHONE-MEDITERRANEE");
    bassin.setAudit(audit);
    CommuneSandre obj1 = new CommuneSandre();
    CommuneSandre obj2 = new CommuneSandre();
    obj1.setCodeCommune("01001");
    obj1.setLibelleCommune("L ABERGEMENT CLEMENCIAT");
    obj1.setStatutCommune("Validé");
    obj1.setDateCreationCommune(sdf.parse("2002-01-01"));
    obj1.setDateMajCommune(sdf.parse("2016-01-01"));
    obj1.setCodeBassinDce("D");
    obj1.setCodeEuDistrict("EU35");
    obj1.setCirconscriptionBassin(bassin);
    obj1.setCodeComiteBassin("FR000006");
    obj1.setAudit(audit);
    obj2.setCodeCommune("01001");
    obj2.setLibelleCommune("L ABERGEMENT CLEMENCIAT");
    obj2.setStatutCommune("Validé");
    obj2.setDateCreationCommune(sdf.parse("2002-01-01"));
    obj2.setDateMajCommune(sdf.parse("2016-01-01"));
    obj2.setCodeBassinDce("D");
    obj2.setCodeEuDistrict("EU35");
    obj2.setCirconscriptionBassin(bassin);
    obj2.setCodeComiteBassin("FR000006");
    obj2.setAudit(audit);
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeCommune()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getLibelleCommune()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getStatutCommune()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getDateCreationCommune().toString()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getDateMajCommune().toString()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeBassinDce()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeEuDistrict()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCirconscriptionBassin().toString()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeComiteBassin()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAudit().toString()));
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
    CommuneSandre commune = entityManager.find(CommuneSandre.class, "01001");
    assertNotNull("Hibernate didn't return a CommuneSandre", commune);
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
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<CommuneSandre> objs = entityManager.createQuery("FROM CommuneSandre", CommuneSandre.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 100, objs.size());
    for (CommuneSandre obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
