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

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.Navigation;
import io.github.sergeivisotsky.metadata.selector.domain.NavigationElement;
import io.github.sergeivisotsky.metadata.selector.domain.NavigationType;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link NavigationMetadataDaoImpl}.
 *
 * @author Sergei Visotsky
 */
class NavigationMetadataDaoImplTest extends AbstractMetadataDao {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<List<Navigation>> navigationMapper;

    @InjectMocks
    private NavigationMetadataDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetNavigationMetadata() {
        //given
        Navigation navigation = new Navigation();
        navigation.setType(NavigationType.NAV_BAR);
        navigation.setResizable(false);
        navigation.setFixed(true);
        navigation.setNumberOfElements(5);

        NavigationElement element = new NavigationElement();
        element.setCode("ELEM_123");
        element.setValue("Main page");
        element.setActive(true);

        navigation.setElements(List.of(element));

        final String mockSql = "SELECT * FROM some_table WHERE id = 1";
        when(navigationMapper.getSql()).thenReturn(mockSql);
        when(jdbcTemplate.queryForObject(anyString(), anyMap(), eq(RowMapper.class)))
                .thenReturn((rs, rowNum) -> navigation);

        //when
        dao.getNavigationMetadata("someView");

        //then
        verify(navigationMapper).getSql();
        verify(jdbcTemplate).queryForObject(any(), anyMap(), any(RowMapper.class));
    }
}
