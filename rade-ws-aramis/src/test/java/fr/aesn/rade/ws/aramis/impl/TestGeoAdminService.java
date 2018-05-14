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

import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.service.CommuneService;
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
  private static CommuneService communeService = mock(CommuneService.class);

  /**
   * Set up the Test Environment.
   */
  @BeforeClass
  public static void setUpClass() {
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
   * Use a CXF JaxWsServerFactoryBean to create JAX-WS endpoints.
   */
  private static Server createServerEndpoint() {
    // Create Service Bean
    GeoAdminServiceExternePortImpl implementor = new GeoAdminServiceExternePortImpl();
    Delegation delegation = delegationBuilder("PPC", "DIRECTION DE PARIS PETITE COURONNE", "NANTERRE CEDEX", "AGENCE DE L'EAU SEINE NORMANDIE", "DIRECTION PARIS PETITE COURONNE", "51 RUE SALVADOR ALLENDE", "", "", "92027", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx");
    when(delegationService.getAllDelegation()).thenReturn(Arrays.asList(delegation));
    List<Departement> depts = Arrays.asList(departementBuilder("971", "Guadeloupe"),
                                            departementBuilder("972", "Martinique"),
                                            departementBuilder("973", "Guyane"),
                                            departementBuilder("974", "La Réunion"),
                                            departementBuilder("976", "Mayotte"));
    when(departementService.getAllDepartement()).thenReturn(depts);
    List<Commune> communes = Arrays.asList(communeBuilder("Abergement-Clémenciat", "ABERGEMENT-CLEMENCIAT", "01001", "06"),
                                           communeBuilder("Abergement-de-Varey", "ABERGEMENT-DE-VAREY", "01002", "06"),
                                           communeBuilder("Ambérieu-en-Bugey", "AMBERIEU-EN-BUGEY", "01004", "06"),
                                           communeBuilder("Ambérieux-en-Dombes", "AMBERIEUX-EN-DOMBES", "01005", "06"));
    List<Commune> nocommunes = Collections.<Commune>emptyList();
    Date year2018 = new GregorianCalendar(2018, 1, 1, 0, 0, 0).getTime();
    when(communeService.getAllCommune(any())).thenAnswer(invocation -> {
      Date date = (Date) invocation.getArguments()[0];
      return date.after(year2018) ? communes : nocommunes;
    });
    implementor.setDelegationService(delegationService);
    implementor.setDepartementService(departementService);
    implementor.setCommuneService(communeService);
    // Create Endpoint
    JaxWsServerFactoryBean jaxWsServerFactoryBean = 
      new JaxWsServerFactoryBean();
    jaxWsServerFactoryBean.setServiceBean(implementor);
    jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
    return jaxWsServerFactoryBean.create();
  }

  /**
   * Create a CXF JaxWsProxyFactoryBean for creating JAX-WS proxies.
   * @return GeoAdminService Client
   */
  private static GeoAdminServiceExterneImpl createClientProxy() {
    JaxWsProxyFactoryBean jaxWsProxyFactoryBean =
      new JaxWsProxyFactoryBean();
    jaxWsProxyFactoryBean.setServiceClass(GeoAdminServiceExterneImpl.class);
    jaxWsProxyFactoryBean.setAddress(ENDPOINT_ADDRESS);
    return (GeoAdminServiceExterneImpl) jaxWsProxyFactoryBean.create();
  }

  private static Delegation delegationBuilder(String code,
                                              String libelle,
                                              String acheminement,
                                              String adresse1,
                                              String adresse2,
                                              String adresse3,
                                              String adresse4,
                                              String adresse5,
                                              String codePostal,
                                              String email,
                                              String fax,
                                              String siteWeb,
                                              String telephone,
                                              String telephone2,
                                              String telephone3) {
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

  private static Departement departementBuilder(String numero, String nom) {
    Departement obj = new Departement();
    obj.setCodeInsee(numero);
    obj.setNomEnrichi(nom);
    return obj;
  }

  private static Commune communeBuilder(String nomCommune, String nomCourt, String numInsee, String bassin) {
    CirconscriptionBassin circonscriptionBassin = new CirconscriptionBassin();
    circonscriptionBassin.setCode(bassin);
    Commune obj = new Commune();
    obj.setNomEnrichi(nomCommune);
    obj.setNomMajuscule(nomCourt);
    obj.setCodeInsee(numInsee);
    obj.setCirconscriptionBassin(circonscriptionBassin);
    return obj;
  }

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

  @Test
  public void testFindAllCommunes2017() {
    List<CommuneVO> communes = client.findAllCommunes(2017);
    assertNotNull("CXF didn't return a List", communes);
    assertEquals("CXF returned a List, but the wrong size", 0, communes.size());
  }

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
