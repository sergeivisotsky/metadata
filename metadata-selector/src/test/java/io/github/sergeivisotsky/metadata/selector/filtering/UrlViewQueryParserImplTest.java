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

package io.github.sergeivisotsky.metadata.selector.filtering;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.sergeivisotsky.metadata.selector.domain.FieldType;
import io.github.sergeivisotsky.metadata.selector.domain.Language;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.exception.UrlParseException;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.BetweenFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link UrlViewQueryParserImpl}.
 *
 * @author Sergei Visotsky
 */
public class UrlViewQueryParserImplTest {

    private final UrlViewQueryParserImpl parser = new UrlViewQueryParserImpl();

    private static ViewMetadata metadata;

    @BeforeClass
    public static void beforeClass() {
        metadata = new ViewMetadata();
        metadata.setFont("Times New Roman");
        metadata.setFontSize(12);
        metadata.setOffset(13);
        metadata.setViewName("main");
        metadata.setLang(Language.EN);

        ViewField viewFieldOne = new ViewField();
        viewFieldOne.setFieldType(FieldType.STRING);
        viewFieldOne.setName("someField");

        ViewField viewFieldTwo = new ViewField();
        viewFieldTwo.setFieldType(FieldType.STRING);
        viewFieldTwo.setName("fieldName2");

        ViewField viewFieldThree = new ViewField();
        viewFieldThree.setFieldType(FieldType.STRING);
        viewFieldThree.setName("fieldName3");

        List<ViewField> fields = ImmutableList.<ViewField>builder()
                .add(viewFieldOne)
                .add(viewFieldTwo)
                .add(viewFieldThree)
                .build();

        metadata.setViewField(fields);
    }

    @Test
    public void shouldConstructViewQueryWithBetweenAndFilters() throws UrlParseException {
        Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                .put("someField", new String[]{"value1"})
                .put("fieldName2", new String[]{"value1"})
                .put("fieldName3:bw", new String[]{"value3,value4"})
                .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                .put("_offset", new String[]{"200"})
                .put("_limit", new String[]{"100"})
                .build();
        ViewQuery query = parser.constructViewQuery(metadata, params);
        assertTrue(query.getFilter() instanceof AndFilter);
        assertTrue(((AndFilter) query.getFilter()).getLeftFilter() instanceof BetweenFilter);
        assertTrue(((AndFilter) query.getFilter()).getRightFilter() instanceof AndFilter);
        assertEquals((Long) 200L, query.getOffset());
        assertEquals((Integer) 100, query.getLimit());
    }
}