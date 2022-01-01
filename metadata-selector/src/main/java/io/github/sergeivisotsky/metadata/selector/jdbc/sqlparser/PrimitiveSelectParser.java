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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class PrimitiveSelectParser implements SelectParser {

    private static final String KW_SELECT = "SELECT";
    private static final String KW_FROM = "FROM";
    private static final String KW_AS = "AS";

    @Override
    public Select parseSelect(String sql) throws SQLParseException {
        String uppercaseTemplate = StringUtils.trim(sql.toUpperCase());

        if (!StringUtils.startsWith(uppercaseTemplate, KW_SELECT)) {
            throw new SQLParseException("Select keyword missing");
        }

        Position curPos = new Position(KW_SELECT.length());
        List<SelectItem> selectItemList = new ArrayList<>();
        while (true) {
            SelectItem item = nextSelectItem(uppercaseTemplate, curPos);
            if (item == null) {
                break;
            }
            selectItemList.add(item);
        }

        return new Select(selectItemList);
    }


    private SelectItem nextSelectItem(String sql, Position curPos) throws SQLParseException {

        if (isKeywordNext(sql, KW_FROM, curPos)) {
            curPos.position -= KW_FROM.length();
            return null;
        }

        int nextNonSpaceIdx = nextNonSpacePos(sql, curPos.position);

        if (isStartOfFunction(sql, curPos)) {
            int openBraceCount = 1;

            int pos = curPos.position;
            while (openBraceCount > 0) {
                pos++;
                if (sql.charAt(pos) == '(') {
                    openBraceCount++;
                } else if (sql.charAt(pos) == ')') {
                    openBraceCount--;
                }
            }
            int expressionEndIndex = pos + 1;

            curPos.position = expressionEndIndex;
            if (!isKeywordNext(sql, KW_AS, curPos)) {
                throw new SQLParseException("Expression must have AS part near: " + getNearText(sql, nextNonSpaceIdx, 20));
            }

            String expression = StringUtils.substring(sql, nextNonSpaceIdx, expressionEndIndex);
            String alias = nextToken(sql, curPos);

            return new SelectItem(expression, alias, true);
        } else {

            String expression = nextToken(sql, curPos);
            String alias;
            if (isKeywordNext(sql, KW_AS, curPos)) {
                alias = nextToken(sql, curPos);
            } else {
                alias = null;
            }

            return new SelectItem(expression, alias, false);
        }
    }

    private String getNearText(String sql, int fromIdx, int length) {
        int toIdx = Math.min(fromIdx + length, sql.length());
        return StringUtils.substring(sql, fromIdx, toIdx);
    }

    private boolean isStartOfFunction(String sql, Position curPos) {
        int idx = nextNonSpacePos(sql, curPos.position);
        char curChar = sql.charAt(idx);
        while (!Character.isWhitespace(curChar) && curChar != '(' && idx < sql.length() - 1) {
            idx++;
            curChar = sql.charAt(idx);
        }
        if (curChar == '(') {
            curPos.position = idx;
            return true;
        }
        return false;
    }

    private boolean isKeywordNext(String sql, String keyword, Position pos) {
        int nextNonSpaceIdx = nextNonSpacePos(sql, pos.position);
        boolean res = keyword.equals(sql.substring(nextNonSpaceIdx, nextNonSpaceIdx + keyword.length()));
        if (res) {
            pos.position = nextNonSpaceIdx + keyword.length();
        }
        return res;
    }

    private String nextToken(String sql, Position pos) {
        int firstNonSpaceIdx = nextNonSpacePos(sql, pos.position);
        int idx = firstNonSpaceIdx;

        char curChar = sql.charAt(idx);
        while (!Character.isWhitespace(curChar) && curChar != ',' && idx < sql.length() - 1) {
            idx++;
            curChar = sql.charAt(idx);
        }
        String res = sql.substring(firstNonSpaceIdx, idx);
        pos.position = idx + 1;
        return res;
    }

    private int nextNonSpacePos(String sql, int position) {
        int cur = position;
        while (Character.isWhitespace(sql.charAt(cur)) && cur < sql.length() - 1) {
            cur++;
        }
        return cur;
    }

    static class Position {
        int position;

        public Position(int position) {
            this.position = position;
        }
    }
}
