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
 * JUnit Test for Entity Commune.
 * 
 * Normally one would not build a Unit Test for an Entity Object,
 * but seeing as this Entity is Persisted on a database with a specific schema,
 * it is worth testing that the Entity behaves properly with the underlying
 * database schema.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCommune extends AbstractTestEntity {
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
   * Test equality of Entity.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testEquality() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    TypeEntiteAdmin type = new TypeEntiteAdmin();
    type.setCode("COM");
    type.setLibelleCourt("Commune");
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
    CirconscriptionBassin bassin = new CirconscriptionBassin();
    bassin.setCode("07");
    bassin.setLibelleCourt("GUADELOUPE");
    bassin.setLibelleLong("GUADELOUPE");
    bassin.setAudit(audit);
    Commune obj1 = new Commune();
    Commune obj2 = new Commune();
    obj1.setId(135233);
    obj1.setDebutValidite(sdf.parse("2018-04-01"));
    obj1.setFinValidite(null);
    obj1.setArticleEnrichi(null);
    obj1.setNomMajuscule("BASSE-TERRE");
    obj1.setNomEnrichi("Basse-Terre");
    obj1.setCommentaire("");
    obj1.setTypeNomClair(tncc);
    obj1.setTypeEntiteAdmin(type);
    obj1.setAudit(audit);
    obj1.setCodeInsee("97105");
    obj1.setIndicateurUrbain("O");
    obj1.setCirconscriptionBassin(bassin);
    obj1.setDepartement("971");
    obj2.setId(135233);
    obj2.setDebutValidite(sdf.parse("2018-04-01"));
    obj2.setFinValidite(null);
    obj2.setArticleEnrichi(null);
    obj2.setNomMajuscule("BASSE-TERRE");
    obj2.setNomEnrichi("Basse-Terre");
    obj2.setCommentaire("");
    obj2.setTypeNomClair(tncc);
    obj2.setTypeEntiteAdmin(type);
    obj2.setAudit(audit);
    obj2.setCodeInsee("97105");
    obj2.setIndicateurUrbain("O");
    obj2.setCirconscriptionBassin(bassin);
    obj2.setDepartement("971");
    assertTrue("String should contain value", obj1.toString().contains("135233"));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getDebutValidite().toString()));
    assertTrue("String should contain value", obj1.toString().contains("null"));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getNomMajuscule()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getNomEnrichi()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCommentaire()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTypeNomClair().getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getTypeEntiteAdmin().getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getAudit().getAuteur()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCodeInsee()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getIndicateurUrbain()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getCirconscriptionBassin().getCode()));
    assertTrue("String should contain value", obj1.toString().contains(obj1.getDepartement()));
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
    Commune com = entityManager.find(Commune.class, 135233);
    assertNotNull("Hibernate didn't return a Commune", com);
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
   * Test getting all Entity.
   */
  @Test
  public void testGettingAllEntity() {
    List<Commune> objs = entityManager.createQuery("FROM Commune", Commune.class).getResultList();
    assertNotNull("Hibernate didn't return a List", objs);
    assertEquals("Hibernate returned a List, but the wrong size",
                 632, objs.size());
    for (Commune obj: objs) {
      assertNotNull("Hibernate returned a List but an Entity is null", obj);
    }
  }
}
