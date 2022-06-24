package com.epam.repository.dao;

import com.epam.repository.config.SpringJdbcConfig;
import com.epam.repository.model.entity.GiftCertificateEntity;
import com.epam.repository.model.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/21/2022
 */
@ContextConfiguration(classes = SpringJdbcConfig.class)
@TestPropertySource(properties = "classpath:application.properties", locations = "classpath:.env")
@ExtendWith(SpringExtension.class)
class GiftCertificateDaoTest {

    private final GiftCertificateDao giftCertificateDao;

    public GiftCertificateDaoTest(@Autowired GiftCertificateDao giftCertificateDao,
                                  @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        executeSqlScript(namedParameterJdbcTemplate);

        this.giftCertificateDao = giftCertificateDao;
    }

    private void executeSqlScript(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        DataSource dataSource = namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("create_tables_script.sql"));
        populator.addScript(new ClassPathResource("insert_tb_gift_certificate_script.sql"));
        populator.addScript(new ClassPathResource("insert_tb_tags_script.sql"));
        populator.addScript(new ClassPathResource("insert_gift_certificate_has_tag_script.sql"));

        populator.execute(Objects.requireNonNull(dataSource));

    }

    @DisplayName("Should save gift certificate to database and return the same entity with assigned id")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldSaveGiftCertificateAndReturnId(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertEquals(saved, giftCertificateEntity);
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to save")
    @ParameterizedTest
    @NullSource
    public void shouldNotSaveAndThrowIllegalArgumentException(GiftCertificateEntity giftCertificateEntity) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.save(giftCertificateEntity));
    }

    @DisplayName("Should find gift certificate by id and return it")
    @ParameterizedTest
    @MethodSource("successCase")
    public void shouldFindEntitiesByIdAndReturn(Long id) {
        Assertions.assertNotNull(giftCertificateDao.findById(id).get());
    }

    @DisplayName("Should throw NoSuchElementException due to get operation invoked on Optional.empty object")
    @ParameterizedTest
    @MethodSource("failureCase")
    public void shouldNotFindEntityAndThrowNoSuchElementException(Long id) {
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> giftCertificateDao.findById(id).get());
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to findById")
    @ParameterizedTest
    @NullSource
    public void shouldNotFindEntityAndThrowIllegalArgumentException(Long id) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.findById(id));
    }

    @DisplayName("Should get gift certificate by id and return it")
    @ParameterizedTest
    @MethodSource("successCase")
    public void shouldGetEntitiesByIdAndReturn(Long id) {
        Assertions.assertNotNull(giftCertificateDao.getById(id));
    }

    @DisplayName("Should throw EntityNotFoundException")
    @ParameterizedTest
    @MethodSource("failureCase")
    public void shouldNotGetEntityThrowEntityNotFoundException(Long id) {
        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> giftCertificateDao.getById(id));
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to getById")
    @ParameterizedTest
    @NullSource
    public void shouldNotGetEntityThrowIllegalArgumentException(Long id) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.getById(id));
    }

    @DisplayName("Should get entities by tag name if such presents")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldFindByName(GiftCertificateEntity giftCertificateEntity) {
        Assertions.assertNotNull(giftCertificateDao.findByName(giftCertificateEntity.getName()).get());
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to findByName")
    @ParameterizedTest
    @NullSource
    public void shouldThrowIllegalArgumentException(String tagName) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.findByName(tagName));
    }

    private static Stream<Arguments> failureCase() {
        return idsProvider(501, 1000);
    }

    private static Stream<Arguments> successCase() {
        return idsProvider(1, 500);
    }

    private static Stream<Arguments> idsProvider(int floor, int ceil) {
        Random random = new Random();
        List<Arguments> idArguments = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            idArguments.add(Arguments.of(floor + (long) random.nextInt(ceil - floor)));
        }
        return idArguments.stream();
    }

}