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

package io.github.sergeivisotsky.metadata.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.itest.domain.ExtendedLookupHolder;
import io.github.sergeivisotsky.metadata.selector.domain.LookupHolder;
import io.github.sergeivisotsky.metadata.itest.dto.ExtendedLookupHolder;
import io.github.sergeivisotsky.metadata.selector.dto.LogicType;
import io.github.sergeivisotsky.metadata.selector.dto.LookupHolder;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

import static io.github.sergeivisotsky.metadata.selector.dto.LogicType.SQL;

public class LookupHolderMapper implements MetadataMapper<LookupHolder> {

    @Override
    public String getSql() {
        return "SELECT l.id,\n" +
                "      l.name,\n" +
                "      l.weight,\n" +
                "      l.height\n" +
                "FROM lookup_holder l\n" +
                "WHERE l.name = :lookupName";
    }

    @Override
    public ExtendedLookupHolder map(ResultSet rs) {
        try {
            ExtendedLookupHolder holder = new ExtendedLookupHolder();

            holder.setName(rs.getString("name"));
            holder.setWeight(rs.getInt("weight"));
            holder.setHeight(rs.getInt("height"));

            return holder;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    LookupHolderMapper.class.getSimpleName(), e);
        }
    }

    @Override
    public LogicType logicType() {
        return SQL;
    }
}
