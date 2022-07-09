package com.epam.business.service;

import com.epam.business.mapper.requestMapper.CreateTagMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.request.CreateTagRequest;
import com.epam.business.service.impl.TagServiceImpl;
import com.epam.business.service.provider.CreateTagProvider;
import com.epam.data.model.entity.TagEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;


/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TagServiceTest {

    @Mock
    private TagService tagService;

    @BeforeEach
    public void beforeAll() {
        Assertions.assertThat(tagService).isNotNull();
    }


    @DisplayName("Should create tag")
    @ParameterizedTest
    @ArgumentsSource(CreateTagProvider.class)
    public void shouldCreateTag(Set<CreateTagRequest> requests) {
        Assertions.assertThat(requests).isNotNull();
        Assertions.assertThat(requests.size()).isGreaterThan(0);

        when(tagService.create(requests)).thenReturn(getTagEntitySet(requests));

    }

    private Set<TagDto> getTagEntitySet(Set<CreateTagRequest> requests) {
        AtomicLong counter = new AtomicLong(1);
        return requests.stream().map(request -> TagDto.builder()
                .id(counter.getAndIncrement())
                .name(request.getName())
                .build()
        ).collect(Collectors.toSet());
    }

    @Test
    void getAllTags() {
    }

    @Test
    void deleteById() {
    }
}