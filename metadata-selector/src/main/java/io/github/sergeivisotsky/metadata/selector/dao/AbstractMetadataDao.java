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
import javax.sql.DataSource;

import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.ProcedureMetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.SQLMetadataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * An abstract class to hold a common methods and beans for all metadata provider DAOs.
 *
 * @author Sergei Visotsky
 */
public abstract class AbstractMetadataDao {

    protected DataSource dataSource;
    protected NamedParameterJdbcTemplate jdbcTemplate;

    protected <T> List<T> executeQuery(Map<String, Object> params, MetadataMapper<T> mapper) {
        return jdbcTemplate.query(mapper.getSql(), params, (rs, index) -> ((SQLMetadataMapper<T>) mapper).map(rs));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <T> T checkLogicType(Supplier<MetadataMapper> mapperSupplier,
                                   Supplier sql,
                                   Supplier procCall) {
        MetadataMapper<T> mapper = mapperSupplier.get();
        if (mapper instanceof SQLMetadataMapper) {
            return (T) sql.get();
        } else if (mapper instanceof ProcedureMetadataMapper) {
            return (T) procCall.get();
        }
        throw new IllegalStateException(
                String.format("Provided LogicType: %s is not supported", mapper.getClass())
        );
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
