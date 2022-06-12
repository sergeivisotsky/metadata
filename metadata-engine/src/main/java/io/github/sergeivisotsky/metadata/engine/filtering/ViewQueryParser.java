package io.github.sergeivisotsky.metadata.engine.filtering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.domain.Order;
import io.github.sergeivisotsky.metadata.engine.domain.SortDirection;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.ViewQueryParseException;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import org.apache.commons.lang3.StringUtils;

import static io.github.sergeivisotsky.metadata.engine.filtering.FilteringConstants.LIMIT;
import static io.github.sergeivisotsky.metadata.engine.filtering.FilteringConstants.OFFSET;
import static io.github.sergeivisotsky.metadata.engine.filtering.FilteringConstants.SORT;

/**
 * View query parser rather from REST or GraphQL.
 *
 * @author Sergejs Visockis
 */
public abstract class ViewQueryParser {

    protected static final String DELIMITER = ":";
    protected static final char MULTI_VALUE_DELIMITER = ',';

    /**
     * Core method responsible for a view query construction.
     * Parameters should contain arther URL query parameters or a GraphQL query parameters related to an offset etc.
     * As GraphQL is more flexible than REST for GraphQL not so many parameters can be passed except offset and limit.
     *
     * @param metadata view metadata content should be picked up for.
     * @param params   view parameters like offset limit, etc.
     * @return view query to execute.
     * @throws ViewQueryParseException rises when URL parsing exception occurs.
     */
    public abstract ViewQuery constructViewQuery(ViewMetadata metadata, Map<String, String[]> params) throws ViewQueryParseException;

    protected Long parseOffset(Map<String, String[]> params) throws ViewQueryParseException {
        String[] strArray = params.get(OFFSET);
        if (strArray == null) {
            return null;
        }
        if (strArray.length > 1) {
            throw new ViewQueryParseException("Only one _offset parameter allowed");
        }
        return Long.parseLong(strArray[0]);
    }

    protected Integer parseLimit(Map<String, String[]> params) throws ViewQueryParseException {
        String[] strArray = params.get(LIMIT);
        if (strArray == null) {
            return null;
        }
        if (strArray.length > 1) {
            throw new ViewQueryParseException("Only one _limit parameter allowed");
        }
        return Integer.parseInt(strArray[0]);
    }

    protected List<Order> parseOrderList(ViewMetadata metadata, Map<String, String[]> params) throws ViewQueryParseException {
        String strSort = getAndValidateParam(params, SORT);

        List<Order> orderList;
        if (StringUtils.isNotEmpty(strSort)) {
            orderList = parseSort(metadata, strSort);
        } else {
            orderList = List.of();
        }
        return orderList;
    }

    private List<Order> parseSort(ViewMetadata metadata, String strSort) throws ViewQueryParseException {
        // remove spaces if any
        strSort = StringUtils.replace(strSort, " ", "");

        String[] splitResult = StringUtils.split(strSort, MULTI_VALUE_DELIMITER);

        List<Order> result = new ArrayList<>();
        for (String order : splitResult) {

            String directionName = StringUtils.substringBefore(order, "(").toUpperCase();
            String fieldName = StringUtils.substringBetween(order, "(", ")");

            ViewField field = getViewFieldByName(metadata, fieldName);
            if (field == null) {
                throw new ViewQueryParseException("Sort field " + fieldName + " is not supported by this view.");
            }

            SortDirection direction;

            try {
                direction = SortDirection.valueOf(directionName);
            } catch (IllegalArgumentException e) {
                throw new ViewQueryParseException("Invalid sort direction definition passed " + directionName);
            }

            result.add(new Order(fieldName, direction));
        }

        return result;
    }

    protected String getAndValidateParam(Map<String, String[]> params, String paramName) throws ViewQueryParseException {
        String[] array = params.get(paramName);
        if (array == null || array.length == 0) {
            return null;
        }
        if (array.length > 1) {
            throw new ViewQueryParseException("Only one parameter " + paramName + " is allowed in view query URL");
        }
        return array[0];
    }

    protected ViewField getViewFieldByName(ViewMetadata metadata, String fieldName) throws ViewQueryParseException {
        return metadata.getViewField()
                .stream()
                .filter(field -> fieldName.equalsIgnoreCase(field.getName()))
                .findFirst()
                .orElseThrow(() -> new ViewQueryParseException("Field " + fieldName +
                        " is not supported for a view " + metadata.getViewName()));
    }

}
