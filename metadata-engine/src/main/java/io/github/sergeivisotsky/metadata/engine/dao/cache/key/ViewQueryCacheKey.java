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
package io.github.sergeivisotsky.metadata.engine.dao.cache.key;

import java.util.Objects;

import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;

/**
 * View Query cache key.
 *
 * @author Sergei Visotsky
 */
public class ViewQueryCacheKey {

    private final String viewName;
    private final String lang;
    private final ViewQuery query;

    public ViewQueryCacheKey(String viewName, String lang, ViewQuery query) {
        this.viewName = viewName;
        this.lang = lang;
        this.query = query;
    }

    public String getViewName() {
        return viewName;
    }

    public String getLang() {
        return lang;
    }

    public ViewQuery getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ViewQueryCacheKey that = (ViewQueryCacheKey) obj;
        return viewName.equals(that.viewName) && lang.equals(that.lang) && query.equals(that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName, lang, query);
    }

}
