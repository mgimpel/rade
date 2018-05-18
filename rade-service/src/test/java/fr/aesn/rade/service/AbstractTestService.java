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

import java.lang.annotation.Annotation;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.sql.DataSource;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.Evenement;
import fr.aesn.rade.persist.tools.AnnotationUtils;

/**
 * Abstract JUnit Test Class for Services.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public abstract class AbstractTestService {
  /** Static Spring Configuration. */
  @Configuration
  @EnableJpaRepositories(basePackages = "fr.aesn.rade.persist.dao")
  protected static class Config {
    /** GeneratedValue Annotation of type Identity because Derby cannot do type Sequence. */
    protected Annotation identity = new GeneratedValue() {
      @Override public Class<? extends Annotation> annotationType() {
        return GeneratedValue.class;
      }
      @Override public String generator() {
        return "";
      }
      @Override public GenerationType strategy() {
        return GenerationType.IDENTITY;
      }
    };
    /** Modify Annotations on Entities because Derby cannot do Sequence GeneratedValue */
    protected void overrideAnnotations() {
      try {
        AnnotationUtils.getFieldAnnotations(Audit.class, "id").put(identity.annotationType(), identity);
        AnnotationUtils.getFieldAnnotations(EntiteAdministrative.class, "id").put(identity.annotationType(), identity);
        AnnotationUtils.getFieldAnnotations(Evenement.class, "id").put(identity.annotationType(), identity);
      } catch (NoSuchFieldException e) {
        System.out.println("This Should never happen (unless the classes have been changed)");
      }
    }
    /** In memory Derby Database */
    @Bean
    protected DataSource dataSource() {
      DriverManagerDataSource ds = new DriverManagerDataSource();
      ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
      ds.setUrl("jdbc:derby:memory:testdb");
      ds.setUsername("sa");
      ds.setPassword("");
      return ds;
    }
    @Bean
    protected EntityManagerFactory entityManagerFactory() {
      overrideAnnotations();
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactoryBean.setDataSource(dataSource());
      entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      entityManagerFactoryBean.setPackagesToScan("fr.aesn.rade.persist.model");
      Properties jpaProperties = new Properties();
      jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
      jpaProperties.put("hibernate.hbm2ddl.auto", "update");
      jpaProperties.put("hibernate.show_sql", "false");
      jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");
      //jpaProperties.put("hibernate.format_sql", "create-drop");
      entityManagerFactoryBean.setJpaProperties(jpaProperties);
      entityManagerFactoryBean.afterPropertiesSet();
      return entityManagerFactoryBean.getObject();
    }
    @Bean
    protected EntityManager entityManager() {
      return entityManagerFactory().createEntityManager();
    }
    @Bean
    protected JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory);
      return transactionManager;
    }
  }
  /** In Memory Derby Database Instance. */
  protected static EmbeddedDatabase db;
  /** Entity Manager. */
  @Autowired
  protected EntityManager entityManager;

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
        .build();
  }

  /**
   * Close the Test Environment cleanly.
   */
  @AfterClass
  public static void tearDownClass() {
    db.shutdown();
  }
}
