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

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.LookupHolder;
import io.github.sergeivisotsky.metadata.engine.domain.LookupMetadata;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link LookupMetadataDaoImpl}.
 *
 * @author Sergei Visotsky
 */
class LookupMetadataDaoImplTest extends AbstractMetadataDao {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<LookupHolder> lookupHolderMapper;

    @Mock
    private MetadataMapper<LookupMetadata> lookupMetadataMapper;

    @InjectMocks
    private LookupMetadataDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetLookupMetadata() {
        //given
        LookupHolder lookupHolder = new LookupHolder();
        lookupHolder.setName("someHolder");
        lookupHolder.setWeight(300);
        lookupHolder.setHeight(20);
        lookupHolder.setMetadata(List.of());

        when(lookupHolderMapper.getSql()).thenReturn("SELECT * FROM some_table WHERE id = 1");
        when(jdbcTemplate.queryForObject(any(), anyMap(), any(RowMapper.class))).thenReturn(lookupHolder);

        //when
        dao.getLookupMetadata("someLookup", "en");

        //then
        verify(jdbcTemplate).queryForObject(any(), anyMap(), any(RowMapper.class));
    }
}
