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
package io.github.sergeivisotsky.metadata.selector.dao.cache;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.sergeivisotsky.metadata.selector.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.cache.key.FormMetadataCacheKey;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.exception.MetadataStorageException;

/**
 * Cacheable Form metadata DAO.
 *
 * @author Sergei Visotsky
 */
public class CacheableFormMetadataDao implements FormMetadataDao {

    private final Cache<FormMetadataCacheKey, FormMetadata> cache;
    private final FormMetadataDao formMetadataDao;

    public CacheableFormMetadataDao(FormMetadataDao formMetadataDao,
                                    RootCacheConfigProperties cacheConfigProperties) {
        this.formMetadataDao = formMetadataDao;
        CacheConfigProperties formConfigProps = cacheConfigProperties.getForm();
        cache = CacheBuilder.newBuilder()
                .initialCapacity(formConfigProps.getInitialCapacity())
                .maximumSize(formConfigProps.getMaximumSize())
                .expireAfterAccess(formConfigProps.getExpireAfterAccess(),
                        formConfigProps.getExpirationAfterAccessUnits())
                .build();
    }

    @Override
    public FormMetadata getFormMetadata(String lang, String formName) {
        try {
            FormMetadataCacheKey cacheKey = new FormMetadataCacheKey(formName, lang);
            return cache.get(cacheKey, () -> formMetadataDao.getFormMetadata(formName, lang));
        } catch (ExecutionException e) {
            throw new MetadataStorageException(e, "Unable to get a metadata from cache for " +
                    "formName={} with lang={}", formName, lang);
        }
    }
}
