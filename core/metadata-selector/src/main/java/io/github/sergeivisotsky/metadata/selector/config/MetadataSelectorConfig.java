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

package io.github.sergeivisotsky.metadata.selector.config;

import java.sql.ResultSet;

import io.github.sergeivisotsky.metadata.selector.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.dao.LookupMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.MetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.impl.CacheableMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.impl.LookupMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dao.impl.MetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dto.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.Layout;
import io.github.sergeivisotsky.metadata.selector.dto.LookupHolder;
import io.github.sergeivisotsky.metadata.selector.dto.LookupMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Sergei Visotsky
 */
@Configuration
@ComponentScan(basePackages = {
        "io.github.sergeivisotsky.metadata.*"
})
public class MetadataSelectorConfig {

    @Bean
    public MetadataDao metadataDao(NamedParameterJdbcTemplate jdbcTemplate,
                                   MetadataMapper<ResultSet, FormMetadata> formMetadataMapper,
                                   MetadataMapper<ResultSet, Layout> layoutMapper,
                                   CacheConfigProperties cacheConfigProperties) {
        MetadataDaoImpl metadataDao = new MetadataDaoImpl(jdbcTemplate, formMetadataMapper, layoutMapper);
        return new CacheableMetadataDao(metadataDao, cacheConfigProperties);
    }

    @Bean
    public LookupMetadataDao lookupMetadataDao(NamedParameterJdbcTemplate jdbcTemplate,
                                               MetadataMapper<ResultSet, LookupHolder> lookupHolderMapper,
                                               MetadataMapper<ResultSet, LookupMetadata> lookupMetadataMapper) {
        return new LookupMetadataDaoImpl(jdbcTemplate, lookupHolderMapper, lookupMetadataMapper);
    }
}
