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

package io.github.sergeivisotsky.metadata.engine.domain;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class ViewQueryResult {

    private final List<ViewField> fieldList;
    private final List<List<Object>> rowList;
    private final List<Order> orderList;
    private final Paging paging;

    ViewQueryResult(List<ViewField> fieldList, List<List<Object>> rowList,
                    List<Order> orderList, Paging paging) {
        this.fieldList = fieldList;
        this.rowList = rowList;
        this.orderList = orderList;
        this.paging = paging;
    }

    public static ViewQueryResultBuilder builder() {
        return new ViewQueryResultBuilder();
    }

    public List<ViewField> getFieldList() {
        return fieldList;
    }

    public List<List<Object>> getRowList() {
        return rowList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Paging getPaging() {
        return paging;
    }

    public static class ViewQueryResultBuilder {
        private List<ViewField> fieldList;
        private List<List<Object>> rowList;
        private List<Order> orderList;
        private Paging paging;

        ViewQueryResultBuilder() {
        }

        public ViewQueryResultBuilder fieldList(List<ViewField> fieldList) {
            this.fieldList = fieldList;
            return this;
        }

        public ViewQueryResultBuilder rowList(List<List<Object>> rowList) {
            this.rowList = rowList;
            return this;
        }

        public ViewQueryResultBuilder orderList(List<Order> orderList) {
            this.orderList = orderList;
            return this;
        }

        public ViewQueryResultBuilder paging(Paging paging) {
            this.paging = paging;
            return this;
        }

        public ViewQueryResult build() {
            return new ViewQueryResult(fieldList, rowList, orderList, paging);
        }
    }
}
