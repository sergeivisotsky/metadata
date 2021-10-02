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

import java.util.Map;

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.ComboBoxMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.NavigationMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class ViewMetadataDaoImpl extends AbstractMetadataDao implements ViewMetadataDao {

    private final MetadataMapper<ViewMetadata> formMetadataMapper;
    private final ComboBoxMetadataDao comboBoxMetadataDao;
    private final LayoutMetadataDao layoutMetadataDao;
    private final NavigationMetadataDao navigationMetadataDao;

    public ViewMetadataDaoImpl(MetadataMapper<ViewMetadata> formMetadataMapper,
                               ComboBoxMetadataDao comboBoxMetadataDao,
                               LayoutMetadataDao layoutMetadataDao,
                               NavigationMetadataDao navigationMetadataDao) {
        this.formMetadataMapper = formMetadataMapper;
        this.comboBoxMetadataDao = comboBoxMetadataDao;
        this.layoutMetadataDao = layoutMetadataDao;
        this.navigationMetadataDao = navigationMetadataDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewMetadata getViewMetadata(String viewName, String lang) {
        Map<String, Object> params = Map.of(
                "viewName", viewName,
                "lang", lang
        );
        return jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                (rs, index) -> {
                    ViewMetadata metadata = formMetadataMapper.map(rs);
                    metadata.setLayouts(layoutMetadataDao.getLayoutMetadata(viewName));
                    metadata.setComboBoxes(comboBoxMetadataDao
                            .getComboBoxesByFormMetadataId(rs.getLong("id")));
                    metadata.setNavigation(navigationMetadataDao.getNavigationMetadata(viewName));
                    return metadata;
                });
    }

}
