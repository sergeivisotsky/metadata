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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.selector.domain.Paging;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.ViewQueryResult;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen.dialect.SQLDialect;

/**
 * @author Sergei Visotsky
 */
public class ViewQueryDaoImpl extends AbstractMetadataDao implements ViewQueryDao {

    private final SQLDialect sqlDialect;

    public ViewQueryDaoImpl(SQLDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewQueryResult query(ViewMetadata metadata, ViewQuery query) {
        String sqlTemplate = metadata.getDefinition();

        ViewQuery plusOneRowQuery = query.plusOneRowQuery();
        String sql = sqlDialect.createSelectQuery(sqlTemplate, plusOneRowQuery);

        List<ViewField> fieldList = metadata.getViewField();
        List<List<Object>> rowList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            List<Object> row = new ArrayList<>(fieldList.size());
            for (ViewField field : fieldList) {
                Object value = sqlDialect.getValueFromResultSet(rs, field);
                row.add(value);
            }
            return row;
        });

        boolean hasMoreElements = false;
        if (plusOneRowQuery.getLimit() != null && rowList.size() == plusOneRowQuery.getLimit()) {
            rowList.remove(rowList.size() - 1);
            hasMoreElements = true;
        }

        return ViewQueryResult.builder()
                .fieldList(fieldList)
                .rowList(rowList)
                .orderList(plusOneRowQuery.getOrderList())
                .paging(Paging.builder()
                        .offset(query.getOffset())
                        .hasMoreElements(hasMoreElements)
                        .totalElements(rowList.stream().mapToLong(Collection::size).sum())
                        .build())
                .build();
    }
}
