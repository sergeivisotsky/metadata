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

package io.github.sergeivisotsky.metadata.selector.mapper;

import java.sql.ResultSet;

/**
 * An interface to be implemented by mappers from {@link ResultSet} to DTO.
 *
 * @param <T> type to extract a {@link ResultSet} to.
 * @author Sergei Visotsky
 */
public interface MetadataMapper<T> {

    /**
     * Get an SQL or stored function containing a new fields added during customization.
     *
     * @return SQL or function to execute.
     */
    String getSql();

    /**
     * Maps an extracted metadata from {@link ResultSet} to a Java DTO.
     *
     * @param rs database result set.
     * @return resulting metadata aggregate DTO.
     */
    T map(ResultSet rs);
}
