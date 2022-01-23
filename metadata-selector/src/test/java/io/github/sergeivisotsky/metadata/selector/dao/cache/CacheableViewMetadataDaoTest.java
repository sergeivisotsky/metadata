package io.github.sergeivisotsky.metadata.selector.dao.cache;

import java.util.concurrent.TimeUnit;

import io.github.sergeivisotsky.metadata.selector.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CacheableViewMetadataDaoTest {

    @Mock
    private ViewMetadataDao viewMetadataDao;

    private CacheableViewMetadataDao cacheableViewMetadataDao;

    @Before
    public void setUp() {
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
    public void shouldGetFormMetadata() {
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