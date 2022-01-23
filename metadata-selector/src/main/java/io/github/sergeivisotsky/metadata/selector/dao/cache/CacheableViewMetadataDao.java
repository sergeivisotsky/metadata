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
import io.github.sergeivisotsky.metadata.selector.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.cache.key.ViewMetadataCacheKey;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.exception.MetadataStorageException;

/**
 * Cacheable View metadata DAO.
 *
 * @author Sergei Visotsky
 */
public class CacheableViewMetadataDao implements ViewMetadataDao {

    private final Cache<ViewMetadataCacheKey, ViewMetadata> cache;
    private final ViewMetadataDao viewMetadata;

    public CacheableViewMetadataDao(ViewMetadataDao viewMetadata,
                                    RootCacheConfigProperties cacheConfigProperties) {
        this.viewMetadata = viewMetadata;
        CacheConfigProperties viewConfigProps = cacheConfigProperties.getView();
        cache = CacheBuilder.newBuilder()
                .initialCapacity(viewConfigProps.getInitialCapacity())
                .maximumSize(viewConfigProps.getMaximumSize())
                .expireAfterAccess(viewConfigProps.getExpireAfterAccess(),
                        viewConfigProps.getExpirationAfterAccessUnits())
                .build();
    }

    @Override
    public ViewMetadata getViewMetadata(String viewName, String lang) {
        try {
            ViewMetadataCacheKey cacheKey = new ViewMetadataCacheKey(viewName, lang);
            return cache.get(cacheKey, () -> viewMetadata.getViewMetadata(viewName, lang));
        } catch (ExecutionException e) {
            throw new MetadataStorageException(e, "Unable to get a metadata from cache for " +
                    "viewName={} with lang={}", viewName, lang);
        }
    }
}
