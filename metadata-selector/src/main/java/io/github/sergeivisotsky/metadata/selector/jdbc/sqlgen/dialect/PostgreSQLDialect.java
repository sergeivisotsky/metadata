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

import java.util.Map;

import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * A PostgreSQL dialect to construct an SQL from template.
 *
 * @author Sergei Visotsky
 */
public class PostgreSQLDialect extends AbstractSQLDialect {

    @Override
    public String createSelectQuery(String sqlTemplate, ViewQuery query) {
        String sql = sqlTemplate;

        Map<String, String> fieldNameToFilterColumnMap = extractFilterColumnMap(sql);
        String strFilter = createFilterClause(query.getFilter(), fieldNameToFilterColumnMap);
        String strOrder = createOrderClause(query.getOrderList(), fieldNameToFilterColumnMap);

        sql = StringUtils.replace(sql, "{filter}", " AND " + strFilter);
        sql = StringUtils.replace(sql, "{filter}", "");
        sql = StringUtils.replace(sql, "{order}", strOrder);

        String strOffset = "";
        if (query.getOffset() != null) {
            strOffset = " OFFSET " + query.getOffset() + " ROWS ";
        }

        String strLimit = "";
        if (query.getLimit() != null) {
            strLimit = " FETCH NEXT " + (query.getLimit()) + " ROWS ONLY";
        }

        sql = StringUtils.replace(sql, "{offset}", strOffset);
        sql = StringUtils.replace(sql, "{limit}", strLimit);

        return sql;
    }
}
