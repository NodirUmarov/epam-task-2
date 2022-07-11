package com.epam.data.dao;

import com.epam.data.config.DataConfig;
import com.epam.data.dao.provider.TagProvider;
import com.epam.data.model.entity.TagEntity;
import com.epam.data.model.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
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
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/28/2022
 */
@ContextConfiguration(classes = DataConfig.class)
@TestPropertySource(properties = "classpath:application.properties", locations = "classpath:.env")
@ExtendWith(SpringExtension.class)
class TagDaoTest {

    private TagDao tagDao;

    @BeforeEach
    public void setBeforeAll(@Autowired TagDao tagDao,
                             @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        executeSqlScript(namedParameterJdbcTemplate);

        this.tagDao = tagDao;
    }

    private void executeSqlScript(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        DataSource dataSource = namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("create_tables_script_h2.sql"));

        populator.execute(Objects.requireNonNull(dataSource));
    }

    @DisplayName("Should save all tagEntities passed and return with assigned id")
    @ParameterizedTest
    @MethodSource("tags")
    public void shouldSaveAllAndReturn(Set<TagEntity> tags) {
        Set<TagEntity> saved = tagDao.saveAll(tags);

        saved.forEach(tagEntity -> {
            Assertions.assertThat(tagEntity).isNotNull();
            Assertions.assertThat(tagEntity.getId()).isNotNull();
        });

        Assertions.assertThat(saved.containsAll(tags)).isTrue();

        Set<TagEntity> entities = tagDao.findAllSorted(150, 0);
        Assertions.assertThat(entities).usingRecursiveComparison().isEqualTo(saved);
    }

    @DisplayName("Should not save and throw IllegalArgumentException")
    @ParameterizedTest
    @NullSource
    public void shouldNotSaveAndThrowIllegalArgument(Set<TagEntity> tagEntities) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> tagDao.saveAll(tagEntities));
    }

    @DisplayName("Should find all tagEntities passed in given range")
    @ParameterizedTest
    @MethodSource("tags")
    public void findAllSorted(Set<TagEntity> tags) {
        Set<TagEntity> saved = tagDao.saveAll(tags);

        saved.forEach(tagEntity -> Assertions.assertThat(tagEntity).isNotNull());
        Assertions.assertThat(saved.containsAll(tags)).isTrue();

        Set<TagEntity> firstFifty = tagDao.findAllSorted(saved.size() / 2, 0);
        Assertions.assertThat(saved.containsAll(firstFifty)).isTrue();
        Assertions.assertThat(saved.size() / 2).isEqualTo(firstFifty.size());

        Set<TagEntity> onlyOne = tagDao.findAllSorted(1, saved.size() / 3);
        Assertions.assertThat(saved.containsAll(onlyOne)).isTrue();
        Assertions.assertThat(1).isEqualTo(onlyOne.size());
    }

    @DisplayName("Should not find and throw IllegalArgumentException")
    @ParameterizedTest
    @CsvSource(value = {"null, null"}, nullValues = {"null"})
    public void shouldNotFindAndThrowIllegalArgument(Integer limit, Integer offset) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> tagDao.findAllSorted(limit, offset));
    }

    @DisplayName("Should save entity and return with assigned id")
    @ParameterizedTest
    @ArgumentsSource(TagProvider.class)
    public void shouldSaveAndReturn(TagEntity tagEntity) {
        TagEntity saved = tagDao.save(tagEntity);
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(tagEntity).isEqualTo(saved);
    }

    @DisplayName("Should not save entity and throw IllegalArgument")
    @ParameterizedTest
    @NullSource
    public void shouldNotSaveAndThrowIllegalArgument(TagEntity tagEntity) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> tagDao.save(tagEntity));
    }

    @DisplayName("Should find tag by id and return it")
    @ParameterizedTest
    @ArgumentsSource(TagProvider.class)
    public void shouldFindEntitiesByIdAndReturn(TagEntity tagEntity) {
        TagEntity saved = tagDao.save(tagEntity);
        Assertions.assertThat(tagEntity).isNotNull();
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved).isEqualTo(tagEntity);

        Optional<TagEntity> entity = tagDao.findById(tagEntity.getId());

        Assertions.assertThatCode(entity::get).doesNotThrowAnyException();
        Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(Optional.of(saved));
    }

    @DisplayName("Optional should contain only null element")
    @ParameterizedTest
    @MethodSource("failureCase")
    public void shouldContainOnlyNull(Long id) {
        Optional<TagEntity> entity = tagDao.findById(id);
        Assertions.assertThat(entity.isEmpty()).isTrue();
    }

    @DisplayName("Should throw IllegalArgumentException due to null value passed as argument to findById")
    @ParameterizedTest
    @NullSource
    public void shouldNotFindEntityAndThrowIllegalArgumentException(Long id) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> tagDao.findById(id));
    }

    @DisplayName("Should get tag by id and return it")
    @ParameterizedTest
    @ArgumentsSource(TagProvider.class)
    public void shouldGetEntitiesByIdAndReturn(TagEntity tagEntity) {
        TagEntity saved = tagDao.save(tagEntity);

        Assertions.assertThat(tagEntity).isNotNull();
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved).isEqualTo(tagEntity);

        TagEntity entity = tagDao.getById(saved.getId());
        Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(saved);
    }

    @DisplayName("Should throw EntityNotFoundException")
    @ParameterizedTest
    @MethodSource("failureCase")
    public void shouldNotGetEntityThrowEntityNotFoundException(Long id) {
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> tagDao.getById(id));
    }

    @DisplayName("Should get wrong entity")
    @ParameterizedTest
    @ArgumentsSource(TagProvider.class)
    public void shouldGetWrongEntity(TagEntity tagEntity) {
        TagEntity saved = tagDao.save(tagEntity);
        Assertions.assertThat(tagEntity).isNotNull();
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved).isEqualTo(tagEntity);

        if (saved.getId() != 1) {
            Optional<TagEntity> entity = tagDao.findById(saved.getId());

            Assertions.assertThat(entity).usingRecursiveComparison().isNotEqualTo(Optional.of(saved));
        }
    }

    @DisplayName("Should return true if all passed tags' names exists in database")
    @ParameterizedTest
    @ArgumentsSource(TagProvider.class)
    public void shouldReturnTrue(TagEntity tagEntity) {
        TagEntity saved = tagDao.save(tagEntity);
        Assertions.assertThat(tagEntity).isNotNull();
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved).isEqualTo(tagEntity);

        Assertions.assertThat(tagDao.existsByName(tagEntity.getName())).isTrue();
    }

    @DisplayName("Should delete entity by id")
    @ParameterizedTest
    @ArgumentsSource(TagProvider.class)
    public void shouldDeleteById(TagEntity tagEntity) {
        TagEntity saved = tagDao.save(tagEntity);
        Assertions.assertThat(tagEntity).isNotNull();
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved).isEqualTo(tagEntity);

        tagDao.deleteById(saved.getId());
        Assertions.assertThat(tagDao.existsByName(saved.getName())).isFalse();
    }

    public static Stream<Arguments> tags() {
        return Stream.of(Arguments.of(TagProvider.entities()));
    }

    private static Stream<Arguments> failureCase() {
        List<Arguments> idArguments = new ArrayList<>();

        for (int id = 200; id <= 500; id++) {
            idArguments.add(Arguments.of((long) id));
        }
        return idArguments.stream();
    }



}