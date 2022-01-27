package io.github.sergeivisotsky.metadata.engine.dao.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.sergeivisotsky.metadata.engine.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.engine.domain.FieldType;
import io.github.sergeivisotsky.metadata.engine.domain.Language;
import io.github.sergeivisotsky.metadata.engine.domain.Order;
import io.github.sergeivisotsky.metadata.engine.domain.SortDirection;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.ViewQueryResult;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.LeafFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CacheableViewQueryDaoTest {

    @Mock
    private ViewQueryDao viewMetadataDao;

    private CacheableViewQueryDao cacheableViewMetadataDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        CacheConfigProperties cacheProps = new CacheConfigProperties();
        cacheProps.setExpireAfterAccess(123);
        cacheProps.setMaximumSize(12);
        cacheProps.setInitialCapacity(13);
        cacheProps.setExpirationAfterAccessUnits(TimeUnit.HOURS);

        cacheableViewMetadataDao = new CacheableViewQueryDao(viewMetadataDao, cacheProps);
    }

    @Test
    void shouldGetFormMetadata() {
        //given
        ViewField field = new ViewField();
        field.setName("someField");
        ViewQueryResult query = ViewQueryResult.builder()
                .fieldList(List.of(field))
                .build();
        when(viewMetadataDao.query(any(), any())).thenReturn(query);

        //when
        ViewMetadata metadata = new ViewMetadata();
        metadata.setLang(Language.EN);
        metadata.setViewName("someName");
        metadata.setViewField(List.of(field));
        LeafFilter leafFilter = new LeafFilter(FieldType.DATE, "someName");
        ViewQuery queryParam = ViewQuery.builder()
                .offset(3L)
                .limit(13)
                .filter(new AndFilter(leafFilter, leafFilter))
                .orderList(List.of(new Order("someField", SortDirection.ASC)))
                .build();
        ViewQueryResult actualMetadata = cacheableViewMetadataDao.query(metadata, queryParam);

        //then
        verify(viewMetadataDao).query(any(ViewMetadata.class), any(ViewQuery.class));
        assertEquals(query.getFieldList().get(0).getName(), actualMetadata.getFieldList().get(0).getName());
    }

}