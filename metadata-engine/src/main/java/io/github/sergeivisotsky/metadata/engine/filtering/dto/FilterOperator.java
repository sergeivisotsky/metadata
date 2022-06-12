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

package io.github.sergeivisotsky.metadata.engine.filtering.dto;

import java.util.stream.Stream;

import io.github.sergeivisotsky.metadata.engine.exception.ViewQueryParseException;

/**
 * @author Sergei Visotsky
 */
public enum FilterOperator {

    BETWEEN("bw", BetweenFilter.class),
    EQUALS("eq", GreaterFilter.class),
    LIKE("lk", LikeFilter.class),
    LESS("ls", LessFilter.class),
    GREATER("gt", GreaterFilter.class);

    private final String code;
    private final Class<? extends LeafFilter> filter;

    FilterOperator(String code, Class<? extends LeafFilter> filter) {
        this.code = code;
        this.filter = filter;
    }

    public String getCode() {
        return code;
    }

    public Class<? extends LeafFilter> getFilter() {
        return filter;
    }

    public static FilterOperator getByCode(String code) throws ViewQueryParseException {
        return Stream.of(values())
                .filter(op -> code.equals(op.code))
                .findFirst()
                .orElseThrow(() -> new ViewQueryParseException("Filter operator provided by code " + code + " is not found"));
    }
}
