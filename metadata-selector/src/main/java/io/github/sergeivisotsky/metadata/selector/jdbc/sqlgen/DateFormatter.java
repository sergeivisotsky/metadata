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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.Nonnull;

/**
 * @author Sergei Visotsky
 */
public class DateFormatter implements Formatter {

    @Override
    public String formatWhereValue(@Nonnull Object value) {
        return "to_date('" + formatDate(value) + "','YYYY-MM-DD')";
    }

    private String formatDate(Object value) {
        if (!(value instanceof Date)) {
            throw new IllegalArgumentException("Formatter does not support " +
                    value.getClass().getName() + "instead it only supports java.util.Date");
        }

        Date date = (Date) value;
        LocalDate localDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();
        return localDate.toString();
    }
}
