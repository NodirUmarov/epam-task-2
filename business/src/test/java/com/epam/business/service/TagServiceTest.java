package com.epam.business.service;

import com.epam.business.mapper.dto.TagMapper;
import com.epam.business.mapper.requestMapper.CreateTagMapper;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.impl.TagServiceImpl;
import com.epam.business.service.provider.CreateTagProvider;
import com.epam.data.dao.TagDao;
import com.epam.data.model.entity.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */

// Warnings suppressed due to tagMapper is injected as Mock and using internally,
// and ArgumentCaptor.forClass using generic type what causes unchecked assignment
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "unused"})
class TagServiceTest {

    @Mock
    TagDao tagDao;

    @Mock
    CreateTagMapper createTagMapper;

    @Mock
    TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @BeforeEach
    public void beforeAll() {
        assertThat(tagServiceImpl).isNotNull();
    }

    @DisplayName("Should create tag")
    @ParameterizedTest
    @ArgumentsSource(CreateTagProvider.class)
    public void shouldCreateTag(Set<TagRequest> requests) {
        when(createTagMapper.toEntitySet(requests)).thenReturn(requests.stream()
                .map(request -> new TagEntity(request.getName()))
                .collect(Collectors.toSet()));

        tagServiceImpl.create(requests);

        ArgumentCaptor<HashSet<TagEntity>> createTagCaptor = ArgumentCaptor.forClass(HashSet.class);

        verify(tagDao).saveAll(createTagCaptor.capture());

        Set<TagEntity> capturedTagEntities = createTagCaptor.getValue();

        assertThat(createTagMapper.toEntitySet(requests)).isEqualTo(capturedTagEntities);
    }

    @DisplayName("Should get all tags")
    @ParameterizedTest
    @MethodSource("paginationDetails")
    public void shouldGetAllTags(Integer quantity, Integer page) {
        tagServiceImpl.getAllTags(quantity, page);

        ArgumentCaptor<Integer> quantityCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(tagDao).findAllSorted(quantityCaptor.capture(), pageCaptor.capture());

        Integer limit = quantityCaptor.getValue();
        Integer offset = pageCaptor.getValue();

        assertThat(limit).isEqualTo(quantity);
        assertThat((page - 1) * quantity).isEqualTo(offset);
    }

    @DisplayName("Should delete by id")
    @ParameterizedTest
    @MethodSource("idDetails")
    public void shouldDeleteById(Long id) {
        tagServiceImpl.deleteById(id);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        verify(tagDao).deleteById(idCaptor.capture());

        Long capturedId = idCaptor.getValue();

        assertThat(capturedId).isEqualTo(id);
    }


    private static Stream<Arguments> idDetails() {
        return LongStream.range(1, 51).mapToObj(Arguments::of);
    }

    private static Stream<Arguments> paginationDetails() {
        return Stream.of(
                Arguments.of(5, 1),
                Arguments.of(15, 3),
                Arguments.of(20, 2),
                Arguments.of(1, 1),
                Arguments.of(50, 1)
        );
    }

}