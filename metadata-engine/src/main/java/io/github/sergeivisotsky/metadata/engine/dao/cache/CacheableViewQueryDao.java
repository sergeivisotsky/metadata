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
import io.github.sergeivisotsky.metadata.engine.config.properties.RootCacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.engine.dao.cache.key.ViewQueryCacheKey;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.ViewQueryResult;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;

/**
 * Cacheable view query cache.
 *
 * @author Sergei Visotsky
 */
public class CacheableViewQueryDao implements ViewQueryDao {

    private final Cache<ViewQueryCacheKey, ViewQueryResult> cache;
    private final ViewQueryDao queryDao;

    public CacheableViewQueryDao(ViewQueryDao queryDao, RootCacheConfigProperties cacheConfigProperties) {
        this.queryDao = queryDao;
        CacheConfigProperties queryProperties = cacheConfigProperties.getQuery();
        cache = CacheBuilder.newBuilder()
                .initialCapacity(queryProperties.getInitialCapacity())
                .maximumSize(queryProperties.getMaximumSize())
                .expireAfterAccess(queryProperties.getExpireAfterAccess(),
                        queryProperties.getExpirationAfterAccessUnits())
                .build();
    }

    @Override
    public ViewQueryResult query(ViewMetadata metadata, ViewQuery query) {
        String viewName = metadata.getViewName();
        String lang = metadata.getLang().name();
        try {
            ViewQueryCacheKey cacheKey = new ViewQueryCacheKey(viewName, lang, query);
            return cache.get(cacheKey, () -> queryDao.query(metadata,query));
        } catch (ExecutionException e) {
            throw new MetadataStorageException(e, "Unable to get a view query from cache for " +
                    "metadata related to viewName={} with lang={}", viewName, lang);
        }
    }
}
