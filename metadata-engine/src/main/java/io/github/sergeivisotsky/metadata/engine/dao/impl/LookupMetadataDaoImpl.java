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

package io.github.sergeivisotsky.metadata.engine.dao.impl;

import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.LookupMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.LookupHolder;
import io.github.sergeivisotsky.metadata.engine.domain.LookupMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

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
        try {
            Map<String, Object> params = Map.of("lookupName", lookupName);
            return jdbcTemplate.queryForObject(lookupHolderMapper.getSql(), params,
                    (rs, index) -> {
                        LookupHolder holder = lookupHolderMapper.map(rs);
                        holder.setMetadata(getMetadataForLookupHolder(lang, rs.getLong("id")));
                        return holder;
                    });
        } catch (Exception e) {
            throw new MetadataStorageException(e, "Unable to get a lookup holder for a lookup " +
                    "with the following input parameters: lookupName={}, lang={}", lookupName, lang);
        }
    }

    private List<LookupMetadata> getMetadataForLookupHolder(String lang, Long holderId) {
        try {
            Map<String, Object> metadataParams = Map.of(
                    "holderId", holderId,
                    "lang", lang);
            return jdbcTemplate.query(lookupMetadataMapper.getSql(), metadataParams,
                    (rs, index) -> lookupMetadataMapper.map(rs));
        } catch (Exception e) {
            throw new MetadataStorageException(e, "Unable to get metadata for a lookup holder with the " +
                    "following holder parameters: lang={}, holderId={}", lang, holderId);
        }
    }
}
