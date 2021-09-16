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

package io.github.sergeivisotsky.metadata.selector.dao.impl;

import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LookupMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.LookupHolder;
import io.github.sergeivisotsky.metadata.selector.dto.LookupMetadata;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class LookupMetadataDaoImpl extends AbstractMetadataDao implements LookupMetadataDao {

    private final MetadataMapper<LookupHolder> lookupHolderMapper;
    private final MetadataMapper<LookupMetadata> lookupMetadataMapper;

    public LookupMetadataDaoImpl(MetadataMapper<LookupHolder> lookupHolderMapper,
                                 MetadataMapper<LookupMetadata> lookupMetadataMapper) {
        this.lookupHolderMapper = lookupHolderMapper;
        this.lookupMetadataMapper = lookupMetadataMapper;
    }

    @Override
    public LookupHolder getLookupMetadata(String lookupName, String lang) {
        Map<String, Object> params = Map.of("lookupName", lookupName);
        return checkLogicType(
                lookupHolderMapper::logicType,
                () -> jdbcTemplate.queryForObject(lookupHolderMapper.getSql(), params,
                        (rs, index) -> {
                            LookupHolder holder = lookupHolderMapper.map(rs);
                            holder.setMetadata(getMetadataForLookupHolder(lang, rs.getLong("id")));
                            return holder;
                        }),
                lookupHolderMapper::executeFunction
        );
    }

    @SuppressWarnings({"unchecked"})
    private List<LookupMetadata> getMetadataForLookupHolder(String lang, Long holderId) {
        Map<String, Object> metadataParams = Map.of(
                "holderId", holderId,
                "lang", lang);
        return (List<LookupMetadata>) checkLogicType(
                lookupMetadataMapper::logicType,
                () -> jdbcTemplate.query(lookupMetadataMapper.getSql(), metadataParams,
                        (rs, index) -> lookupMetadataMapper.map(rs)),
                lookupMetadataMapper::executeFunction
        );
    }
}
