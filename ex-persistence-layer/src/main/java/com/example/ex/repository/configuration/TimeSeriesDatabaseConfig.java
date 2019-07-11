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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConditionalOnExpression("'${spring.jpa.database}'.equals(\"default\")")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "tsEntityManagerFactory", transactionManagerRef = "tsTransactionManager", 
    basePackages = { "com.example.ex.repository.ts" })
public class TimeSeriesDatabaseConfig {

    @Bean(name = "tsDataSource")
    @ConfigurationProperties(prefix = "ts.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
      
    @Bean(name = "tsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("tsDataSource") DataSource dataSource) {
        return builder
          .dataSource(dataSource)
          .properties(hibernateProperties())
          .packages("com.example.ex.model.entity.ts")
          .persistenceUnit("ts")
          .build();
    }
      
    @Bean(name = "tsTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("tsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
      return new JpaTransactionManager(entityManagerFactory);
    }
    
    private Map<String,String> hibernateProperties() {
        Map<String,String> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        hibernateProperties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        return hibernateProperties;
    }
}