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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.IndexedParamItem;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.ParamItem;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.ProcedureParamHolder;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.ProcedureParser;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.SQLParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Sergei Visotsky
 */
public class PrimitiveProcedureParser implements ProcedureParser {

    @Override
    public ProcedureParamHolder parseProcedure(String proc) throws SQLParseException {
        if (proc == null) {
            throw new SQLParseException("Procedure definition is not provided");
        }
        String inputParams = StringUtils.substringBetween(proc, "(", ")");

        List<String> inParams = Arrays.stream(StringUtils.split(inputParams, ","))
                .map(param -> StringUtils.substringAfter(param, "=>").trim())
                .collect(Collectors.toList());

        List<ParamItem> inputItems = new ArrayList<>();

        int index = 0;
        while (inParams.iterator().hasNext()) {
            if (index == inParams.size()) {
                break;
            }
            inputItems.add(new IndexedParamItem(index++));
        }

        return new ProcedureParamHolder(inputItems, List.of());
    }
}
