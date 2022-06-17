package com.epam.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/17/2022
 */

@Configuration
@ComponentScan("com.epam.repository")
@TestPropertySource("classpath:application-test.properties")
public class SpringTestJdbcConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Primary
    @Bean
    public DataSource h2DataSource() {
        EmbeddedDatabase database = new EmbeddedDatabaseBuilder()
                .set
    }
}
