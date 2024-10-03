package com.vesalukkarila.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vesalukkarila.ApplicationLauncher;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JdbcDataSource jdbcDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/songsH2Database;INIT=RUNSCRIPT FROM 'classpath:schema.sql' ");
        dataSource.setUser("sa"); // sa&sa somewhat a convention with H2
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(jdbcDataSource());
    }

    @Bean
    public TransactionManager transactionManager(){
        return new DataSourceTransactionManager(jdbcDataSource());
    }

}
