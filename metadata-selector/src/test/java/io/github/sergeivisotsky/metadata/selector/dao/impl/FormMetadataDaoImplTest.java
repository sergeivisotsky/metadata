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

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormField;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormSection;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FormMetadataDaoImpl}.
 *
 * @author Sergei Visotsky
 */
class FormMetadataDaoImplTest extends AbstractMetadataDao {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<FormMetadata> formMetadataMapper;

    @Mock
    private MetadataMapper<FormSection> formSectionMapper;

    @Mock
    private MetadataMapper<FormField> formFieldMapper;

    @InjectMocks
    private FormMetadataDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetFormMetadata() {
        //given
        when(formMetadataMapper.getSql()).thenReturn("SELECT * FROM some_table WHERE id = 1");
        when(formSectionMapper.getSql()).thenReturn("SELECT * FROM some_table WHERE id = 1");
        when(formFieldMapper.getSql()).thenReturn("SELECT * FROM some_table WHERE id = 1");

        //when
        dao.getFormMetadata("someForm", "EN");

        //then
        verify(jdbcTemplate).queryForObject(anyString(), anyMap(), any(RowMapper.class));
    }
}