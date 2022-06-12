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

package io.github.sergeivisotsky.metadata.engine.rest.filtering;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.sergeivisotsky.metadata.engine.domain.FieldType;
import io.github.sergeivisotsky.metadata.engine.domain.Language;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.ViewQueryParseException;
import io.github.sergeivisotsky.metadata.engine.filtering.ViewQueryParser;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.BetweenFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.GreaterFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.LessFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.LikeFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.sergeivisotsky.metadata.engine.domain.SortDirection.ASC;
import static io.github.sergeivisotsky.metadata.engine.domain.SortDirection.DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@link UrlViewQueryParser}.
 *
 * @author Sergei Visotsky
 */
class UrlViewQueryParserTest {

    private final ViewQueryParser parser = new UrlViewQueryParser();

    private static ViewMetadata metadata;

    @BeforeAll
    static void beforeAll() {
        metadata = new ViewMetadata();
        metadata.setFont("Times New Roman");
        metadata.setFontSize(12);
        metadata.setOffset(13);
        metadata.setViewName("main");
        metadata.setLang(Language.EN);

        ViewField viewFieldOne = new ViewField();
        viewFieldOne.setFieldType(FieldType.STRING);
        viewFieldOne.setName("someField");

        ViewField viewFieldOneAndOne = new ViewField();
        viewFieldOneAndOne.setFieldType(FieldType.STRING);
        viewFieldOneAndOne.setName("fieldName1");

        ViewField viewFieldTwo = new ViewField();
        viewFieldTwo.setFieldType(FieldType.STRING);
        viewFieldTwo.setName("fieldName2");

        ViewField viewFieldThree = new ViewField();
        viewFieldThree.setFieldType(FieldType.STRING);
        viewFieldThree.setName("fieldName3");

        List<ViewField> fields = ImmutableList.<ViewField>builder()
                .add(viewFieldOne)
                .add(viewFieldOneAndOne)
                .add(viewFieldTwo)
                .add(viewFieldThree)
                .build();

        metadata.setViewField(fields);
    }

    @Test
    void shouldConstructViewQueryWithBetweenAndFilters() throws ViewQueryParseException {
        //given
        Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                .put("someField", new String[]{"value1"})
                .put("fieldName2", new String[]{"value1"})
                .put("fieldName3:bw", new String[]{"value3,value4"})
                .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                .put("_offset", new String[]{"200"})
                .put("_limit", new String[]{"100"})
                .build();

        //when
        ViewQuery query = parser.constructViewQuery(metadata, params);

        //then
        assertTrue(query.getFilter() instanceof AndFilter);
        assertTrue(((AndFilter) query.getFilter()).getLeftFilter() instanceof BetweenFilter);
        assertTrue(((AndFilter) query.getFilter()).getRightFilter() instanceof AndFilter);
        assertEquals((Long) 200L, query.getOffset());
        assertEquals((Integer) 100, query.getLimit());
        assertEquals(DESC, query.getOrderList().get(0).getDirection());
        assertEquals("fieldName1", query.getOrderList().get(0).getFieldName());
        assertEquals(ASC, query.getOrderList().get(1).getDirection());
        assertEquals("fieldName2", query.getOrderList().get(1).getFieldName());
    }

    @Test
    void shouldConstructViewQueryWithGreaterFilter() throws ViewQueryParseException {
        //given
        Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                .put("someField", new String[]{"value1"})
                .put("fieldName2", new String[]{"value1"})
                .put("fieldName3:gt", new String[]{"value3,value4"})
                .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                .put("_offset", new String[]{"200"})
                .put("_limit", new String[]{"100"})
                .build();

        //when
        ViewQuery query = parser.constructViewQuery(metadata, params);

        //then
        assertTrue(query.getFilter() instanceof AndFilter);
        assertTrue(((AndFilter) query.getFilter()).getLeftFilter() instanceof GreaterFilter);
        assertTrue(((AndFilter) query.getFilter()).getRightFilter() instanceof AndFilter);
        assertEquals((Long) 200L, query.getOffset());
        assertEquals((Integer) 100, query.getLimit());
        assertEquals(DESC, query.getOrderList().get(0).getDirection());
        assertEquals("fieldName1", query.getOrderList().get(0).getFieldName());
        assertEquals(ASC, query.getOrderList().get(1).getDirection());
        assertEquals("fieldName2", query.getOrderList().get(1).getFieldName());
    }

