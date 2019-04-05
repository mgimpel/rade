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

import fr.aesn.rade.service.BassinService;
import fr.aesn.rade.service.DepartementService;

import java.nio.charset.Charset;
import java.util.Locale;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.webapp.mvc.referentiel.JsonController;

import javax.servlet.ServletContext;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import static org.hamcrest.Matchers.*;

/**
 * Spring MVC Test for JsonController .
 *
 * @author Adrien GUILLARD (adrien.guillard@sully-group.fr)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestJsonController extends AbstractTestController {

  /**
   * Service for Departements.
   */
  @Autowired
  private DepartementService deptService;
  /**
   * Service for Regions.
   */
  @Autowired
  private RegionService regionService;
  /**
   * Bassin Service.
   */
  @Autowired
  private BassinService bassinService;
  /**
   * Web application Context.
   */
  @Autowired
  private WebApplicationContext context;

  @InjectMocks
  private JsonController controller;
  @Mock
  private View mockView;
  private MockMvc mockMvc;
  Locale locale = Locale.ENGLISH;
  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

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
    controller = new JsonController();
    controller.setDepartementService(deptService);
    controller.setRegionService(regionService);
    controller.setBassinService(bassinService);
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
   * Test getting the depertement Json .
   *
   * @throws Exception if there was an Exception processing request.
   */
  @Test
  public void testGettingJsonDepertementsList() throws Exception {
    this.mockMvc.perform(get("/referentiel/json/deptlist").locale(locale))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.10").value("Aube"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.75").value("Paris"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.69").value("Rhône"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.2B").value("Haute-Corse"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(101)));
  }

  /**
   * Test getting the json region list .
   *
   * @throws Exception if there was an Exception processing request.
   */
  @Test
  public void testGettingJsonRegionsList() throws Exception {
    this.mockMvc.perform(get("/referentiel/json/regionlist").locale(locale))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.11").value("Île-de-France"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.93").value("Provence-Alpes-Côte d'Azur"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.94").value("Corse"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.06").value("Mayotte"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(18)));
  }

  /**
   * Test getting the Json depertement List .
   *
   * @throws Exception if there was an Exception processing request.
   */
  @Test
  public void testGettingJsonBassinsList() throws Exception {
    this.mockMvc.perform(get("/referentiel/json/bassinlist").locale(locale))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.10").value("REUNION"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.06").value("RHONE-MEDITERRANEE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.08").value("MARTINIQUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.09").value("GUYANE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(12)));
  }
}
