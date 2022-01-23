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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import io.github.sergeivisotsky.metadata.engine.domain.FieldType;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLDecimalFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLIntegerFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLStringFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.date.MySQLDateFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.datetime.MySQLDateTimeFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.time.MySQLTimeFormatter;
import org.apache.commons.lang3.StringUtils;

import static io.github.sergeivisotsky.metadata.engine.domain.FieldType.DATE;
import static io.github.sergeivisotsky.metadata.engine.domain.FieldType.DATETIME;
import static io.github.sergeivisotsky.metadata.engine.domain.FieldType.DECIMAL;
import static io.github.sergeivisotsky.metadata.engine.domain.FieldType.INTEGER;
import static io.github.sergeivisotsky.metadata.engine.domain.FieldType.STRING;
import static io.github.sergeivisotsky.metadata.engine.domain.FieldType.TIME;

/**
 * A PostgreSQL dialect to construct an SQL from template.
 *
 * @author Sergei Visotsky
 */
public class MySQLDialect extends BaseSQLDialect {

    private static final Map<FieldType, SQLFormatter> FORMATTER_MAP = ImmutableMap.<FieldType, SQLFormatter>builder()
            .put(TIME, new MySQLTimeFormatter())
            .put(DATETIME, new MySQLDateTimeFormatter())
            .put(DATE, new MySQLDateFormatter())
            .put(INTEGER, new SQLIntegerFormatter())
            .put(STRING, new SQLStringFormatter())
            .put(DECIMAL, new SQLDecimalFormatter())
            .build();

    public MySQLDialect() {
        super(FORMATTER_MAP);
    }

    @Override
    public String createSelectQuery(String sqlTemplate, ViewQuery query) {
        String sql = prepareSQL(sqlTemplate, query);

        String strLimit = "";
        if (query.getLimit() != null) {
            strLimit = " LIMIT " + (query.getLimit());
        }

        String strOffset = "";
        if (query.getOffset() != null) {
            strOffset = " OFFSET " + query.getOffset();
        }

        sql = StringUtils.replace(sql, LIMIT_PLACEHOLDER, strLimit);
        sql = StringUtils.replace(sql, OFFSET_PLACEHOLDER, strOffset);

        return sql;
    }
}