    @Test
    void shouldConstructViewQueryWithLessFilter() throws ViewQueryParseException {
        //given
        Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                .put("someField", new String[]{"value1"})
                .put("fieldName2", new String[]{"value1"})
                .put("fieldName3:ls", new String[]{"value3,value4"})
                .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                .put("_offset", new String[]{"200"})
                .put("_limit", new String[]{"100"})
                .build();

        //when
        ViewQuery query = parser.constructViewQuery(metadata, params);

        //then
        assertTrue(query.getFilter() instanceof AndFilter);
        assertTrue(((AndFilter) query.getFilter()).getLeftFilter() instanceof LessFilter);
        assertTrue(((AndFilter) query.getFilter()).getRightFilter() instanceof AndFilter);
        assertEquals((Long) 200L, query.getOffset());
        assertEquals((Integer) 100, query.getLimit());
        assertEquals(DESC, query.getOrderList().get(0).getDirection());
        assertEquals("fieldName1", query.getOrderList().get(0).getFieldName());
        assertEquals(ASC, query.getOrderList().get(1).getDirection());
        assertEquals("fieldName2", query.getOrderList().get(1).getFieldName());
    }

    @Test
    void shouldConstructViewQueryWithLikeFilter() throws ViewQueryParseException {
        //given
        Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                .put("someField", new String[]{"value1"})
                .put("fieldName2", new String[]{"value1"})
                .put("fieldName3:lk", new String[]{"value3,value4"})
                .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                .put("_offset", new String[]{"200"})
                .put("_limit", new String[]{"100"})
                .build();

        //when
        ViewQuery query = parser.constructViewQuery(metadata, params);

        //then
        assertTrue(query.getFilter() instanceof AndFilter);
        assertTrue(((AndFilter) query.getFilter()).getLeftFilter() instanceof LikeFilter);
        assertTrue(((AndFilter) query.getFilter()).getRightFilter() instanceof AndFilter);
        assertEquals((Long) 200L, query.getOffset());
        assertEquals((Integer) 100, query.getLimit());
        assertEquals(DESC, query.getOrderList().get(0).getDirection());
        assertEquals("fieldName1", query.getOrderList().get(0).getFieldName());
        assertEquals(ASC, query.getOrderList().get(1).getDirection());
        assertEquals("fieldName2", query.getOrderList().get(1).getFieldName());
    }

    @Test
    void shouldConstructViewQueryThrowViewQueryParseException() {
        Assertions.assertThrows(ViewQueryParseException.class, () -> {
            //given
            Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                    .put("someField", new String[]{"value1"})
                    .put("fieldName2", new String[]{"value1"})
                    .put("fieldName3:mm", new String[]{"value3,value4"})
                    .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                    .put("_offset", new String[]{"200"})
                    .put("_limit", new String[]{"100"})
                    .build();

            //when
            ViewQuery query = parser.constructViewQuery(metadata, params);

            //then
            assertTrue(query.getFilter() instanceof AndFilter);
            assertTrue(((AndFilter) query.getFilter()).getLeftFilter() instanceof LikeFilter);
            assertTrue(((AndFilter) query.getFilter()).getRightFilter() instanceof AndFilter);
            assertEquals((Long) 200L, query.getOffset());
            assertEquals((Integer) 100, query.getLimit());
            assertEquals(DESC, query.getOrderList().get(0).getDirection());
            assertEquals("fieldName1", query.getOrderList().get(0).getFieldName());
            assertEquals(ASC, query.getOrderList().get(1).getDirection());
            assertEquals("fieldName2", query.getOrderList().get(1).getFieldName());
        });
    }
}