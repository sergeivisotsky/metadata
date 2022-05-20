/*
 * Copyright 2022 the original author or authors.
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
package io.github.sergeivisotsky.metadata.engine.graphql;

import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.UrlParseException;
import io.github.sergeivisotsky.metadata.engine.filtering.ViewQueryParser;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;

/**
 * Pageable graphQL parser.
 *
 * @author Sergejs Visockis
 */
public class PageableGraphQLParser extends ViewQueryParser {


    @Override
    public ViewQuery constructViewQuery(ViewMetadata metadata, Map<String, String[]> params) throws UrlParseException {
        return ViewQuery.builder()
                .offset(parseOffset(params))
                .limit(parseLimit(params))
                .build();
    }
}
