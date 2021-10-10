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

import io.github.sergeivisotsky.metadata.selector.domain.filtering.Filter;
import io.github.sergeivisotsky.metadata.selector.domain.filtering.ViewQuery;
import io.github.sergeivisotsky.metadata.selector.exception.UrlParseException;

/**
 * @author Sergei Visotsky
 */
public class QueryFilterService {


    public ViewQuery constructViewQuery(Map<String, String[]> params) throws UrlParseException {
        return ViewQuery.builder()
                .filter(parseFilter(params))
                .offset(parseOffset(params))
                .limit(parseLimit(params))
                .build();
    }

    private Filter parseFilter(Map<String, String[]> params) throws UrlParseException {
        for (String paramKey : params.keySet()) {

            String param = getAndValidateParam(params, paramKey);
            // TODO: parse filtering

        }
        return null;
    }

    private Long parseOffset(Map<String, String[]> params) {
        return null;
    }

    private Integer parseLimit(Map<String, String[]> params) {
        return null;
    }

    private String getAndValidateParam(Map<String, String[]> params, String paramName) throws UrlParseException {
        String[] array = params.get(paramName);
        if (array == null || array.length == 0) {
            return null;
        }
        if (array.length > 1) {
            throw new UrlParseException("Only one parameter " + paramName + " is allowed in view query URL");
        }
        return array[0];
    }

}
