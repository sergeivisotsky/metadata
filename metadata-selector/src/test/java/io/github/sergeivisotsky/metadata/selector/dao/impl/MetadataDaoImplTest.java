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
import io.github.sergeivisotsky.metadata.selector.dao.ComboBoxMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Sergei Visotsky
 */
public class MetadataDaoImplTest extends AbstractMetadataDao {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<ViewMetadata> formMetadataMapper;

    @Mock
    private ComboBoxMetadataDao comboBoxMetadataDao;

    @Mock
    private LayoutMetadataDao layoutMetadataDao;

    @InjectMocks
    private MetadataDaoImpl dao;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetFormMetadata() {
        ViewMetadata metadata = new ViewMetadata();
        metadata.setFont("Times New Roman");
        metadata.setDescription("some description");

        Map<String, String> map = Map.of(
                "formName", "main",
                "lang", "en"
        );

        final String mockSql = "SELECT * FROM some_table WHERE id = 1";
        when(formMetadataMapper.getSql()).thenReturn(mockSql);
        when(layoutMetadataDao.getLayoutMetadata(anyString())).thenReturn(List.of());
        when(comboBoxMetadataDao.getComboBoxesByFormMetadataId(anyLong())).thenReturn(List.of());
        when(jdbcTemplate.queryForObject(anyString(), anyMap(), eq(RowMapper.class))).thenReturn((rs, rowNum) -> metadata);

        dao.getViewMetadata("main", "en");

        verify(formMetadataMapper).getSql();
        verify(jdbcTemplate).queryForObject(any(), anyMap(), any(RowMapper.class));
    }
}
