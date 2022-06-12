package io.github.sergeivisotsky.metadata.engine.graphql.filtering;

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
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.sergeivisotsky.metadata.engine.domain.SortDirection.ASC;
import static io.github.sergeivisotsky.metadata.engine.domain.SortDirection.DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphQLViewQueryParserTest {

    private final ViewQueryParser parser = new GraphQLViewQueryParser();

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
    void shouldConstructViewQueryWithProperSortLimitAndOffset() throws ViewQueryParseException {
        //given
        Map<String, String[]> params = ImmutableMap.<String, String[]>builder()
                .put("_sort", new String[]{"desc(fieldName1),asc(fieldName2)"})
                .put("_offset", new String[]{"200"})
                .put("_limit", new String[]{"100"})
                .build();

        //when
        ViewQuery query = parser.constructViewQuery(metadata, params);

        //then
        assertEquals((Long) 200L, query.getOffset());
        assertEquals((Integer) 100, query.getLimit());
        assertEquals(DESC, query.getOrderList().get(0).getDirection());
        assertEquals(ASC, query.getOrderList().get(1).getDirection());
    }

}