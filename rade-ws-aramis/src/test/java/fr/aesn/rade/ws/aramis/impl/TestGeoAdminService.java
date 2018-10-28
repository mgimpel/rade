package fr.aesn.rade.ws.aramis.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.service.CommunePlusService;
import fr.aesn.rade.service.DelegationService;
import fr.aesn.rade.service.DepartementService;

/**
 * Unit Test for GeoAdminService.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestGeoAdminService {
  /** URL for the service during test. */
  private static final String ENDPOINT_ADDRESS = "http://localhost:9080/services/geoadminservice";

  /** WebService Endpoint for the test; */
  private static Server server;
  /** WebService client for the test. */
  private static GeoAdminServiceExterneImpl client;

  /** Delegation Service. */
  private static DelegationService delegationService = mock(DelegationService.class);
  /** Departement Service. */
  private static DepartementService departementService = mock(DepartementService.class);
  /** Commune Service. */
  private static CommunePlusService communePlusService = mock(CommunePlusService.class);

  /**
   * Set up the Test Environment.
   */
  @BeforeClass
  public static void setUpClass() {
    createServiceMocks();
    server = createServerEndpoint();
    client = createClientProxy();
  }

  /**
   * Close the Test Environment cleanly.
   */
  @AfterClass
  public static void tearDownClass() {
    server.stop();
    server.destroy();
  }

  /**
   * Build Service Mocks for use in the tests.
   */
  private static void createServiceMocks() {
    Delegation delegation = delegationBuilder("PPC", "DIRECTION DE PARIS PETITE COURONNE", "NANTERRE CEDEX", "AGENCE DE L'EAU SEINE NORMANDIE", "DIRECTION PARIS PETITE COURONNE", "51 RUE SALVADOR ALLENDE", "", "", "92027", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx");
    when(delegationService.getAllDelegation()).thenReturn(Arrays.asList(delegation));
    List<Departement> depts = Arrays.asList(departementBuilder("971", "Guadeloupe"),
                                            departementBuilder("972", "Martinique"),
                                            departementBuilder("973", "Guyane"),
                                            departementBuilder("974", "La Réunion"),
                                            departementBuilder("976", "Mayotte"));
    when(departementService.getAllDepartement()).thenReturn(depts);
    List<CommunePlus> communes = Arrays.asList(communeBuilder("Abergement-Clémenciat", "ABERGEMENT-CLEMENCIAT", "01001", "06"),
                                               communeBuilder("Abergement-de-Varey", "ABERGEMENT-DE-VAREY", "01002", "06"),
                                               communeBuilder("Ambérieu-en-Bugey", "AMBERIEU-EN-BUGEY", "01004", "06"),
                                               communeBuilder("Ambérieux-en-Dombes", "AMBERIEUX-EN-DOMBES", "01005", "06"));
    List<CommunePlus> nocommunes = Collections.<CommunePlus>emptyList();
    Date year2018 = new GregorianCalendar(2018, 1, 1, 0, 0, 0).getTime();
    when(communePlusService.getAllCommune(any())).thenAnswer(invocation -> {
      Date date = (Date) invocation.getArguments()[0];
      return date.after(year2018) ? communes : nocommunes;
    });
  }

  /**
   * Use a CXF JaxWsServerFactoryBean to create JAX-WS endpoints.
   * @return Server WebService Server.
   */
  private static Server createServerEndpoint() {
    GeoAdminServiceExternePortImpl implementor = new GeoAdminServiceExternePortImpl();
    implementor.setDelegationService(delegationService);
    implementor.setDepartementService(departementService);
    implementor.setCommunePlusService(communePlusService);
    // Create Endpoint
    JaxWsServerFactoryBean jaxWsServerFactoryBean = 
      new JaxWsServerFactoryBean();
    jaxWsServerFactoryBean.setServiceBean(implementor);
    jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
    return jaxWsServerFactoryBean.create();
  }

  /**
   * Create a CXF JaxWsProxyFactoryBean for creating JAX-WS proxies.
   * @return GeoAdminService WebService Client.
   */
  private static GeoAdminServiceExterneImpl createClientProxy() {
    JaxWsProxyFactoryBean jaxWsProxyFactoryBean =
      new JaxWsProxyFactoryBean();
    jaxWsProxyFactoryBean.setServiceClass(GeoAdminServiceExterneImpl.class);
    jaxWsProxyFactoryBean.setAddress(ENDPOINT_ADDRESS);
    return (GeoAdminServiceExterneImpl) jaxWsProxyFactoryBean.create();
  }

  /**
   * Builds a Delegation with the given parameters.
   * @param code Delegation code.
   * @param libelle Delegation label.
   * @param acheminement Delegation location.
   * @param adresse1 Delegation address line 1.
   * @param adresse2 Delegation address line 2.
   * @param adresse3 Delegation address line 3.
   * @param adresse4 Delegation address line 4.
   * @param adresse5 Delegation address line 5.
   * @param codePostal Delegation Post Code.
   * @param email Delegation e-mail address.
   * @param fax Delegation fax number.
   * @param siteWeb Delegation web site.
   * @param telephone Delegation phone number 1.
   * @param telephone2 Delegation phone number 2.
   * @param telephone3 Delegation phone number 3.
   * @return a Delegation with the given parameters.
   */
  private static Delegation delegationBuilder(final String code,
                                              final String libelle,
                                              final String acheminement,
                                              final String adresse1,
                                              final String adresse2,
                                              final String adresse3,
                                              final String adresse4,
                                              final String adresse5,
                                              final String codePostal,
                                              final String email,
                                              final String fax,
                                              final String siteWeb,
                                              final String telephone,
                                              final String telephone2,
                                              final String telephone3) {
    Delegation obj = new Delegation();
    obj.setCode(code);
    obj.setLibelle(libelle);
    obj.setAcheminement(acheminement);
    obj.setAdresse1(adresse1);
    obj.setAdresse2(adresse2);
    obj.setAdresse3(adresse3);
    obj.setAdresse4(adresse4);
    obj.setAdresse5(adresse5);
    obj.setCodePostal(codePostal);
    obj.setEmail(email);
    obj.setFax(fax);
    obj.setSiteWeb(siteWeb);
    obj.setTelephone(telephone);
    obj.setTelephone2(telephone2);
    obj.setTelephone3(telephone3);
    return obj;
  }

  /**
   * Builds a Departement with the given parameters.
   * @param numero Departement code.
   * @param nom Departement name.
   * @return Departement with the given parameters.
   */
  private static Departement departementBuilder(final String numero,
                                                final String nom) {
    Departement obj = new Departement();
    obj.setCodeInsee(numero);
    obj.setNomEnrichi(nom);
    return obj;
  }

  /**
   * Builds a Commune with the given parameters.
   * @param nomCommune Commune name.
   * @param nomCourt Commune short name.
   * @param numInsee Commune code.
   * @param bassin Commune bassin.
   * @return a Commune with the given parameters.
   */
  private static CommunePlus communeBuilder(final String nomCommune,
                                            final String nomCourt,
                                            final String numInsee,
                                            final String bassin) {
    CirconscriptionBassin circonscriptionBassin = new CirconscriptionBassin();
    circonscriptionBassin.setCode(bassin);
    CommunePlus obj = new CommunePlus();
    obj.setCode(numInsee);
    obj.setDateEffective(new Date());
    Commune insee = new Commune();
    insee.setNomEnrichi(nomCommune);
    insee.setNomMajuscule(nomCourt);
    insee.setCodeInsee(numInsee);
    CommuneSandre sandre = new CommuneSandre();
    sandre.setCodeCommune(numInsee);
    sandre.setCirconscriptionBassin(circonscriptionBassin);
    try {
      obj.setCommuneInsee(insee);
      obj.setCommuneSandre(sandre);
    } catch (InvalidArgumentException e) {
      // This should never happen because the INSEE and sandre Commune Objects
      // we built are valid for any and every date.
      throw new RuntimeException(e);
    }
    return obj;
  }

  /**
   * Test WebService FindAllDelegations.
   */
  @Test
  public void testFindAllDelegations() {
    List<DelegationVO> delegations = client.findAllDelegations();
    assertNotNull("CXF didn't return a List", delegations);
    assertEquals("CXF returned a List, but the wrong size", 1, delegations.size());
    DelegationVO delegation = delegations.get(0);
    assertNotNull(delegation);
    assertEquals("CXF returned a Delegation, but the ID doesn't match",
                 "PPC", delegation.getCode());
    assertEquals("CXF returned a Delegation, but the Libelle doesn't match",
                 "DIRECTION DE PARIS PETITE COURONNE", delegation.getLibelle());
    assertEquals("CXF returned a Delegation, but the Acheminement doesn't match",
                 "NANTERRE CEDEX", delegation.getAcheminement());
    assertEquals("CXF returned a Delegation, but the Addresse1 doesn't match",
                 "AGENCE DE L'EAU SEINE NORMANDIE", delegation.getAdresse1());
    assertEquals("CXF returned a Delegation, but the Addresse2 doesn't match",
                 "DIRECTION PARIS PETITE COURONNE", delegation.getAdresse2());
    assertEquals("CXF returned a Delegation, but the Addresse3 doesn't match",
                 "51 RUE SALVADOR ALLENDE", delegation.getAdresse3());
    assertEquals("CXF returned a Delegation, but the Addresse4 doesn't match",
                 "", delegation.getAdresse4());
    assertEquals("CXF returned a Delegation, but the Addresse5 doesn't match",
                 "", delegation.getAdresse5());
    assertEquals("CXF returned a Delegation, but the Code Postal doesn't match",
                 "92027", delegation.getCodePostal());
    assertEquals("CXF returned a Delegation, but the E-mail doesn't match",
                 "xxx", delegation.getEmail());
    assertEquals("CXF returned a Delegation, but the Fax doesn't match",
                 "xxx", delegation.getFax());
    assertEquals("CXF returned a Delegation, but the Site Web doesn't match",
                 "xxx", delegation.getSiteWeb());
    assertEquals("CXF returned a Delegation, but the Telephone doesn't match",
                 "xxx", delegation.getTelephone());
    assertEquals("CXF returned a Delegation, but the Telephone2 doesn't match",
                 "xxx", delegation.getTelephone2());
    assertEquals("CXF returned a Delegation, but the Telephone3 doesn't match",
                 "xxx", delegation.getTelephone3());
  }

  /**
   * Test WebService FindAllDepartements.
   */
  @Test
  public void testFindAllDepartements() {
    List<DepartementVO> depts = client.findAllDepartements();
    assertNotNull("CXF didn't return a List", depts);
    assertEquals("CXF returned a List, but the wrong size", 5, depts.size());
    DepartementVO dept = depts.get(0);
    assertNotNull("CXF didn't return a Departement", dept);
    assertEquals("CXF returned a Departement, but the code doesn't match",
                 "971", dept.getNumero());
    assertEquals("CXF returned a Departement, but a name doesn't match",
                 "Guadeloupe", dept.getNom());
  }

  /**
   * Test WebService FindAllCommunes with year 2017.
   */
  @Test
  public void testFindAllCommunes2017() {
    List<CommuneVO> communes = client.findAllCommunes(2017);
    assertNotNull("CXF didn't return a List", communes);
    assertEquals("CXF returned a List, but the wrong size", 0, communes.size());
  }

  /**
   * Test WebService FindAllCommunes with year 2018.
   */
  @Test
  public void testFindAllCommunes2018() {
    List<CommuneVO> communes = client.findAllCommunes(2018);
    assertNotNull("CXF didn't return a List", communes);
    assertEquals("CXF returned a List, but the wrong size", 4, communes.size());
    CommuneVO commune = communes.get(0);
    assertNotNull("CXF didn't return a Commune", commune);
    assertEquals("CXF returned a Commune, but the code doesn't match",
                 "01001", commune.getNumInsee());
    assertEquals("CXF returned a Commune, but field doesn't match",
                 "Abergement-Clémenciat", commune.getNomCommune());
    assertEquals("CXF returned a Commune, but field doesn't match",
                 "ABERGEMENT-CLEMENCIAT", commune.getNomCourt());
    assertEquals("CXF returned a Commune, but field doesn't match",
                 "06", commune.getBassin());
    assertEquals("CXF returned a Commune, but field doesn't match",
                 true, commune.isHorsBassin());
  }
}
