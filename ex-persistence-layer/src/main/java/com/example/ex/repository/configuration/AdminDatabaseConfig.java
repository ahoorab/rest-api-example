package com.example.ex.repository.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConditionalOnExpression("'${spring.jpa.database}'.equals(\"default\")")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "adminEntityManagerFactory", transactionManagerRef = "adminTransactionManager", 
    basePackages = { "com.example.ex.repository.admin" })
public class AdminDatabaseConfig {

    @Primary
    @Bean(name = "adminDataSource")
    @ConfigurationProperties(prefix = "admin.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
      
    @Primary
    @Bean(name = "adminEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("adminDataSource") DataSource dataSource) {
        return builder
          .dataSource(dataSource)
          .properties(hibernateProperties())
          .packages("com.example.ex.model.entity.admin","com.example.ex.model.converter")
          .persistenceUnit("admin")
          .build();
    }
      
    @Primary
    @Bean(name = "adminTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("adminEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
      return new JpaTransactionManager(entityManagerFactory);
    }
    
    private Map<String,String> hibernateProperties() {
        Map<String,String> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return hibernateProperties;
    }
}