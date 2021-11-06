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

package io.github.sergeivisotsky.metadata.selector.dao;

import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.ViewQueryResult;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;

/**
 * Main DAO class to perform a view metadata query.
 *
 * @author Sergei Visotsky
 */
public interface ViewQueryDao {

    /**
     * Executes a metadata retrieval query to fetch an initial content for a metadata.
     *
     * @param metadata to perform a query for.
     * @param query    query with soring, ordering, filtering, etc.
     * @return query result set.
     */
    ViewQueryResult query(ViewMetadata metadata, ViewQuery query);

}
