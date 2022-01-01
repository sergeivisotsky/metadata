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

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Formats a time units rather {@link Date} or {@link Time} in UTC format.
 *
 * @author Sergei Visotsky
 */
public final class TimeUnitFormatter {

    private TimeUnitFormatter() {
        // noop
    }

    public static String formatDate(Object value) {
        throwTypeNotSupported(value);
        Date date = (Date) value;

        return getZonedDateTime(date)
                .toLocalDate()
                .toString();
    }

    public static String formatDateTime(Object value) {
        throwTypeNotSupported(value);
        Date date = (Date) value;

        return getZonedDateTime(date)
                .toLocalDateTime()
                .toString();
    }

    public static String formatTime(Object value) {
        if (!(value instanceof Time)) {
            throw new IllegalArgumentException("Formatter does not support " + value.getClass().getName()
                    + " instead it supports java.sql.Time");
        }
        Time date = (Time) value;

        LocalTime localDate = getZonedDateTime(date).toLocalTime();
        return Time.valueOf(localDate)
                .toString()
                .replaceAll(":", "");
    }

    private static void throwTypeNotSupported(Object value) {
        if (!(value instanceof Date)) {
            throw new IllegalArgumentException(
                    String.format("Formatter does not support %s instead it only supports java.util.Date",
                            value.getClass().getName())
            );
        }
    }

    private static ZonedDateTime getZonedDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.of("UTC"));
    }
}
