package com.epam.business.service;

import com.epam.business.mapper.dto.GiftCertificateMapper;
import com.epam.business.mapper.requestMapper.CreateGiftCertificateMapper;
import com.epam.business.mapper.requestMapper.UpdateGiftCertificateMapper;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.CreateTagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.impl.GiftCertificateServiceImpl;
import com.epam.business.service.provider.CreateGiftCertificateProvider;
import com.epam.business.service.provider.CreateTagProvider;
import com.epam.business.service.provider.UpdateGiftCertificateProvider;
import com.epam.data.dao.GiftCertificateDao;
import com.epam.data.model.entity.GiftCertificateEntity;
import com.epam.data.model.entity.TagEntity;
import com.epam.data.model.enums.SortType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagService tagService;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private CreateGiftCertificateMapper createGiftCertificateMapper;

    @Mock
    private UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    @BeforeEach
    void setUp() {
        assertThat(giftCertificateServiceImpl).isNotNull();
    }

    @DisplayName("Testing create method")
    @ParameterizedTest
    @ArgumentsSource(CreateGiftCertificateProvider.class)
    public void create(CreateGiftCertificateRequest request) {
        when(createGiftCertificateMapper.toEntity(request)).then(invocation -> GiftCertificateEntity.builder()
                .name(request.getName())
                .createDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0))
                .description(request.getDescription())
                .duration(LocalDateTime.of(2022, 1, 1, 0, 0, 0).plusDays(request.getDuration()))
                .price(request.getPrice())
                .tags(request.getTags().stream()
                        .map(tagRequest -> new TagEntity(tagRequest.getName()))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build());

        giftCertificateServiceImpl.create(request);

        ArgumentCaptor<GiftCertificateEntity> captor = ArgumentCaptor.forClass(GiftCertificateEntity.class);

        verify(giftCertificateDao).save(captor.capture());

        GiftCertificateEntity captured = captor.getValue();

        assertThat(captured).usingRecursiveComparison().isEqualTo(createGiftCertificateMapper.toEntity(request));
    }

    @DisplayName("Testing getByTag method")
    @ParameterizedTest
    @ArgumentsSource(CreateTagProvider.class)
    public void getByTag(LinkedHashSet<CreateTagRequest> createTagRequests) {
        Random random = new Random();
        String tag = createTagRequests.stream().findAny().get().getName();
        Integer page = 1 + random.nextInt(10);
        Integer quantity = 5;

        SortType sortType = SortType.values()[random.nextInt(3)];

        ArgumentCaptor<String> tagCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<SortType> sortTypeCaptor = ArgumentCaptor.forClass(SortType.class);

        giftCertificateServiceImpl.getByTag(tag, quantity, page, sortType);

        verify(giftCertificateDao).findByTag(tagCaptor.capture(), limitCaptor.capture(), offsetCaptor.capture(), sortTypeCaptor.capture());

        String capturedTag = tagCaptor.getValue();
        Integer capturedLimit = limitCaptor.getValue();
        Integer capturedOffset = offsetCaptor.getValue();
        SortType capturedSortType = sortTypeCaptor.getValue();

        assertThat(capturedLimit).isEqualTo(quantity);
        assertThat(capturedOffset).isEqualTo((page - 1) * quantity);
        assertThat(capturedTag).isEqualTo(tag);
        assertThat(capturedSortType).isEqualTo(sortType);
    }

    @DisplayName("Testing deleteById method")
    @ParameterizedTest
    @MethodSource("idDetails")
    void deleteById(Long id) {
        giftCertificateServiceImpl.deleteById(id);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        verify(giftCertificateDao).deleteById(idCaptor.capture());

        Long capturedId = idCaptor.getValue();

        assertThat(capturedId).isEqualTo(id);
    }

    @DisplayName("Testing update method")
    @ParameterizedTest
    @ArgumentsSource(UpdateGiftCertificateProvider.class)
    public void update(Long id, UpdateGiftCertificateRequest request) {
        when(updateGiftCertificateMapper.toEntity(request)).thenReturn(GiftCertificateEntity.builder()
                .duration(LocalDateTime.of(2022, 2, 2, 0, 0, 0).plusDays(request.getDuration()))
                .price(request.getPrice())
                .name(request.getName())
                .description(request.getDescription())
                .build());

        giftCertificateServiceImpl.update(id, request);

        ArgumentCaptor<GiftCertificateEntity> entityCaptor = ArgumentCaptor.forClass(GiftCertificateEntity.class);

        verify(giftCertificateDao).save(entityCaptor.capture());

        GiftCertificateEntity captured = entityCaptor.getValue();

        assertThat(captured).isEqualTo(updateGiftCertificateMapper.toEntity(request));
    }

    private static Stream<Arguments> idDetails() {
        return LongStream.range(1, 51).mapToObj(Arguments::of);
    }

}