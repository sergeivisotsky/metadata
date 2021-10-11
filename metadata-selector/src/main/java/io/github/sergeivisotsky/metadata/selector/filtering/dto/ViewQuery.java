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

package io.github.sergeivisotsky.metadata.selector.filtering.dto;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class ViewQuery {

    private final Filter filter;
    private final Long offset;
    private final Integer limit;
    private final List<SortFilter> sort;
    // TODO: Here should also be _sort

    ViewQuery(Filter filter, Long offset, Integer limit, List<SortFilter> sort) {
        this.filter = filter;
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public static ViewQueryBuilder builder() {
        return new ViewQueryBuilder();
    }

    public Filter getFilter() {
        return filter;
    }

    public Long getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public List<SortFilter> getSort() {
        return sort;
    }

    public static class ViewQueryBuilder {
        private Filter filter;
        private Long offset;
        private Integer limit;
        private List<SortFilter> sort;

        ViewQueryBuilder() {
        }

        public ViewQueryBuilder filter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public ViewQueryBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }

        public ViewQueryBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public ViewQueryBuilder sort(List<SortFilter> sort) {
            this.sort = sort;
            return this;
        }

        public ViewQuery build() {
            return new ViewQuery(filter, offset, limit, sort);
        }
    }
}
