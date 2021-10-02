/*
 * Copyright 2021 the original author or authors.
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

package io.github.sergeivisotsky.metadata.selector.dao.impl;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.sergeivisotsky.metadata.selector.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.keys.FormMetadataCacheKey;
import io.github.sergeivisotsky.metadata.selector.dao.keys.MetadataCacheKey;
import io.github.sergeivisotsky.metadata.selector.dao.keys.ViewMetadataCacheKey;
import io.github.sergeivisotsky.metadata.selector.domain.BaseMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.exception.DataAccessException;

/**
 * @author Sergei Visotsky
 */
public class CacheableMetadataDao implements ViewMetadataDao, FormMetadataDao {

    private final Cache<MetadataCacheKey, BaseMetadata> cache;
    private final ViewMetadataDao viewMetadata;
    private final FormMetadataDao formMetadataDao;

    public CacheableMetadataDao(ViewMetadataDao viewMetadata,
                                CacheConfigProperties cacheConfigProperties,
                                FormMetadataDao formMetadataDao) {
        this.viewMetadata = viewMetadata;
        cache = CacheBuilder.newBuilder()
                .initialCapacity(cacheConfigProperties.getInitialCapacity())
                .maximumSize(cacheConfigProperties.getMaximumSize())
                .expireAfterAccess(cacheConfigProperties.getExpireAfterAccess(),
                        cacheConfigProperties.getExpirationAfterAccessUnits())
                .build();
        this.formMetadataDao = formMetadataDao;
    }

    @Override
    public ViewMetadata getViewMetadata(String viewName, String lang) {
        try {
            ViewMetadataCacheKey cacheKey = new ViewMetadataCacheKey(viewName, lang);
            return (ViewMetadata) cache.get(cacheKey, () -> viewMetadata.getViewMetadata(viewName, lang));
        } catch (ExecutionException e) {
            throw new DataAccessException(e, "Unable to get a metadata from cache for " +
                    "viewName={} with lang={}", viewName, lang);
        }
    }

    @Override
    public FormMetadata getFormMetadata(String lang, String formName) {
        try {
            FormMetadataCacheKey cacheKey = new FormMetadataCacheKey(formName, lang);
            return (FormMetadata) cache.get(cacheKey, () -> formMetadataDao.getFormMetadata(formName, lang));
        } catch (ExecutionException e) {
            throw new DataAccessException(e, "Unable to get a metadata from cache for " +
                    "formName={} with lang={}", formName, lang);
        }
    }
}
