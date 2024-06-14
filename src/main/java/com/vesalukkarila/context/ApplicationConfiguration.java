package com.vesalukkarila.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vesalukkarila.ApplicationLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
}
