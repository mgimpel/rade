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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.persist.dao.AuditJpaDao;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.service.impl.AuditServiceImpl;

/**
 * JUnit Test for DelegationService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestAuditService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private AuditJpaDao jpaDao;
  /** Service  to be tested. */
  private AuditService service;

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
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    service = new AuditServiceImpl();
    ((AuditServiceImpl)service).setAuditJpaDao(jpaDao);
  }

  /**
   * Test getting a the list of all Audit.
   */
  @Test @Ignore
  public void testGettingAuditList() {
    List<Audit> list = service.getAllAudit();
    assertNotNull("auditService returned a null list", list);
    assertEquals(1, list.size());
    for (Audit audit: list) {
      assertNotNull("Hibernate returned a List but an Entity is null",
                    audit);
    }
  }

  /**
   * Test getting an Audit.
   */
  @Test
  public void testGettingCommuneById() {
    Audit audit = service.getAuditbyId(1);
    assertNotNull(audit);
    assertEquals(1, audit.getId().intValue());
    assertEquals("gimpelma", audit.getAuteur());
    assertEquals(new GregorianCalendar(2018, 3, 1, 0, 0, 0).getTime(), audit.getDate());
    assertEquals("Import initial", audit.getNote());
  }

  @Test
  public void testCreatingAudit() {
    List<Audit> list = service.getAllAudit();
    assertNotNull("auditService returned a null list", list);
    assertEquals(1, list.size());
    Audit audit = new Audit();
    audit.setAuteur("Batch");
    audit.setDate(new Date());
    audit.setNote("Import Batch");
    assertEquals(null, audit.getId());
    Audit created = service.createAudit(audit);
    assertNotNull(created);
    assertEquals(2, audit.getId().intValue());
    assertEquals(audit, created);
    list = service.getAllAudit();
    assertNotNull("auditService returned a null list", list);
    assertEquals(2, list.size());
    assertEquals(created, service.getAuditbyId(2));
  }
}
