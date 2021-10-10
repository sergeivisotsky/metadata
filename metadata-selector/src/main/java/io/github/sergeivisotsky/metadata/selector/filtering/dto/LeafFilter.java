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

package io.github.sergeivisotsky.metadata.selector.filtering.dto;

import io.github.sergeivisotsky.metadata.selector.domain.FieldType;

/**
 * @author Sergei Visotsky
 */
public class LeafFilter implements Filter {

    private final FieldType type;
    private final String attributeName;

    public LeafFilter(FieldType type, String attributeName) {
        this.type = type;
        this.attributeName = attributeName;
    }

    public FieldType getType() {
        return type;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
