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
import java.util.stream.Collectors;

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.NavigationMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.Navigation;
import io.github.sergeivisotsky.metadata.selector.dto.NavigationElement;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class NavigationMetadataDaoImpl extends AbstractMetadataDao implements NavigationMetadataDao {

    private final MetadataMapper<List<Navigation>> navigationMapper;

    public NavigationMetadataDaoImpl(MetadataMapper<List<Navigation>> navigationMapper) {
        this.navigationMapper = navigationMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Navigation getNavigationMetadata(String viewName) {
        Map<String, Object> params = Map.of("viewName", viewName);
        return jdbcTemplate.queryForObject(navigationMapper.getSql(), params,
                (rs, index) -> normalizeNavigationMetadata(navigationMapper.map(rs)));
    }

    private Navigation normalizeNavigationMetadata(List<Navigation> originalNav) {
        Navigation navigation = originalNav.get(0);

        List<NavigationElement> navElements = originalNav
                .stream()
                .flatMap(nav -> nav.getElements().stream())
                .collect(Collectors.toList());
        navigation.getElements().clear();

        navigation.setElements(navElements);
        return navigation;
    }
}
