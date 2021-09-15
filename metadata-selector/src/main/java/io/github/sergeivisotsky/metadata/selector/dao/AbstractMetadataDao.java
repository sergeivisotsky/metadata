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

package io.github.sergeivisotsky.metadata.selector.dao;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import io.github.sergeivisotsky.metadata.selector.dto.LogicType;
import io.github.sergeivisotsky.metadata.selector.exception.DataAccessException;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import static io.github.sergeivisotsky.metadata.selector.dto.LogicType.FUNCTION;
import static io.github.sergeivisotsky.metadata.selector.dto.LogicType.SQL;

/**
 * An abstract class to hold a common methods and beans for all metadata provider DAOs.
 *
 * @author Sergei Visotsky
 */
public abstract class AbstractMetadataDao {

    protected SimpleJdbcCall jdbcCall;
    protected NamedParameterJdbcTemplate jdbcTemplate;

    protected <T> List<T> executeQuery(Map<String, Object> params, MetadataMapper<T> mapper) {
        return jdbcTemplate.query(mapper.getSql(), params, (rs, index) -> mapper.map(rs));
    }

    protected <T> T checkLogicType(Supplier<LogicType> logicType,
                                   Supplier<T> sql,
                                   Supplier<T> functionCall) {
        if (SQL.equals(logicType.get())) {
            return sql.get();
        }
        if (FUNCTION.equals(logicType.get())) {
            return functionCall.get();
        }
        throw new DataAccessException("Provided LogicType: {} is not supported", logicType.get().name());
    }

    @Autowired
    public void setJdbcCall(SimpleJdbcCall jdbcCall) {
        this.jdbcCall = jdbcCall;
    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
