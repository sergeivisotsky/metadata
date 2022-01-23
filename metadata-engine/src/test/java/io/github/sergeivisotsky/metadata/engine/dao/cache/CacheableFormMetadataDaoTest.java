package io.github.sergeivisotsky.metadata.engine.dao.cache;

import java.util.concurrent.TimeUnit;

import io.github.sergeivisotsky.metadata.engine.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CacheableFormMetadataDaoTest {

    @Mock
    private FormMetadataDao formMetadataDao;

    private CacheableFormMetadataDao cacheableFormMetadataDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RootCacheConfigProperties rootCacheProps = new RootCacheConfigProperties();

        CacheConfigProperties cacheProps = new CacheConfigProperties();
        cacheProps.setExpireAfterAccess(123);
        cacheProps.setMaximumSize(12);
        cacheProps.setInitialCapacity(13);
        cacheProps.setExpirationAfterAccessUnits(TimeUnit.HOURS);

        rootCacheProps.setForm(cacheProps);

        cacheableFormMetadataDao = new CacheableFormMetadataDao(formMetadataDao, rootCacheProps);
    }

    @Test
    void shouldGetFormMetadata() {
        //given
        FormMetadata expectedMetadata = new FormMetadata();
        expectedMetadata.setName("someFormName");
        when(formMetadataDao.getFormMetadata(anyString(), anyString())).thenReturn(expectedMetadata);

        //when
        FormMetadata actualMetadata = cacheableFormMetadataDao.getFormMetadata("EN", "main");

        //then
        verify(formMetadataDao).getFormMetadata(anyString(), anyString());
        assertEquals(expectedMetadata.getName(), actualMetadata.getName());
    }

}