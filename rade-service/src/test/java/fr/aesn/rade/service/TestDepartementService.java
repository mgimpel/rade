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
package fr.aesn.rade.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.dao.DepartementJpaDao;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.service.impl.DepartementServiceImpl;

/**
 * JUnit Test for DelegationService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestDepartementService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private DepartementJpaDao jpaDao;
  /** Service  to be tested. */
  private DepartementService departementService;

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
        .addScript("db/sql/insert-Departement.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    departementService = new DepartementServiceImpl(jpaDao);
  }

  /**
   * Test getting a the list of all Departements.
   */
  @Test
  public void testGettingDepartementList() {
    List<Departement> list = departementService.getAllDepartement();
    assertNotNull("DepartementService returned a null list", list);
    assertEquals(101, list.size());
    for (Departement dept : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    dept);
    }
  }

  /**
   * Test getting a the list of all Departements.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingDepartementById() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Departement dept = departementService.getDepartementById(197);
    assertNotNull("Hibernate didn't return a Departement", dept);
    assertEquals("Hibernate returned a Departement, but the Id doesn't match",
                 197, dept.getId().intValue());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 sdf.parse("2018-01-01"), dept.getDebutValidite());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 null, dept.getFinValidite());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 null, dept.getArticleEnrichi());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "GUADELOUPE", dept.getNomMajuscule());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "Guadeloupe", dept.getNomEnrichi());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "", dept.getCommentaire());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "3", dept.getTypeNomClair().getCode());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "DEP", dept.getTypeEntiteAdmin().getCode());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 1, dept.getAudit().getId().intValue());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "971", dept.getCodeInsee());
    assertEquals("vdept returned a Departement, but a field doesn't match",
                 "97105", dept.getChefLieu());
    assertEquals("vdept returned a Departement, but a field doesn't match",
                 "01", dept.getRegion());
    assertNull(departementService.getDepartementById(0));
    assertNull(departementService.getDepartementById(-1));
    assertNull(departementService.getDepartementById(1000));

  }

  /**
   * Test getting a the list of all Departements.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingDepartementByCode() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Departement> list = departementService.getDepartementByCode("971");
    assertNotNull("DepartementService returned a null list", list);
    assertEquals(1, list.size());
    Departement dept = list.get(0);
    assertNotNull("Hibernate didn't return a Departement", dept);
    assertEquals("Hibernate returned a Departement, but the Id doesn't match",
                 197, dept.getId().intValue());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 sdf.parse("2018-01-01"), dept.getDebutValidite());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 null, dept.getFinValidite());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 null, dept.getArticleEnrichi());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "GUADELOUPE", dept.getNomMajuscule());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "Guadeloupe", dept.getNomEnrichi());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "", dept.getCommentaire());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "3", dept.getTypeNomClair().getCode());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "DEP", dept.getTypeEntiteAdmin().getCode());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 1, dept.getAudit().getId().intValue());
    assertEquals("Hibernate returned a Departement, but a field doesn't match",
                 "971", dept.getCodeInsee());
    assertEquals("vdept returned a Departement, but a field doesn't match",
                 "97105", dept.getChefLieu());
    assertEquals("vdept returned a Departement, but a field doesn't match",
                 "01", dept.getRegion());
  }
}
