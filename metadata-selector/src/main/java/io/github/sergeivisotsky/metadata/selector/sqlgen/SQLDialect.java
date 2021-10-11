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
import org.springframework.context.annotation.Bean;

/**
 * A public API which represents an  SQL dialect related functionality
 * which should be custom and configured as a Spring's {@link Bean}
 * as per need of specific project which uses a particular database.
 *
 * @author Sergei Visotsky
 */
public interface SQLDialect {

    /**
     * Creates a SELECT query from the given template by replacing all parts needed.
     *
     * @param sqlTemplate SQL template which should be adjusted per needs.
     * @param query       view query to extract needed filters etc.
     * @return an SQL query which can be used.
     */
    String createSelectQuery(String sqlTemplate, ViewQuery query);

    /**
     * Get a value from result set.
     *
     * @param rs        Result set to get value from.
     * @param viewField needed view field.
     * @return value from result set.
     */
    Object getValueFromResultSet(ResultSet rs, ViewField viewField) throws SQLException;
}
