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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen.dialect;

import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * A PostgreSQL dialect to construct an SQL from template.
 *
 * @author Sergei Visotsky
 */
public class PostgreSQLDialect extends AbstractSQLDialect {

    @Override
    @SuppressWarnings("Duplicates") // really same as an Oracle in these terms,
    // as SQL support will grow will need a custom Oracle specific things.
    public String createSelectQuery(String sqlTemplate, ViewQuery query) {
        String sql = prepareSQL(sqlTemplate, query);

        String strOffset = "";
        if (query.getOffset() != null) {
            strOffset = " OFFSET " + query.getOffset() + " ROWS ";
        }

        String strLimit = "";
        if (query.getLimit() != null) {
            strLimit = " FETCH NEXT " + (query.getLimit()) + " ROWS ONLY";
        }

        sql = StringUtils.replace(sql, OFFSET_PLACEHOLDER, strOffset);
        sql = StringUtils.replace(sql, LIMIT_PLACEHOLDER, strLimit);

        return sql;
    }
}
