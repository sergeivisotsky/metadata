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

import io.github.sergeivisotsky.metadata.engine.domain.FieldType;

/**
 * @author Sergei Visotsky
 */
public class BetweenFilter extends LeafFilter {

    private final Object from;
    private final Object to;

    public BetweenFilter(FieldType type, String attributeName, Object from, Object to) {
        super(type, attributeName);
        this.from = from;
        this.to = to;
    }

    public Object getFrom() {
        return from;
    }

    public Object getTo() {
        return to;
    }
}
