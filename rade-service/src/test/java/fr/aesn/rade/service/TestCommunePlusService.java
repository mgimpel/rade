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

import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.CommuneSandreJpaDao;
import fr.aesn.rade.service.impl.CommunePlusServiceImpl;

/**
 * JUnit Test for CommunePlusService.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestCommunePlusService
  extends AbstractTestService {
  /** DAO for the Service to be tested. */
  @Autowired
  private CommuneJpaDao communeJpaDao;
  /** Data Access Object for CommuneSandre. */
  @Autowired
  private CommuneSandreJpaDao communeSandreJpaDao;
  /** Service  to be tested. */
  private CommunePlusService service;

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
        .addScript("db/sql/insert-CommuneSandre-Test.sql")
        .addScript("db/sql/insert-Commune-Test.sql")
        .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    service = new CommunePlusServiceImpl();
    ((CommunePlusServiceImpl)service).setCommuneJpaDao(communeJpaDao);
    ((CommunePlusServiceImpl)service).setCommuneSandreJpaDao(communeSandreJpaDao);
  }

   /**
   * Test de la recherche par critères multiples
   * @throws java.text.ParseException
   */
  @Test
  public void getCommuneByCriteria() throws ParseException{
      List<CommunePlusWithGenealogie> communes;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      communes = service.getCommuneByCriteria("91001",null,null,null,null,null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria("91001",null,null,null,null,sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria("91001",null,null,null,null,sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria("91001","X","X","X","X",null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,"91",null,null,null,null);
      assertEquals("Hibernate returned the wrong number of results",196, communes.size());
      communes = service.getCommuneByCriteria(null,"91",null,null,"éVi",null);
      assertEquals("Hibernate returned the wrong number of results",2, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,null,null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,null, sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,null, sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,"éVi",null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,"éVi", sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,"éVi", sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,"éVi", sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03",null,"éVi", sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,null,null,"11",null,null);
      assertEquals("Hibernate returned the wrong number of results",503, communes.size());
      communes = service.getCommuneByCriteria(null,null,"03","11",null,null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
       communes = service.getCommuneByCriteria(null,null,"03","11","éVi",null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());;
      communes = service.getCommuneByCriteria(null,null,"03","11","éVi",sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,null,"03","11","éVi",sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,null,"03","11",null,sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria(null,null,"03","11",null,sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,null,"03",null,"éVi",null);
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
       communes = service.getCommuneByCriteria(null,null,"03",null,"éVi",sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
       communes = service.getCommuneByCriteria(null,null,"03",null,"éVi",sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,null,null,null,"éVi",null);
      assertEquals("Hibernate returned the wrong number of results",3, communes.size());
      communes = service.getCommuneByCriteria(null,null,null,null,"éVi",sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",3, communes.size());
      communes = service.getCommuneByCriteria(null,null,null,null,"éVi",sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,null,null,null,null,sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",632, communes.size());
      communes = service.getCommuneByCriteria(null,null,null,null,null,sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria("91001","91","03","11","éVi",sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",1, communes.size());
      communes = service.getCommuneByCriteria("91001","91","03","11","éVi",sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03","11","aaa",sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,"91","03","11","aaa",sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
      communes = service.getCommuneByCriteria(null,"91",null,"11","éVi",sdf.parse("2018-01-01"));
      assertEquals("Hibernate returned the wrong number of results",2, communes.size());
      communes = service.getCommuneByCriteria(null,"91",null,"11","éVi",sdf.parse("2000-01-01"));
      assertEquals("Hibernate returned the wrong number of results",0, communes.size());
  }
}
