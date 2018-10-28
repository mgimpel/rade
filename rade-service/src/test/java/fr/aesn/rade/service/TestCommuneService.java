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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.GenealogieEntiteAdminJpaDao;
import fr.aesn.rade.persist.dao.StatutModificationJpaDao;
import fr.aesn.rade.persist.dao.TypeEntiteAdminJpaDao;
import fr.aesn.rade.persist.dao.TypeGenealogieEntiteAdminJpaDao;
import fr.aesn.rade.persist.dao.TypeNomClairJpaDao;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.service.impl.CommuneServiceImpl;
import fr.aesn.rade.service.impl.MetadataServiceImpl;

/**
 * JUnit Test for DelegationService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCommuneService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private CommuneJpaDao communeJpaDao;
  /** Data Access Object for TypeEntiteAdmin. */
  @Autowired
  private GenealogieEntiteAdminJpaDao genealogieEntiteAdminJpaDao;
  /** Data Access Object for TypeEntiteAdmin. */
  @Autowired
  private TypeEntiteAdminJpaDao typeEntiteAdminJpaDao;
  /** Data Access Object for TypeGenealogieEntiteAdmin. */
  @Autowired
  private TypeGenealogieEntiteAdminJpaDao typeGenealogieEntiteAdminJpaDao;
  /** Data Access Object for TypeNomClair. */
  @Autowired
  private TypeNomClairJpaDao typeNomClairJpaDao;
  /** Data Access Object for StatutModification. */
  @Autowired
  private StatutModificationJpaDao statutModificationJpaDao;
  /** Service  to be tested. */
  private CommuneService communeService;

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
        .addScript("db/sql/insert-TypeEntiteAdmin.sql")
        .addScript("db/sql/insert-TypeGenealogieEntiteAdmin.sql")
        .addScript("db/sql/insert-TypeNomClair.sql")
        .addScript("db/sql/insert-Audit.sql")
        .addScript("db/sql/insert-CirconscriptionBassin.sql")
        .addScript("db/sql/insert-Region.sql")
        .addScript("db/sql/insert-Departement.sql")
        .addScript("db/sql/insert-Commune-Test.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    MetadataService metadataService = new MetadataServiceImpl();
    ((MetadataServiceImpl)metadataService).setTypeEntiteAdminJpaDao(typeEntiteAdminJpaDao);
    ((MetadataServiceImpl)metadataService).setTypeGenealogieEntiteAdminJpaDao(typeGenealogieEntiteAdminJpaDao);
    ((MetadataServiceImpl)metadataService).setTypeNomClairJpaDao(typeNomClairJpaDao);
    ((MetadataServiceImpl)metadataService).setStatutModificationJpaDao(statutModificationJpaDao);
    communeService = new CommuneServiceImpl(communeJpaDao);
    ((CommuneServiceImpl)communeService).setGenealogieEntiteAdminJpaDao(genealogieEntiteAdminJpaDao);;
    ((CommuneServiceImpl)communeService).setMetadataService(metadataService);
  }

  /**
   * Test getting a the list of all Communes.
   */
  @Test
  public void testGettingCommuneList() {
    List<Commune> list = communeService.getAllCommune();
    assertNotNull("CommuneService returned a null list", list);
    assertEquals(632, list.size());
    for (Commune commune : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    commune);
    }
  }

  /**
   * Test getting a the list of all Communes.
   */
  @Test
  public void testGettingCommuneList2018() {
    Date year2018 = new GregorianCalendar(2018, 1, 1, 0, 0, 0).getTime();
    List<Commune> list = communeService.getAllCommune(year2018);
    assertNotNull("CommuneService returned a null list", list);
    assertEquals(632, list.size());
    for (Commune commune : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    commune);
    }
  }

  /**
   * Test getting a the list of all Communes.
   */
  @Test
  public void testGettingCommuneList2017() {
    Date year2017 = new GregorianCalendar(2017, 1, 1, 0, 0, 0).getTime();
    List<Commune> list = communeService.getAllCommune(year2017);
    assertNotNull("CommuneService returned a null list", list);
    assertEquals(0, list.size());
  }

  /**
   * Test getting a Communes.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingCommuneById() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Commune com = communeService.getCommuneById(135233);
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
                 "971", com.getDepartement());
    assertNull(communeService.getCommuneById(0));
    assertNull(communeService.getCommuneById(-1));
    assertNull(communeService.getCommuneById(2000000));
  }

  /**
   * Test getting a the list of all Communes.
   * @throws ParseException failed to parse date.
   */
  @Test
  public void testGettingCommuneByCode() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Commune> list = communeService.getCommuneByCode("97105");
    assertNotNull("CommuneService returned a null list", list);
    assertEquals(1, list.size());
    Commune com = list.get(0);
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
                 "971", com.getDepartement());
  }

  /**
   * Test getting a the list of all Communes.
   */
  @Test
  public void testGettingCommuneForDate() {
    List<Commune> list;
    Calendar cal = Calendar.getInstance();
    cal.set(2018, 1, 2);
    list = communeService.getAllCommune(cal.getTime());
    assertNotNull("CommuneService returned a null list", list);
    assertEquals(632, list.size());
    for (Commune commune : list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    commune);
    }
    cal.set(2017, 1, 2);
    list = communeService.getAllCommune(cal.getTime());
    assertNotNull("CommuneService returned a null list", list);
    assertEquals(0, list.size());
  }

  /**
   * Tests MOD=100 : Changement de Nom.
   * @throws ParseException failed to parse date.
   * @throws InvalidArgumentException passed wrong arguments during
   * CommuneService request.
   */
  @Test
  public void testMod100() throws ParseException, InvalidArgumentException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Set<GenealogieEntiteAdmin> genealogie;
    // Check the commune has no children
    Commune commune = communeService.getCommuneByCode("97105", "2018-03-01");
    genealogie = commune.getEnfants();
    assertEquals(0, genealogie.size());
    // Modify the Commune and test it's new values
    Commune newCommune = communeService.mod100ChangementdeNom("97105", sdf.parse("2018-06-01"), "0", "Basse-Terre 2", commune.getAudit(), null);
    assertNotNull("Hibernate didn't return a Commune", newCommune);
    assertNotEquals("Hibernate returned a Commune, but the Id doesn't match",
                    135233, newCommune.getId().intValue());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 sdf.parse("2018-06-01"), newCommune.getDebutValidite());
    assertNull("Hibernate returned a Commune, but a field doesn't match",
               newCommune.getFinValidite());
    assertNull("Hibernate returned a Commune, but a field doesn't match",
               newCommune.getArticleEnrichi());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "BASSE-TERRE 2", newCommune.getNomMajuscule());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "Basse-Terre 2", newCommune.getNomEnrichi());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "", newCommune.getCommentaire());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "0", newCommune.getTypeNomClair().getCode());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "COM", newCommune.getTypeEntiteAdmin().getCode());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 1, newCommune.getAudit().getId().intValue());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "97105", newCommune.getCodeInsee());
    assertEquals("Hibernate returned a Commune, but a field doesn't match",
                 "971", newCommune.getDepartement());
    // Check the new genealogie
    genealogie = newCommune.getEnfants();
    assertEquals(0, genealogie.size());
    genealogie = newCommune.getParents();
    assertEquals(1, genealogie.size());
    assertEquals(135233, genealogie.iterator().next().getParentEnfant().getParent().getId().intValue());
    Commune parent = communeService.getCommuneById(135233);
    // Check the original commune now has a child
    genealogie = parent.getEnfants();
    assertEquals(1, genealogie.size());
    assertEquals(newCommune.getId(), genealogie.iterator().next().getParentEnfant().getEnfant().getId());
  }

  /**
   * Tests MOD=200 : Creation.
   * @throws ParseException failed to parse date.
   * @throws InvalidArgumentException passed wrong arguments during
   * CommuneService request.
   */
  @Test
  public void testMod200() throws ParseException, InvalidArgumentException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Audit audit = communeService.getCommuneById(135233).getAudit(); // re-use existing Audit
    Commune newCommune = communeService.mod200Creation("01999", sdf.parse("2019-01-01"), "01", "0", "Nouvelle Commune", audit, null);
    Commune commune;
    commune = communeService.getCommuneByCode("01999", "2018-01-01");
    assertNull(commune);
    commune = communeService.getCommuneByCode("01999", "2019-01-02");
    assertNotNull(commune);
    assertEquals(newCommune, commune);
  }
}
