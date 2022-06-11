package io.github.sergeivisotsky.metadata.engine.filtering;

import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.UrlParseException;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;

/**
 * View query parser rather from RESt or GraphQL.
 *
 * @author Sergejs Visockis
 */
public abstract class ViewQueryParser {

    protected static final String DELIMITER = ":";
    protected static final char MULTI_VALUE_DELIMITER = ',';
    protected static final String OFFSET = "_offset";
    protected static final String LIMIT = "_limit";
    protected static final String SORT = "_sort";

    protected Long parseOffset(Map<String, String[]> params) throws UrlParseException {
        String[] strArray = params.get(OFFSET);
        if (strArray == null) {
            return null;
        }
        if (strArray.length > 1) {
            throw new UrlParseException("Only one _offset parameter allowed");
        }
        return Long.parseLong(strArray[0]);
    }

    protected Integer parseLimit(Map<String, String[]> params) throws UrlParseException {
        String[] strArray = params.get(LIMIT);
        if (strArray == null) {
            return null;
        }
        if (strArray.length > 1) {
            throw new UrlParseException("Only one _limit parameter allowed");
        }
        return Integer.parseInt(strArray[0]);
    }

    /**
     * Core method responsible for a view query construction.
     * Parameters should contain arther URL query parameters or a GraphQL query parameters related to an offset etc.
     * As GraphQL is more flexible than REST for GraphQL not so many parameters can be passed except offset and limit.
     *
     * @param metadata view metadata content should be picked up for.
     * @param params   view parameters like offset limit, etc.
     * @return view query to execute.
     * @throws UrlParseException rises when URL parsing exception occurs.
     */
    public abstract ViewQuery constructViewQuery(ViewMetadata metadata, Map<String, String[]> params) throws UrlParseException;

}
