/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.sergeivisotsky.metadata.engine.dao.cache;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.sergeivisotsky.metadata.engine.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.cache.key.FormMetadataCacheKey;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;

/**
 * Cacheable Form metadata DAO.
 *
 * @author Sergei Visotsky
 */
public class CacheableFormMetadataDao implements FormMetadataDao {

    private final Cache<FormMetadataCacheKey, FormMetadata> cache;
    private final FormMetadataDao formMetadataDao;

    public CacheableFormMetadataDao(FormMetadataDao formMetadataDao,
                                    CacheConfigProperties formCacheProperties) {
        this.formMetadataDao = formMetadataDao;
        cache = CacheBuilder.newBuilder()
                .initialCapacity(formCacheProperties.getInitialCapacity())
                .maximumSize(formCacheProperties.getMaximumSize())
                .expireAfterAccess(formCacheProperties.getExpireAfterAccess(),
                        formCacheProperties.getExpirationAfterAccessUnits())
                .build();
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        try {
            FormMetadataCacheKey cacheKey = new FormMetadataCacheKey(formName, lang);
            return cache.get(cacheKey, () -> formMetadataDao.getFormMetadata(formName, lang));
        } catch (ExecutionException e) {
            throw new MetadataStorageException(e, "Unable to get a metadata from cache for " +
                    "formName={} with lang={}", formName, lang);
        }
    }
}
