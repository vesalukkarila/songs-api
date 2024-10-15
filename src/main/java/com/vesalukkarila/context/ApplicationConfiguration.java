package com.vesalukkarila.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vesalukkarila.ApplicationLauncher;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * ApplicationConfiguration is the central configuration class for the Spring application.
 * This class is responsible for setting up the necessary beans for the application, including
 * the ObjectMapper for JSON processing, data source for the H2 database, JdbcTemplate for
 * database operations.
 */
@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationConfiguration{

    /**
     * Creates and configures an ObjectMapper bean.
     *
     * @return a configured ObjectMapper instance for converting Java objects to JSON and vice versa
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Creates and configures a JdbcDataSource bean for connecting to the H2 database.
     *
     * @return a JdbcDataSource configured with the database URL, user, and password
     */
    @Bean
    public JdbcDataSource jdbcDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/songsH2Database;" +
                "INIT=RUNSCRIPT FROM 'classpath:schema.sql' ");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    /**
     * Creates and configures a JdbcTemplate bean.
     *
     * @return a JdbcTemplate instance configured with the JdbcDataSource for performing database operations
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(jdbcDataSource());
    }

    /**
     * Creates and configures a TransactionManager bean for managing database transactions.
     *
     * @return a DataSourceTransactionManager configured with the JdbcDataSource
     */
    @Bean
    public TransactionManager transactionManager(){
        return new DataSourceTransactionManager(jdbcDataSource());
    }

}
