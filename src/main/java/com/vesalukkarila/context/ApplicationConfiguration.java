package com.vesalukkarila.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vesalukkarila.ApplicationLauncher;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@EnableWebMvc
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // TODO: Beans; datasource, jdbcTemplate(datasource), simpleJdbcInsert(jdbcTemplate)
    //TODO: create schema.sql to resources, make datasource run that script
    // TODO: later; find out how to allow multiple access to database simultaneously (properties?, through datasource(?))
    @Bean
    public JdbcDataSource jdbcDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/songsH2Database;INIT=RUNSCRIPT FROM 'classpath:schema.sql' ");
        dataSource.setUser("sa"); // sa&sa somewhat a convention with H2
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsert() {
        return new SimpleJdbcInsert(jdbcDataSource())
                .withTableName("songs")
                .usingColumns("name", "artist", "publishYear")
                .usingGeneratedKeyColumns("id");
    }
}
