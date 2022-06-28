package com.epam.repository.dao;

import com.epam.repository.config.SpringJdbcConfig;
import com.epam.repository.dao.provider.CollectorUtils;
import com.epam.repository.dao.provider.GiftCertificateProvider;
import com.epam.repository.model.entity.GiftCertificateEntity;
import com.epam.repository.model.entity.TagEntity;
import com.epam.repository.model.enums.SortType;
import com.epam.repository.model.exception.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/21/2022
 */
@ContextConfiguration(classes = SpringJdbcConfig.class)
@TestPropertySource(properties = "classpath:application.properties", locations = "classpath:.env")
@ExtendWith(SpringExtension.class)
class GiftCertificateDaoTest {

    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    public void setBeforeAll(@Autowired GiftCertificateDao giftCertificateDao,
                             @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        executeSqlScript(namedParameterJdbcTemplate);

        this.giftCertificateDao = giftCertificateDao;
    }

    private void executeSqlScript(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        DataSource dataSource = namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("create_tables_script_h2.sql"));
        populator.addScript(new ClassPathResource("insert_tb_tags_script.sql"));

        populator.execute(Objects.requireNonNull(dataSource));

    }

    @DisplayName("Should save gift certificate to database and return the same entity with assigned id. Compared using equals")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldSaveGiftCertificateAndReturn(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(giftCertificateEntity, saved);
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to save")
    @ParameterizedTest
    @NullSource
    public void shouldNotSaveAndThrowIllegalArgumentException(GiftCertificateEntity giftCertificateEntity) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.save(giftCertificateEntity));
    }

    @DisplayName("Should find gift certificate by id and return it")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldFindEntitiesByIdAndReturn(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved, giftCertificateEntity);

        Optional<GiftCertificateEntity> entity = giftCertificateDao.findById(giftCertificateEntity.getId());

        org.assertj.core.api.Assertions.assertThatCode(entity::get).doesNotThrowAnyException();
        org.assertj.core.api.Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(Optional.of(saved));
    }

    @DisplayName("Optional should contain only null element")
    @ParameterizedTest
    @MethodSource("failureCase")
    public void shouldContainOnlyNull(Long id) {
        Optional<GiftCertificateEntity> entity = giftCertificateDao.findById(id);
        Assertions.assertTrue(entity.isEmpty());
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to findById")
    @ParameterizedTest
    @NullSource
    public void shouldNotFindEntityAndThrowIllegalArgumentException(Long id) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.findById(id));
    }

    @DisplayName("Should get gift certificate by id and return it")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldGetEntitiesByIdAndReturn(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);

        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved, giftCertificateEntity);

        GiftCertificateEntity entity = giftCertificateDao.getById(saved.getId());
        org.assertj.core.api.Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(saved);
    }

    @DisplayName("Should throw EntityNotFoundException")
    @ParameterizedTest
    @MethodSource("failureCase")
    public void shouldNotGetEntityThrowEntityNotFoundException(Long id) {
        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> giftCertificateDao.getById(id));
    }

    @DisplayName("Should get wrong entity")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldGetWrongEntity(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved, giftCertificateEntity);

        if (saved.getId() != 1) {
            Optional<GiftCertificateEntity> entity = giftCertificateDao.findById(saved.getId());

            org.assertj.core.api.Assertions.assertThat(entity).usingRecursiveComparison().isNotEqualTo(Optional.of(saved));
        }
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to getById")
    @ParameterizedTest
    @NullSource
    public void shouldNotGetEntityAndThrowIllegalArgumentException(Long id) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.getById(id));
    }

    @DisplayName("Should get entities by certificate name")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldFindByName(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(giftCertificateEntity.getName());
        Assertions.assertEquals(saved, giftCertificateEntity);

        Optional<GiftCertificateEntity> entity = giftCertificateDao.findByName(giftCertificateEntity.getName());

        org.assertj.core.api.Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(Optional.of(giftCertificateEntity));
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to findByName")
    @ParameterizedTest
    @NullSource
    public void shouldNotFindByNameAndThrowIllegalArgumentException(String tagName) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> giftCertificateDao.findByName(tagName));
    }

    @DisplayName("Should find gift certificate by tag name")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldFindByTagName(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(giftCertificateEntity.getTags());
        Assertions.assertEquals(saved, giftCertificateEntity);

        Set<TagEntity> tagEntities = saved.getTags();
        Assertions.assertEquals(giftCertificateEntity, saved);
        org.assertj.core.api.Assertions.assertThat(tagEntities).usingRecursiveComparison().isEqualTo(giftCertificateEntity.getTags());

        tagEntities.forEach(tagEntity -> giftCertificateDao.findByTag(tagEntity.getName(), 500, 0, SortType.NONE)
                .forEach(entity -> {
                    Assertions.assertNotNull(entity);
                    Assertions.assertNotNull(entity.getTags());
                    Assertions.assertTrue(entity.getTags().stream().anyMatch(tag -> tag.equals(tagEntity)));
                }));

    }

    @DisplayName("Should untag the certificate")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldUntag(GiftCertificateEntity giftCertificateEntity) {
        Random random = new Random();

        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(giftCertificateEntity.getTags());
        Assertions.assertEquals(saved, giftCertificateEntity);

        Set<TagEntity> tagEntities = giftCertificateEntity.getTags().stream()
                .collect(CollectorUtils.toShuffledStream())
                .limit(1 + random.nextInt(giftCertificateEntity.getTags().size() - 1))
                .collect(Collectors.toSet());

        int prevSize = giftCertificateEntity.getTags().size();

        Optional<GiftCertificateEntity> entity = tagEntities.stream()
                .map(tagEntity -> giftCertificateDao.untagCertificate(giftCertificateEntity.getId(),
                        tagEntities.stream().map(TagEntity::getName).collect(Collectors.toSet())))
                .reduce((first, second) -> second);

        Assertions.assertFalse(entity.isEmpty());
        tagEntities.forEach(tagEntity ->
                org.assertj.core.api.Assertions.assertThat(new ArrayList<>(entity.get().getTags())).asList().doesNotContain(tagEntity));
        Assertions.assertNotEquals(prevSize, entity.get().getTags().size());
    }

    @DisplayName("Should not untag the certificate, as null value passed to arguments of untag method")
    @ParameterizedTest
    @CsvSource(value = {"null, null"}, nullValues = {"null"})
    public void shouldNotUntagAndThrowIllegalArgumentException(Long id, Set<String> tagName) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->
                giftCertificateDao.untagCertificate(id, tagName));
    }

    @DisplayName("Should return true if all passed ids exists in database")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldReturnTrue(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved, giftCertificateEntity);

        Assertions.assertTrue(giftCertificateDao.existsById(giftCertificateEntity.getId()));
    }

    @DisplayName("Should update entity fields and return")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldUpdateAndReturn(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved, giftCertificateEntity);

        Optional<GiftCertificateEntity> toCheck = giftCertificateDao.findById(giftCertificateEntity.getId());
        org.assertj.core.api.Assertions.assertThat(toCheck).usingRecursiveComparison().isEqualTo(Optional.of(saved));

        GiftCertificateEntity entity = giftCertificateDao.getById(giftCertificateEntity.getId());
        org.assertj.core.api.Assertions.assertThat(toCheck).usingRecursiveComparison().isEqualTo(Optional.of(entity));

        Random random = new Random();
        switch (random.nextInt(10)) {
            case 0:
                entity.setName(entity.getName() + " Changed");
                break;
            case 1:
                entity.setDescription(entity.getDescription() + " Changed");
                break;
            case 2:
                entity.setDuration(entity.getDuration().plusDays(40));
                break;
            case 3:
                entity.setPrice(entity.getPrice().add(BigDecimal.valueOf(40000)));
                break;
            case 4:
                entity.setName(entity.getName() + " Changed");
                entity.setDescription(entity.getDescription() + " Changed");
                break;
            case 5:
                entity.setName(entity.getName() + " Changed");
                entity.setDuration(entity.getDuration().plusDays(40));
                break;
            case 6:
                entity.setName(entity.getName() + " Changed");
                entity.setPrice(entity.getPrice().add(BigDecimal.valueOf(40000)));
                break;
            case 7:
                entity.setDescription(entity.getDescription() + " Changed");
                entity.setDuration(entity.getDuration().plusDays(40));
                break;
            case 8:
                entity.setDuration(entity.getDuration().plusDays(40));
                entity.setPrice(entity.getPrice().add(BigDecimal.valueOf(40000)));
                break;
            case 9:
                entity.setName(entity.getName() + " Changed");
                entity.setDescription(entity.getDescription() + " Changed");
                entity.setDuration(entity.getDuration().plusDays(40));
                entity.setPrice(entity.getPrice().add(BigDecimal.valueOf(40000)));
        }
        entity = giftCertificateDao.save(entity);
        org.assertj.core.api.Assertions.assertThat(toCheck).usingRecursiveComparison().isNotEqualTo(Optional.of(entity));
    }

    @DisplayName("Should delete entity by id")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    public void shouldDeleteById(GiftCertificateEntity giftCertificateEntity) {
        GiftCertificateEntity saved = giftCertificateDao.save(giftCertificateEntity);
        Assertions.assertNotNull(giftCertificateEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved, giftCertificateEntity);

        giftCertificateDao.deleteById(saved.getId());
        Assertions.assertFalse(giftCertificateDao.existsById(saved.getId()));
    }

    private static Stream<Arguments> failureCase() {
        List<Arguments> idArguments = new ArrayList<>();

        for (int id = 501; id <= 1001; id++) {
            idArguments.add(Arguments.of((long) id));
        }
        return idArguments.stream();
    }

}