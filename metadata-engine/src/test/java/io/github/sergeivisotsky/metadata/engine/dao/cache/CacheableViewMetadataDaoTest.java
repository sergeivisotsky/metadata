package io.github.sergeivisotsky.metadata.engine.dao.cache;

import java.util.concurrent.TimeUnit;

import io.github.sergeivisotsky.metadata.engine.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CacheableViewMetadataDaoTest {

    @Mock
    private ViewMetadataDao viewMetadataDao;

    private CacheableViewMetadataDao cacheableViewMetadataDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RootCacheConfigProperties rootCacheProps = new RootCacheConfigProperties();

        CacheConfigProperties cacheProps = new CacheConfigProperties();
        cacheProps.setExpireAfterAccess(123);
        cacheProps.setMaximumSize(12);
        cacheProps.setInitialCapacity(13);
        cacheProps.setExpirationAfterAccessUnits(TimeUnit.HOURS);

        rootCacheProps.setView(cacheProps);

        cacheableViewMetadataDao = new CacheableViewMetadataDao(viewMetadataDao, rootCacheProps);
    }

    @Test
    void shouldGetFormMetadata() {
        //given
        ViewMetadata expectedMetadata = new ViewMetadata();
        expectedMetadata.setDescription("some description");
        when(viewMetadataDao.getViewMetadata(anyString(), anyString())).thenReturn(expectedMetadata);

        //when
        ViewMetadata actualMetadata = cacheableViewMetadataDao.getViewMetadata("EN", "main");

        //then
        verify(viewMetadataDao).getViewMetadata(anyString(), anyString());
        assertEquals(expectedMetadata.getDescription(), actualMetadata.getDescription());
    }

}