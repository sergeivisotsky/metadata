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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit test for {@link PrimitiveSelectParser}.
 */
public class PrimitiveSelectParserTest {

    private final SelectParser selectParser = new PrimitiveSelectParser();

    @Test
    public void shouldParseSelect() throws SQLParseException {
        //given
        final String parseSelect = "SELECT t.some_first_column AS first,\n" +
                "t.some_second_column AS second,\n" +
                "st.first_column AS third\n" +
                "FROM some_table t\n" +
                "LEFT JOIN second_table st\n" +
                "ON t.id = st.first_table_id";

        //when
        Select select = selectParser.parseSelect(parseSelect);

        //then
        SelectItem firstSelectItem = select.getSelectItemList().get(0);
        assertEquals("FIRST", firstSelectItem.getAlias());
        assertEquals("T.SOME_FIRST_COLUMN", firstSelectItem.getExpression());
        assertFalse(firstSelectItem.getHasComplexExpression());

        SelectItem secondSelectItem = select.getSelectItemList().get(1);
        assertEquals("SECOND", secondSelectItem.getAlias());
        assertEquals("T.SOME_SECOND_COLUMN", secondSelectItem.getExpression());
        assertFalse(secondSelectItem.getHasComplexExpression());

        SelectItem thirdSelectItem = select.getSelectItemList().get(2);
        assertEquals("THIRD", thirdSelectItem.getAlias());
        assertEquals("ST.FIRST_COLUMN", thirdSelectItem.getExpression());
        assertFalse(thirdSelectItem.getHasComplexExpression());
    }
}