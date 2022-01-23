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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter;

import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link SQLDecimalFormatter}.
 *
 * @author Sergei Visotsky
 */
public class SQLDecimalFormatterTest {

    private final SQLFormatter formatter = new SQLDecimalFormatter();

    @Test
    public void shouldFormatWhereValueWithDecimalProperly() {
        //given
        BigDecimal amount = BigDecimal.valueOf(12300123123123123L);

        //when
        String result = formatter.formatWhereValue(amount);

        //then
        assertEquals("12300123123123123", result);
    }
}