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
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link MSSQLDateTimeFormatter}.
 *
 * @author Sergei Visotsky
 */
public class MSSQLDateTimeFormatterTest {

    private final SQLFormatter formatter = new MSSQLDateTimeFormatter();

    @Test
    public void shouldFormatWhereValueWithDateProperly() {
        //given
        final Date date = new Date(19700102);

        //when
        String result = formatter.formatWhereValue(date);

        //then
        assertEquals("CONVERT(VARCHAR(10), '1970-01-01T05:28:20.102', 26)", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithIllegalArgumentException() {
        //given
        final String date = "19700102";

        //when
        formatter.formatWhereValue(date);
    }
}