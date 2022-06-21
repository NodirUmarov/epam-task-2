package com.epam.repository.dao;

import com.epam.repository.config.SpringJdbcConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;


/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/21/2022
 */
@ContextConfiguration(classes = SpringJdbcConfig.class)
@TestPropertySource(properties = "classpath:application.properties")
@ExtendWith(SpringExtension.class)
class GiftCertificateDaoTest {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp(@Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void test() throws SQLException {
        System.out.println(namedParameterJdbcTemplate.getJdbcTemplate().getDataSource().getConnection().getMetaData().getURL());
    }
}