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
package fr.aesn.rade.batch.tasks;

import java.lang.annotation.Annotation;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.Evenement;
import fr.aesn.rade.persist.tools.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Batch Configuration for use in JUnit tests.
 *
 * The generated Application Context contains the following:
 * <ul>
 * <li>In memory Derby Database as DataSource</li>
 * <li>JPA Entity and Transaction Managers</li>
 * <li>JPA DAOs for Rade Persist Entities
 * (with overwritten annotations for Derby compatibility)</li>
 * <li>Rade Services</li>
 * <li>In memory Spring Batch Job Repository and Launcher</li>
 * </ul>
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackages = "fr.aesn.rade.persist.dao")
@ComponentScan(basePackages = "fr.aesn.rade.service")
@ImportResource(locations = "classpath*:batch-context-test.xml")
public class SpringBatchTestConfiguration
  implements BatchConfigurer {

  /** 
   * GeneratedValue Annotation of type Identity
   * because Derby cannot do type Sequence.
   */
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

  /** 
   * Modify GeneratedValue Annotations on Entities id 
   * because Derby cannot do Sequence.
   */
  protected void overrideAnnotations() {
    try {
      AnnotationUtils.getFieldAnnotations(Audit.class, "id")
                     .put(identity.annotationType(), identity);
      AnnotationUtils.getFieldAnnotations(EntiteAdministrative.class, "id")
                     .put(identity.annotationType(), identity);
      AnnotationUtils.getFieldAnnotations(Evenement.class, "id")
                     .put(identity.annotationType(), identity);
    } catch (NoSuchFieldException e) {
      log.error("This Should never happen (unless the classes have been changed)");
    }
  }

  /**
   * In memory Derby Database.
   * @return In memory Derby Database.
   */
  @Bean
  protected DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
    ds.setUrl("jdbc:derby:memory:testdb");
    ds.setUsername("sa");
    ds.setPassword("");
    return ds;
  }

  /**
   * JPA Entity Manager Factory using Hibernate on Derby Database.
   * @return Entity Manager Factory.
   */
  @Bean
  protected EntityManagerFactory entityManagerFactory() {
    overrideAnnotations(); // because Derby cannot do Sequence GeneratedValue
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("fr.aesn.rade.persist");
    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
    jpaProperties.put("hibernate.hbm2ddl.auto", "update");
    jpaProperties.put("hibernate.show_sql", "false");
    //jpaProperties.put("hibernate.format_sql", "create-drop");
    entityManagerFactoryBean.setJpaProperties(jpaProperties);
    entityManagerFactoryBean.afterPropertiesSet();
    return entityManagerFactoryBean.getObject();
  }

  /**
   * In Memory JobRepository Factory Bean for Spring Batch.
   * @return JobRepository Factory Bean.
   */
  @Bean
  protected MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean() {
    MapJobRepositoryFactoryBean jobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
    jobRepositoryFactoryBean.setTransactionManager(new ResourcelessTransactionManager());
    return jobRepositoryFactoryBean;
  }

  /**
   * From BatchConfigurer Interface, defines the PlatformTransactionManager
   * setup by @EnableBatchProcessing Annotation.
   */
  @Override
  public PlatformTransactionManager getTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory());
    return transactionManager;
  }

  /**
   * From BatchConfigurer Interface, defines the JobRepository
   * setup by @EnableBatchProcessing Annotation.
   */
  @Override
  public JobRepository getJobRepository() throws Exception {
    JobRepository jobRepository = mapJobRepositoryFactoryBean().getObject();
    return jobRepository;
  }

  /**
   * From BatchConfigurer Interface, defines the JobExplorer
   * setup by @EnableBatchProcessing Annotation.
   */
  @Override
  public JobExplorer getJobExplorer() throws Exception {
    MapJobRepositoryFactoryBean factory = mapJobRepositoryFactoryBean();
    SimpleJobExplorer jobExplorer = new SimpleJobExplorer(factory.getJobInstanceDao(),
                                                          factory.getJobExecutionDao(),
                                                          factory.getStepExecutionDao(),
                                                          factory.getExecutionContextDao());
    return jobExplorer;
  }

  /**
   * From BatchConfigurer Interface, defines the JobLauncher
   * setup by @EnableBatchProcessing Annotation.
   */
  @Override
  public JobLauncher getJobLauncher() throws Exception {
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(getJobRepository());
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}
