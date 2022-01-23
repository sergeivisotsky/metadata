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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen.dialect;

import java.util.List;

import io.github.sergeivisotsky.metadata.selector.domain.Order;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.EqualsFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.LessFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.PrimitiveSelectParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.INTEGER;
import static io.github.sergeivisotsky.metadata.selector.domain.FieldType.STRING;
import static io.github.sergeivisotsky.metadata.selector.domain.SortDirection.ASC;
import static io.github.sergeivisotsky.metadata.selector.domain.SortDirection.DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link OracleSQLDialect}.
 *
 * @author Sergei Visotsky
 */
class OracleSQLDialectTest {

    private static final String QUERY = "SELECT sst.column_one,\n" +
            "sst.column_two,\n" +
            "sst.column_three,\n" +
            "sst.column_four,\n" +
            "sst.column_five,\n" +
            "sst.column_six,\n" +
            "sst.column_seven,\n" +
            "sstt.a_column_one,\n" +
            "sstt.b_column_two,\n" +
            "sstt.c_column_three,\n" +
            "sstt.d_column_four,\n" +
            "sstt.e_column_five,\n" +
            "sstt.f_column_six,\n" +
            "sstt.g_column_seven\n" +
            "FROM some_sample_table sst\n" +
            "LEFT JOIN some_sample_table_two sstt\n" +
            "ON sst.id = sstt.some_sample_table_one_id\n" +
            "WHERE {filter}\n" +
            "{order}\n" +
            "{offset}\n" +
            "{limit}";

    private static OracleSQLDialect dialect;

    @BeforeAll
    static void beforeAll() {
        dialect = new OracleSQLDialect();
        dialect.setSelectParser(new PrimitiveSelectParser());
    }

    @Test
    void shouldCreateSelectQuery() {
        //given
        ViewQuery viewQuery = ViewQuery.builder()
                .orderList(List.of(new Order("someField", ASC)))
                .orderList(List.of(new Order("some_value", DESC)))
                .filter(new AndFilter(
                        new EqualsFilter(STRING, "COLUMN_ONE", "someMysteriousValue"),
                        new LessFilter(INTEGER, "COLUMN_FOUR", "someMask")))
                .limit(3)
                .offset(3000L)
                .build();

        //when
        String result = dialect.createSelectQuery(QUERY, viewQuery);

        //then
        final String expectedSQL = "SELECT sst.column_one,\n" +
                "sst.column_two,\n" +
                "sst.column_three,\n" +
                "sst.column_four,\n" +
                "sst.column_five,\n" +
                "sst.column_six,\n" +
                "sst.column_seven,\n" +
                "sstt.a_column_one,\n" +
                "sstt.b_column_two,\n" +
                "sstt.c_column_three,\n" +
                "sstt.d_column_four,\n" +
                "sstt.e_column_five,\n" +
                "sstt.f_column_six,\n" +
                "sstt.g_column_seven\n" +
                "FROM some_sample_table sst\n" +
                "LEFT JOIN some_sample_table_two sstt\n" +
                "ON sst.id = sstt.some_sample_table_one_id\n" +
                "WHERE (SST.COLUMN_ONE='someMysteriousValue') AND (SST.COLUMN_FOUR < someMask)\n" +
                "ORDER BY null DESC\n" +
                " OFFSET 3000 ROWS \n" +
                " FETCH NEXT 3 ROWS ONLY";

        assertEquals(expectedSQL, result);
    }

}