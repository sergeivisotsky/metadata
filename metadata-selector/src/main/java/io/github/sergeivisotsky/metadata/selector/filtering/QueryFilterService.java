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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import io.github.sergeivisotsky.metadata.selector.domain.FieldType;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.BetweenFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.EqualsFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.Filter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.FilterOperator;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.GreaterFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.LessFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.LikeFilter;
import io.github.sergeivisotsky.metadata.selector.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.selector.exception.UrlParseException;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.DateTimeTypeParser;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.DateTypeParser;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.DecimalTypeParser;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.IntegerTypeParser;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.StringTypeParser;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.TimeTypeParser;
import io.github.sergeivisotsky.metadata.selector.filtering.parser.UrlParameterParser;
import io.github.sergeivisotsky.metadata.selector.utils.ParseUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Sergei Visotsky
 */
public class QueryFilterService {

    private static final String DELIMITER = ":";
    private static final char MULTI_VALUE_DELIMITER = ',';

    private static final Map<FieldType, UrlParameterParser> PARAMETER_PARSER_MAP = ImmutableMap.<FieldType, UrlParameterParser>builder()
            .put(FieldType.STRING, new StringTypeParser())
            .put(FieldType.DATE, new DateTypeParser())
            .put(FieldType.DATETIME, new DateTimeTypeParser())
            .put(FieldType.INTEGER, new IntegerTypeParser())
            .put(FieldType.DECIMAL, new DecimalTypeParser())
            .put(FieldType.TIME, new TimeTypeParser())
            .build();

    public ViewQuery constructViewQuery(ViewMetadata metadata, Map<String, String[]> params) throws UrlParseException {
        return ViewQuery.builder()
                .filter(parseFilter(metadata, params))
                .offset(parseOffset(params))
                .limit(parseLimit(params))
                .build();
    }

    private Filter parseFilter(ViewMetadata metadata, Map<String, String[]> params) throws UrlParseException {

        List<Filter> filterList = new ArrayList<>();

        for (String paramKey : params.keySet()) {

            String paramValue = getAndValidateParam(params, paramKey);

            if (StringUtils.isEmpty(paramValue)) {
                throw new UrlParseException("Parameter " + paramKey + " is null");
            }

            String paramName;
            FilterOperator operator;

            if (StringUtils.contains(paramKey, DELIMITER)) {
                paramName = StringUtils.substringBefore(paramKey, DELIMITER);
                String operatorCode = StringUtils.substringAfterLast(paramKey, DELIMITER);
                operator = FilterOperator.getByCode(operatorCode);
            } else {
                paramName = paramKey;
                operator = FilterOperator.EQUALS;
            }

            ViewField field = metadata.getViewField()
                    .stream()
                    .filter(fld -> paramName.equals(fld.getName()))
                    .findFirst()
                    .orElseThrow(() -> new UrlParseException("Field " + paramName +
                            " is not supported for a view " + metadata.getViewName()));

            filterList.add(createFilter(field, operator, paramKey, paramValue));
        }

        if (filterList.size() == 1) {
            return filterList.get(0);
        }

        AndFilter andFilter = new AndFilter(filterList.get(0), filterList.get(1));
        for (int i = 2; i < filterList.size(); i++) {
            andFilter = new AndFilter(filterList.get(i), andFilter);
        }

        return andFilter;
    }

    private Filter createFilter(ViewField field, FilterOperator operator, String paramKey, String paramValue) throws UrlParseException {
        switch (operator) {
            case EQUALS:
                return createEqualsFilter(field, paramValue);
            case BETWEEN:
                return createBetweenFilter(field, paramValue);
            case GREATER:
                return createGreaterFilter(field, paramValue);
            case LESS:
                return createLessFilter(field, paramValue);
            case LIKE:
                return createLikeFilter(field, paramValue);
            default:
                throw new UrlParseException("Operator " + operator + " in parameter " + paramKey + "is not supported");
        }
    }

    private Filter createEqualsFilter(ViewField field, String paramValue) {
        Object value = parseTypedValue(field, paramValue);
        return new EqualsFilter(field.getFieldType(), field.getName(), value);
    }

    private Filter createBetweenFilter(ViewField field, String paramValue) throws UrlParseException {
        List<String> split = ParseUtils.splitEscaped(paramValue, MULTI_VALUE_DELIMITER);
        if (split.size() != 2) {
            throw new UrlParseException("Parameter " + field.getName() +
                    " should have only two values delimited by " + MULTI_VALUE_DELIMITER);
        }
        return new BetweenFilter(
                field.getFieldType(),
                field.getName(),
                parseTypedValue(field, split.get(0)),
                parseTypedValue(field, split.get(1))
        );
    }

    private Filter createGreaterFilter(ViewField field, String paramValue) {
        Object value = parseTypedValue(field, paramValue);
        return new GreaterFilter(field.getFieldType(), field.getName(), value);
    }

    private Filter createLessFilter(ViewField field, String paramValue) {
        Object value = parseTypedValue(field, paramValue);
        return new LessFilter(field.getFieldType(), field.getName(), value);
    }

    private Filter createLikeFilter(ViewField field, String likeMask) {
        return new LikeFilter(field.getFieldType(), field.getName(), likeMask);
    }

    private Long parseOffset(Map<String, String[]> params) {
        // TODO
        return null;
    }

    private Integer parseLimit(Map<String, String[]> params) {
        // TODO
        return null;
    }

    private Object parseTypedValue(ViewField field, String paramValue) {
        return PARAMETER_PARSER_MAP
                .get(field.getFieldType())
                .parseTypedValue(field, paramValue);
    }

    private String getAndValidateParam(Map<String, String[]> params, String paramName) throws UrlParseException {
        String[] array = params.get(paramName);
        if (array == null || array.length == 0) {
            return null;
        }
        if (array.length > 1) {
            throw new UrlParseException("Only one parameter " + paramName + " is allowed in view query URL");
        }
        return array[0];
    }

}
