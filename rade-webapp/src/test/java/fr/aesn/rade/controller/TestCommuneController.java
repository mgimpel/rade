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
package fr.aesn.rade.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.hamcrest.collection.IsMapContaining;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;

import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.webapp.mvc.referentiel.CommuneController;

/**
 * Spring MVC Test for CommuneController .
 *
 * @author Adrien GUILLARD (adrien.guillard@sully-group.fr)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestCommuneController
  extends AbstractTestController {
  /** Service for Communes. */
  @Autowired
  private CommunePlusService communeService;
  /** Service for Departements. */
  @Autowired
  private DepartementService deptService;
  /** Service for Regions. */
  @Autowired
  private RegionService regionService;
  /** I18n message source */
  @Autowired
  private MessageSource messageSource;
  /** Web application Context. */
  @Autowired
  private WebApplicationContext context;

  @InjectMocks
  private CommuneController controller;
  @Mock
  private View mockView;
  private MockMvc mockMvc;

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
            .addScript("db/sql/insert-CommuneGenealogie-Test.sql")
            .build();
  }

  /**
   * Set up the Test Environment.
   */
  @Before
  public void setUp() {
    controller = new CommuneController();
    controller.setCommunePlusService(communeService);
    controller.setDepartementService(deptService);
    controller.setRegionService(regionService);
    controller.setMessageSource(messageSource);
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
                             .setSingleView(mockView)
                             .build();
  }

  @Test
  public void testServletContext() {
    ServletContext servletContext = context.getServletContext();
    Assert.assertNotNull(servletContext);
    Assert.assertTrue(servletContext instanceof MockServletContext);
  }

  /**
   * Test getting the Commune Search page (in French).
   */
  @Test
  public void testGetCommuneSearchPageFrench() throws Exception {
    mockMvc.perform(get("/referentiel/commune").locale(Locale.FRENCH))
           .andExpect(status().isOk())
           .andExpect(model().attribute("titre", "Rechercher une Commune"))
           .andExpect(view().name("referentiel/communesearch"));
  }

  /**
   * Test getting the Commune Search page (in English).
   */
  @Test
  public void testGetCommuneSearchPageEnglish() throws Exception {
    mockMvc.perform(get("/referentiel/commune").locale(Locale.ENGLISH))
           .andExpect(status().isOk())
           .andExpect(model().attribute("titre", "Commune Search"))
           .andExpect(view().name("referentiel/communesearch"));
  }

  /**
   * Test getting the Commune display page.
   * @throws Exception if there was an Exception processing request.
   */
  @Test
  public void testGetCommuneDisplayPage() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    mockMvc.perform(get("/referentiel/commune/97402?date=2018-01-21"))
           .andExpect(status().isOk())
           .andExpect(model().attribute("communeDisplay", hasProperty("codeInsee", is("97402"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("motifModification", is("Fusion-association : commune absorbante"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("nomEnrichi", is("Bras-Panon"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("commentaireModification", nullValue())))
           .andExpect(model().attribute("communeDisplay", hasProperty("article", is(""))))
           .andExpect(model().attribute("communeDisplay", hasProperty("articleEnrichi", is("Lzs"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("codeBassin", is("03"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("nomBassin", is("SEINE-NORMANDIE"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("codeDepartement", is("974"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("nomDepartement", is("La Réunion"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("nomMajuscule", is("BRAS-PANON"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("nomRegion", is("La Réunion"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("debutValidite", is(sdf.parse("1999-01-01")))))
           .andExpect(model().attribute("communeDisplay", hasProperty("finValidite", nullValue())))
           .andExpect(model().attribute("communeDisplay", hasProperty("parents", IsMapContaining.hasKey("95019"))))
           .andExpect(model().attribute("communeDisplay", hasProperty("enfants", IsMapContaining.hasKey("95259"))))
           .andExpect(view().name("referentiel/communedisplay"));
  }
  
  /**
   * Test getting Commune display page for a non-existant commune.
   * @throws Exception if there was an Exception processing request.
   */
  @Test
  public void testGetCommuneDisplayPageError() throws Exception {
    mockMvc.perform(get("/referentiel/commune/1000?date=2000-01-01").locale(Locale.FRENCH))
           .andExpect(status().isOk())
           .andExpect(model().attribute("errorRecherche", "La commune recherchée n'existe pas"))
           .andExpect(view().name("referentiel/communesearch"));
  }
}
