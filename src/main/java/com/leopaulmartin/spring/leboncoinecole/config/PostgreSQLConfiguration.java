package com.leopaulmartin.spring.leboncoinecole.config;

import ch.qos.logback.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/*
Configuration
	https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.java-config
	https://www.baeldung.com/spring-boot-custom-auto-configuration
-
EnableJpaRepositories: activates Spring Data JPA repositories
-
EnableTransactionManagement: explicitly to get annotation-based configuration of facades to work such as @Transactional
-
ConditionalOnClass: Let's specify that our PostgreSQL will only be loaded if the class DataSource is present, in which case we can assume the application will use a database:
https://www.baeldung.com/spring-boot-custom-auto-configuration#1-class-conditions
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.leopaulmartin.spring.leboncoinecole.persistence.repositories")
@EnableTransactionManagement
@ConditionalOnClass(DataSource.class)
@PropertySource("classpath:postgresql.properties")
public class PostgreSQLConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(PostgreSQLConfiguration.class);
	private Context env;

	@Bean
	@ConditionalOnProperty(name = "usepostgresql", havingValue = "local")
	@ConditionalOnMissingBean
	public DataSource dataSource() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.driverClassName("org.postgresql.Driver");
		builder.url("jdbc:postgresql://localhost:5432/leboncoinecole_app");
		builder.username("postgres");
		builder.password("test");
		logger.info("My custom Postgres datasource bean has been initialized and set by local method");
		return builder.build();
	}

	@Bean(name = "dataSource")
	@ConditionalOnProperty(name = "usepostgresql", havingValue = "custom")
	@ConditionalOnMissingBean
	public DataSource dataSource2() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.driverClassName("postgresql.driver");
		builder.url(env.getProperty("postgresql.url"));
		builder.username(env.getProperty("postgresql.username"));
		builder.password("postgresql");
		logger.info("My custom Postgres datasource bean has been initialized and set by custom method");
		return builder.build();
	}

	/*
	Bean: You must create LocalContainerEntityManagerFactoryBean and not EntityManagerFactory directly, since the former also participates in exception translation mechanisms in addition to creating EntityManagerFactory
	ConditionalOnBean: To exemplify this, let's add an entityManagerFactory bean to our configuration class, and specify we only want this bean to be created if a bean called dataSource is present and if a bean called entityManagerFactory is not already defined:
	https://www.baeldung.com/spring-boot-custom-auto-configuration#2-bean-conditions
	*/
	@Bean
	@ConditionalOnBean(name = "dataSource")
	@ConditionalOnMissingBean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setJpaProperties(additionalProperties());
		factory.setMappingResources("com.leopaulmartin.spring.leboncoinecole.persistence.entities.User");
		factory.setMappingResources("com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student");
		factory.setMappingResources("com.leopaulmartin.spring.leboncoinecole.persistence.entities.Administrator");
		factory.setPackagesToScan("com.leopaulmartin.spring.leboncoinecole.persistence");
		factory.setDataSource(dataSource());
		return factory;
	}

	/*
		Bean: Spring Data JPA requires a PlatformTransactionManager bean named transactionManager to be present if no explicit transaction-manager-ref is defined.
	 	ConditionalOnMissingBean: Let's also configure a transactionManager bean that will only be loaded if a bean of type JpaTransactionManager is not already defined:
	*/
	@Bean
	@ConditionalOnMissingBean(type = "JpaTransactionManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@ConditionalOnResource(resources = "classpath:postgresql.properties")
	Properties additionalProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		hibernateProperties.setProperty("hibernate.generate_statistics", "false");
		hibernateProperties.setProperty("hibernate.show-sql", "true");
		return hibernateProperties;
	}
}
