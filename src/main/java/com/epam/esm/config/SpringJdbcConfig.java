package com.epam.esm.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableWebMvc
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpringJdbcConfig {

    @Value("${spring.datasource.driver}")
    String driver;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.schema}")
    String schema;

    @Bean
    @Primary
    public DataSource postgresqlDataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setSchema(schema);
        dataSource.setPassword(password);
        var logger = dataSource.getParentLogger();
        logger.setLevel(Level.ALL);
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
