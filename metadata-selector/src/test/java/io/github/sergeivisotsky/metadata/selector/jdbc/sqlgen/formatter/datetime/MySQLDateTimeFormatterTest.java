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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen.formatter.datetime;

import java.util.Date;

import io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen.formatter.SQLFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link MySQLDateTimeFormatter}.
 *
 * @author Sergei Visotsky
 */
class MySQLDateTimeFormatterTest {

    private final SQLFormatter formatter = new MySQLDateTimeFormatter();

    @Test
    void shouldFormatWhereValueWithDateProperly() {
        //given
        final Date date = new Date(19700102);

        //when
        String result = formatter.formatWhereValue(date);

        //then
        assertEquals("STR_TO_DATE(1970-01-01T05:28:20.102', '%Y-%m-%d %H:%i:%s')", result);
    }

    @Test
    void shouldFailWithIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //given
            final String date = "19700102";

            //when
            formatter.formatWhereValue(date);
        });
    }
}