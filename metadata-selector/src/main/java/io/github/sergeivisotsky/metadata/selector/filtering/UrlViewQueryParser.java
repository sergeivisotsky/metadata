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

package io.github.sergeivisotsky.metadata.selector.filtering;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.exception.UrlParseException;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;

/**
 * @author Sergei Visotsky
 */
public interface UrlViewQueryParser {

    /**
     * Executes main view query parsing and construction logic.
     *
     * @param metadata view metadata to grab some data.
     * @param params   parameters from {@link HttpServletRequest}.
     * @return constructed view query.
     * @throws UrlParseException the following exception is risen in
     *                           case of any problem which occurs during
     *                           the URL parsing.
     */
    ViewQuery constructViewQuery(ViewMetadata metadata, Map<String, String[]> params) throws UrlParseException;
}
