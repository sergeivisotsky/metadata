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

package io.github.sergeivisotsky.metadata.selector.sqlgen;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;

/**
 * @author Sergei Visotsky
 */
public class PostgreSQLDialect implements SQLDialect {

    @Override
    public String createSelectQuery(String sqlTemplate, ViewQuery query) {
        // TODO: Implement
        return null;
    }

    @Override
    public Object getValueFromResultSet(ResultSet rs, ViewField field) throws SQLException {
        switch (field.getFieldType()) {
            case DATE:
                long intValue = rs.getLong(field.getName());
                return rs.wasNull() ? null : intValue;
            case TIME:
                return rs.getTime(field.getName());
            case STRING:
                return rs.getString(field.getName());
            case DECIMAL:
                return rs.getBigDecimal(field.getName());
            case INTEGER:
                return rs.getInt(field.getName());
            case DATETIME:
                return rs.getTimestamp(field.getName());
        }
        throw new IllegalArgumentException("Field " + field.getName() +
                " of type " + field.getFieldType() + " is not supported");
    }
}
