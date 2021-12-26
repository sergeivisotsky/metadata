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

import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link DateFormatter}.
 *
 * @author Sergei Visotsky
 */
public class DateFormatterTest {

    private final Formatter formatter = new DateFormatter();

    @Test
    public void shouldFormatWhereValueWithDateProperly() {
        //given
        final Date date = new Date(19700102);

        //when
        String result = formatter.formatWhereValue(date);

        //then
        assertEquals("to_date('1970-01-01','YYYY-MM-DD')", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithIllegalArgumentExceptionException() {
        //given
        final String date = "19700102";

        //when
        formatter.formatWhereValue(date);
    }
}