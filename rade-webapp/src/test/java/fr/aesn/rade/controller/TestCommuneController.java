package fr.aesn.rade.controller;

import java.text.SimpleDateFormat;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.CommuneSandreJpaDao;
import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import fr.aesn.rade.service.impl.DepartementServiceImpl;
import fr.aesn.rade.service.impl.RegionServiceImpl;
import fr.aesn.rade.persist.dao.RegionJpaDao;
import fr.aesn.rade.persist.dao.DepartementJpaDao;
import fr.aesn.rade.service.impl.CommunePlusServiceImpl;
import fr.aesn.rade.webapp.mvc.referentiel.CommuneController;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletContext;
import org.hamcrest.Matchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.hamcrest.collection.IsMapContaining;

/**
 * Spring MVC Test for CommuneController .
 *
 * @author Adrien GUILLARD (adrien.guillard@sully-group.fr)
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestCommuneController extends AbstractTestController {

  /**
   * DAO for the Controller to be tested.
   */
  @Autowired
  private CommuneJpaDao communeJpaDao;
  /**
   * Data Access Object for CommuneSandre.
   */
  @Autowired
  private CommuneSandreJpaDao communeSandreJpaDao;
  /**
   * Data Access Object for Region .
   */
  @Autowired
  private RegionJpaDao regionJpaDao;
  /**
   * Departement Service.
   */
  @Autowired
  private DepartementJpaDao departementJpaDao;
  /**
   * Controller to be tested.
   */
  @InjectMocks
  private CommuneController controller;
  /**
   * The service to be used for test.
   */
  private CommunePlusService service;
  private RegionService serviceReg;
  private DepartementService serviceDep;
  @Mock
  View mockView;
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext wac;

  /**
   * Set up the Test Environment.
   */
  @BeforeClass
  public static void setUpClass() {
    // create temporary database for Hibernate
    db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).setScriptEncoding("UTF-8")
            .setName("testdb")
            .setName("testdb").addScript("db/sql/create-tables.sql")
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

  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages", "i18n/messages");
    messageSource.setDefaultEncoding(Charset.forName("UTF-8").name());
    messageSource.setFallbackToSystemLocale(true);
    messageSource.setCacheSeconds(-1);
    messageSource.setAlwaysUseMessageFormat(false);
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }

  /**
   * Set up the Test Environment.
   *
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    service = new CommunePlusServiceImpl();
    ((CommunePlusServiceImpl) service).setCommuneJpaDao(communeJpaDao);
    ((CommunePlusServiceImpl) service).setCommuneSandreJpaDao(communeSandreJpaDao);
    serviceReg = new RegionServiceImpl();
    serviceDep = new DepartementServiceImpl();
    ((RegionServiceImpl) serviceReg).setRegionJpaDao(regionJpaDao);
    ((DepartementServiceImpl) serviceDep).setDepartementJpaDao(departementJpaDao);
    controller = new CommuneController();
    controller.setCommunePlusService(service);
    controller.setDepartementService(serviceDep);
    controller.setRegionService(serviceReg);
    controller.setMessageSource(messageSource());
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(mockView).build();
  }

  @Test
  public void testServletContext() {
    ServletContext servletContext = wac.getServletContext();
    Assert.assertNotNull(servletContext);
    Assert.assertTrue(servletContext instanceof MockServletContext);
  }

  /**
   * Test getting the home page .
   */
  @Test
  public void testHomePage() {
    try {
      this.mockMvc.perform(get("/referentiel/commune"))
              .andExpect(status().isOk());
    } catch (Exception ex) {
      log.error("This Should never happen");
    }
  }

  /**
   * Test getting the Commune display page .
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testGetDisplayPage() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    mockMvc.perform(get("/referentiel/commune/97402?date=2019-03-26"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("codeInsee", is("97402"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("motifModification", is("Fusion-association : commune absorbante"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("nomEnrichi", is("Bras-Panon"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("commentaireModification", nullValue())))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("article", is(""))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("articleEnrichi", is("Lzs"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("codeBassin", is("03"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("nomBassin", is("SEINE-NORMANDIE"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("codeDepartement", is("974"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("nomDepartement", is("La Réunion"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("nomMajuscule", is("BRAS-PANON"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("nomRegion", is("La Réunion"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("debutValidite", is(sdf.parse("1999-01-01")))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("finValidite", nullValue())))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("parents", IsMapContaining.hasKey("95019"))))
            .andExpect(model().attribute("communeDisplay", Matchers.hasProperty("enfants", IsMapContaining.hasKey("95259"))))
            .andExpect(view().name("referentiel/communedisplay"));
  }
  
  /**
   * Test getting Commune display page.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testGetDisplayPageError() throws Exception {
    mockMvc.perform(get("/referentiel/commune/1000?date=2019-03-05"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("errorRecherche", ("La commune recherchée n'existe pas")))
            .andExpect(view().name("referentiel/communesearch"));
  }
}
