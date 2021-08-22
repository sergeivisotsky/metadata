package org.sergei.metadata.selector.dao.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.sergei.metadata.selector.dao.MetadataDao;
import org.sergei.metadata.selector.dto.FormMetadata;
import org.sergei.metadata.selector.exception.DataAccessException;

public class CacheableMetadataDao implements MetadataDao {

    private final Cache<MetadataCacheKey, FormMetadata> cache;
    private final MetadataDao metadataDao;

    public CacheableMetadataDao(MetadataDao metadataDao) {
        this.metadataDao = metadataDao;
        cache = CacheBuilder.newBuilder()
                .initialCapacity(8000)
                .expireAfterAccess(8, TimeUnit.HOURS)
                .build();
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        try {
            MetadataCacheKey cacheKey = new MetadataCacheKey(formName, lang);
            return cache.get(cacheKey, () -> metadataDao.getFormMetadata(formName, lang));
        } catch (ExecutionException e) {
            throw new DataAccessException(e, "Unable to get a metadata from cache for " +
                    "formName={} with lang={}", formName, lang);
        }
    }

    private static final class MetadataCacheKey {
        private final String formName;
        private final String lang;

        private MetadataCacheKey(String formName, String lang) {
            this.formName = formName;
            this.lang = lang;
        }

        public String getFormName() {
            return formName;
        }

        public String getLang() {
            return lang;
        }
    }
}
