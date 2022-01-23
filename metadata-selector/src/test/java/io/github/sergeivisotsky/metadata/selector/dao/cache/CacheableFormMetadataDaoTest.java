package io.github.sergeivisotsky.metadata.selector.dao.cache;

import java.util.concurrent.TimeUnit;

import io.github.sergeivisotsky.metadata.selector.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormMetadata;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CacheableFormMetadataDaoTest {

    @Mock
    private FormMetadataDao formMetadataDao;

    private CacheableFormMetadataDao cacheableFormMetadataDao;

    @Before
    public void setUp() {
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
    public void shouldGetFormMetadata() {
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