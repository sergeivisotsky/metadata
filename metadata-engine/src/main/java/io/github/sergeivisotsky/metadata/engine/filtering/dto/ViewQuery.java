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

package io.github.sergeivisotsky.metadata.engine.filtering.dto;

import java.util.List;
import java.util.Objects;

import io.github.sergeivisotsky.metadata.engine.domain.Order;

/**
 * @author Sergei Visotsky
 */
public class ViewQuery {

    private final Filter filter;
    private final Long offset;
    private final Integer limit;
    private final List<Order> orderList;

    ViewQuery(Filter filter, Long offset, Integer limit, List<Order> orderList) {
        this.filter = filter;
        this.offset = offset;
        this.limit = limit;
        this.orderList = orderList;
    }

    public static ViewQueryBuilder builder() {
        return new ViewQueryBuilder();
    }

    public ViewQuery plusOneRowQuery() {
        return ViewQuery.builder()
                .filter(getFilter())
                .limit(getLimit() == null ? null : getLimit() + 1)
                .offset(getOffset())
                .orderList(getOrderList())
                .build();
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

    public List<Order> getOrderList() {
        return orderList;
    }

    public static class ViewQueryBuilder {
        private Filter filter;
        private Long offset;
        private Integer limit;
        private List<Order> orderList;

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

        public ViewQueryBuilder orderList(List<Order> orderList) {
            this.orderList = orderList;
            return this;
        }

        public ViewQuery build() {
            return new ViewQuery(filter, offset, limit, orderList);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ViewQuery viewQuery = (ViewQuery) obj;
        return nullSafeEquals(filter, viewQuery.filter) &&
                nullSafeEquals(offset, viewQuery.offset) &&
                nullSafeEquals(limit, viewQuery.limit) &&
                nullSafeEquals(orderList, viewQuery.orderList);
    }

    private static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, offset, limit, orderList);
    }
}
