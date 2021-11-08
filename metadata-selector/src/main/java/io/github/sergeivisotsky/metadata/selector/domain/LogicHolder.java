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

package io.github.sergeivisotsky.metadata.selector.domain;

/**
 * Simple DTO to hold a pair of {@link LogicType} and
 * SQL statement or stored function/procedure method name.
 *
 * @author Sergei Visotsky
 */
public class LogicHolder {
    private final LogicType logicType;
    private final String sql;

    public LogicHolder(LogicType logicType, String sql) {
        this.logicType = logicType;
        this.sql = sql;
    }

    public LogicType getLogicType() {
        return logicType;
    }

    public String getSql() {
        return sql;
    }
}
