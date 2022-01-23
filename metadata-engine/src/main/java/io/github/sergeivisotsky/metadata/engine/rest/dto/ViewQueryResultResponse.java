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

package io.github.sergeivisotsky.metadata.engine.rest.dto;

import java.util.List;
import java.util.stream.Collectors;

import io.github.sergeivisotsky.metadata.engine.domain.Order;
import io.github.sergeivisotsky.metadata.engine.domain.Paging;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.domain.ViewQueryResult;

/**
 * @author Sergei Visotsky
 */
public class ViewQueryResultResponse {

    private final List<String> fieldNames;
    private final List<List<Object>> content;
    private final List<Order> orderList;
    private final Paging paging;

    public ViewQueryResultResponse(ViewQueryResult result) {
        this.fieldNames = result.getFieldList()
                .stream()
                .map(ViewField::getName)
                .collect(Collectors.toList());
        this.content = result.getRowList();
        this.orderList = result.getOrderList();
        this.paging = result.getPaging();
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public List<List<Object>> getContent() {
        return content;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Paging getPaging() {
        return paging;
    }
}
