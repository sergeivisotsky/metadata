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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter;

import java.sql.Time;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link TimeUnitFormatter}.
 *
 * @author Sergei Visotsky
 */
class TimeUnitFormatterTest {

    @Test
    void shouldFormatDate() {
        //given
        final Date date = new Date(19700102);

        //when
        String actual = TimeUnitFormatter.formatDate(date);

        //then
        assertEquals("1970-01-01", actual);
    }

    @Test
    void shouldFailWithIllegalArgumentExceptionWhenIncorrectDateTypePassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //given
            final String date = "19700102";

            //when
            TimeUnitFormatter.formatDate(date);
        });
    }

    @Test
    void shouldFormatDateTime() {
        //given
        final Date date = new Date(19700102);

        //when
        String actual = TimeUnitFormatter.formatDateTime(date);

        //then
        assertEquals("1970-01-01T05:28:20.102", actual);
    }

    @Test
    void shouldFailWithIllegalArgumentExceptionWhenIncorrectDateTimeTypePassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //given
            final String date = "19700102";

            //when
            TimeUnitFormatter.formatDateTime(date);
        });
    }

    @Test
    void shouldFormatTime() {
        //given
        final Time time = Time.valueOf("12:00:03");

        //when
        String actual = TimeUnitFormatter.formatTime(time);

        //then
        assertEquals("110003", actual);
    }


    @Test
    void shouldFailWithIllegalArgumentExceptionWhenIncorrectTimeTypePassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //given
            final String timeAsString = "12:00:03";

            //when
            TimeUnitFormatter.formatTime(timeAsString);
        });
    }
}
