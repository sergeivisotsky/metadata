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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.time;

import java.sql.Time;

import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link SQLTimeFormatter}.
 *
 * @author Sergei Visotsky
 */
class MySQLTimeFormatterTest {

    private final SQLFormatter formatter = new MySQLTimeFormatter();

    @Test
    void shouldFormatWhereValueWithTimeProperly() {
        //given
        final Time time = Time.valueOf("12:00:03");

        //when
        String result = formatter.formatWhereValue(time);

        //then
        assertEquals("STR_TO_DATE(110003', '%H:%i')", result);
    }

    @Test
    void shouldFailWithIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //given
            final String timeAsString = "12:00:03";

            //when
            formatter.formatWhereValue(timeAsString);
        });
    }
}