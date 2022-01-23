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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.date;

import javax.annotation.Nonnull;

import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLFormatter;

import static io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.TimeUnitFormatter.formatDate;

/**
 * MySQL's dialect Date formatter according.
 *
 * @author Sergei Visotsky
 */
public class MySQLDateFormatter implements SQLFormatter {

    @Override
    public String formatWhereValue(@Nonnull Object value) {
        return "STR_TO_DATE('" + formatDate(value) + "', '%d,%m,%Y')";
    }
}
