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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import io.github.sergeivisotsky.metadata.selector.domain.FieldType;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.SelectParser;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.DATE;
import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.DATETIME;
import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.DECIMAL;
import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.INTEGER;
import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.STRING;
import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.TIME;

/**
 * @author Sergei Visotsky
 */
public class PostgreSQLDialect implements SQLDialect {

    private static final Map<FieldType, Formatter> FORMATTER_MAP = ImmutableMap.<FieldType, Formatter>builder()
            .put(TIME, new TimeFormatter())
            .put(DATETIME, new DateTimeFormatter())
            .put(DATE, new DateFormatter())
            .put(INTEGER, new IntegerFormatter())
            .put(STRING, new StringFormatter())
            .put(DECIMAL, new DecimalFormatter())
            .build();

    private SelectParser selectParser;

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

    @Autowired
    public void setSelectParser(SelectParser selectParser) {
        this.selectParser = selectParser;
    }
}
