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

package io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.domain.FieldType;
import io.github.sergeivisotsky.metadata.engine.domain.Order;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.AndFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.BetweenFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.BinaryFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.EqualsFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.Filter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.GreaterFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.LessFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.OrFilter;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.formatter.SQLFormatter;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlparser.SQLParseException;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlparser.Select;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlparser.SelectItem;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlparser.SelectParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base implementation of an SQL dialect which aimed
 * to hold a methods specific for all dialects.
 *
 * @author Sergei Visotsky
 */
abstract class BaseSQLDialect implements SQLDialect {

    protected static final String FILTER_PLACEHOLDER = "{filter}";
    protected static final String ORDER_PLACEHOLDER = "{order}";
    protected static final String LIMIT_PLACEHOLDER = "{limit}";
    protected static final String OFFSET_PLACEHOLDER = "{offset}";

    private final Map<FieldType, SQLFormatter> formatterMap;

    public BaseSQLDialect(Map<FieldType, SQLFormatter> formatterMap) {
        this.formatterMap = formatterMap;
    }

    protected SelectParser selectParser;

    @Override
    public Object getValueFromResultSet(ResultSet rs, ViewField field) throws SQLException {
        switch (field.getFieldType()) {
            case DATE:
                long intValue = rs.getLong(field.getName());
                return rs.wasNull() ? null : intValue;
            case TIME:
                return rs.getTime(field.getName());
            case STRING:
                return rs.getString(field.getName());
            case DECIMAL:
                return rs.getBigDecimal(field.getName());
            case INTEGER:
                return rs.getInt(field.getName());
            case DATETIME:
                return rs.getTimestamp(field.getName());
        }
        throw new IllegalArgumentException("Field " + field.getName() +
                " of type " + field.getFieldType() + " is not supported");
    }

    protected String prepareSQL(String sqlTemplate, ViewQuery query) {
        String sql = sqlTemplate;

        Map<String, String> fieldNameToFilterColumnMap = extractFilterColumnMap(sql);
        String strFilter = createFilterClause(query.getFilter(), fieldNameToFilterColumnMap);
        String strOrder = createOrderClause(query.getOrderList(), fieldNameToFilterColumnMap);

        sql = StringUtils.replace(sql, FILTER_PLACEHOLDER, strFilter);
        sql = StringUtils.replace(sql, FILTER_PLACEHOLDER, "");
        sql = StringUtils.replace(sql, ORDER_PLACEHOLDER, strOrder);

        return sql;
    }

    protected String createFilterClause(Filter filter, Map<String, String> fieldNameToFilterColumnMap) {

        if (filter == null) {
            return "0=0";
        }

        if (filter instanceof BinaryFilter) {
            BinaryFilter binFilter = (BinaryFilter) filter;
            String left = createFilterClause(binFilter.getLeftFilter(), fieldNameToFilterColumnMap);
            String right = createFilterClause(binFilter.getRightFilter(), fieldNameToFilterColumnMap);

            String strBinFilter = getBinaryFilterSQLKeyword(binFilter);
            return "(" + left + ") " + strBinFilter + " (" + right + ")";
        }

        if (filter instanceof EqualsFilter) {
            EqualsFilter equalsFilter = (EqualsFilter) filter;
            String sqlFilterColumn = getSqlFilterColumnChecked(fieldNameToFilterColumnMap, equalsFilter.getAttributeName());
            return sqlFilterColumn + "=" + formatWhereValue(equalsFilter.getType(), equalsFilter.getValue());
        }

        if (filter instanceof BetweenFilter) {
            BetweenFilter betweenFilter = (BetweenFilter) filter;
            String sqlFilterColumn = getSqlFilterColumnChecked(fieldNameToFilterColumnMap, betweenFilter.getAttributeName());
            return sqlFilterColumn +
                    " BETWEEN " +
                    formatWhereValue(betweenFilter.getType(), betweenFilter.getFrom()) +
                    " AND " +
                    formatWhereValue(betweenFilter.getType(), betweenFilter.getTo());
        }

        if (filter instanceof LessFilter) {
            LessFilter lessFilter = (LessFilter) filter;
            String sqlFilterColumn = getSqlFilterColumnChecked(fieldNameToFilterColumnMap, lessFilter.getAttributeName());
            return sqlFilterColumn + " < " + formatWhereValue(lessFilter.getType(), lessFilter.getValue());
        }

        if (filter instanceof GreaterFilter) {
            GreaterFilter greaterFilter = (GreaterFilter) filter;
            String sqlFilterColumn = getSqlFilterColumnChecked(fieldNameToFilterColumnMap, greaterFilter.getAttributeName());
            return sqlFilterColumn + " > " + formatWhereValue(greaterFilter.getType(), greaterFilter.getValue());
        }

        throw new IllegalArgumentException("Filter of type " + filter.getClass().getName() + " is not supported");
    }

    protected String createOrderClause(List<Order> orderList, Map<String, String> fieldNameToFilterColumnMap) {
        if (orderList.isEmpty()) {
            return "";
        }
        StringBuilder buf = new StringBuilder("ORDER BY");
        boolean first = true;
        for (Order order : orderList) {
            String sqlColumn = fieldNameToFilterColumnMap.get(order.getFieldName());
            if (first) {
                first = false;
            } else {
                buf.append(",");
            }
            buf.append(" ").append(sqlColumn).append(" ").append(order.getDirection());
        }
        return buf.toString();
    }

    protected String formatWhereValue(FieldType fieldType, Object value) {
        SQLFormatter formatter = formatterMap.get(fieldType);
        return formatter.formatWhereValue(value);
    }

    protected String getSqlFilterColumnChecked(Map<String, String> fieldNameToFilterColumnMap,
                                               String attributeName) {
        String sqlFilterColumn = fieldNameToFilterColumnMap.get(attributeName.toUpperCase());
        if (sqlFilterColumn == null) {
            throw new IllegalArgumentException("There is no applicable SQL query column for filter attribute " + attributeName);
        }
        return sqlFilterColumn;
    }

    protected String getBinaryFilterSQLKeyword(BinaryFilter binFilter) {
        if (binFilter instanceof AndFilter) {
            return "AND";
        } else if (binFilter instanceof OrFilter) {
            return "OR";
        }
        throw new IllegalArgumentException("Unsupported filter specified " + binFilter.getClass());
    }

    protected Map<String, String> extractFilterColumnMap(String sqlTemplate) {
        Select select;
        try {
            select = selectParser.parseSelect(sqlTemplate);
        } catch (SQLParseException e) {
            throw new MetadataStorageException(e, "Error parsing SQL template");
        }

        Map<String, String> resultMap = new HashMap<>();
        for (SelectItem item : select.getSelectItemList()) {
            if (!item.getHasComplexExpression()) {
                String alias;
                if (item.getAlias() != null) {
                    alias = item.getAlias();
                } else {
                    alias = StringUtils.substringAfterLast(item.getExpression(), ".");
                }
                resultMap.put(alias.toUpperCase(), item.getExpression());
            }
        }
        return resultMap;
    }

    @Autowired
    public void setSelectParser(SelectParser selectParser) {
        this.selectParser = selectParser;
    }

}
