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

package io.github.sergeivisotsky.metadata.engine.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.engine.itest.domain.ExtendedLookupMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.LookupMetadata;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

public class LookupMetadataMapper implements MetadataMapper<LookupMetadata> {

    @Override
    public String getSql() {
        return "SELECT lm.code,\n" +
                "       lm.lang,\n" +
                "       lm.default_value,\n" +
                "       lm.display_value\n" +
                "FROM lookup_metadata lm\n" +
                "WHERE lm.lookup_holder_id = :holderId\n" +
                "  AND lm.lang = :lang";
    }

    @Override
    public LookupMetadata map(ResultSet rs) {
        try {
            ExtendedLookupMetadata metadata = new ExtendedLookupMetadata();

            metadata.setLang(rs.getString("lang"));
            metadata.setCode(rs.getString("code"));
            metadata.setDefaultValue(rs.getString("default_value"));
            metadata.setDisplayValue(rs.getString("display_value"));

            return metadata;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    LookupMetadataMapper.class.getSimpleName(), e);
        }
    }
}
