package io.github.sergeivisotsky.metadata.selector.dao.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.sergeivisotsky.metadata.selector.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.selector.domain.FieldType;
import io.github.sergeivisotsky.metadata.selector.domain.Language;
import io.github.sergeivisotsky.metadata.selector.domain.Order;
import io.github.sergeivisotsky.metadata.selector.domain.SortDirection;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.ViewQueryResult;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.LeafFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CacheableViewQueryDaoTest {

    @Mock
    private ViewQueryDao viewMetadataDao;

    private CacheableViewQueryDao cacheableViewMetadataDao;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        RootCacheConfigProperties rootCacheProps = new RootCacheConfigProperties();

        CacheConfigProperties cacheProps = new CacheConfigProperties();
        cacheProps.setExpireAfterAccess(123);
        cacheProps.setMaximumSize(12);
        cacheProps.setInitialCapacity(13);
        cacheProps.setExpirationAfterAccessUnits(TimeUnit.HOURS);

        rootCacheProps.setQuery(cacheProps);

        cacheableViewMetadataDao = new CacheableViewQueryDao(viewMetadataDao, rootCacheProps);
    }

    @Test
    public void shouldGetFormMetadata() {
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