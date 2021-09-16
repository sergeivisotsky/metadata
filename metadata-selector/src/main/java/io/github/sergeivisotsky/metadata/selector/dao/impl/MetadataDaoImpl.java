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
import io.github.sergeivisotsky.metadata.selector.dao.MetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class MetadataDaoImpl extends AbstractMetadataDao implements MetadataDao {

    private final MetadataMapper<FormMetadata> formMetadataMapper;
    private final ComboBoxMetadataDao comboBoxMetadataDao;
    private final LayoutMetadataDao layoutMetadataDao;

    public MetadataDaoImpl(MetadataMapper<FormMetadata> formMetadataMapper,
                           ComboBoxMetadataDao comboBoxMetadataDao,
                           LayoutMetadataDao layoutMetadataDao) {
        this.formMetadataMapper = formMetadataMapper;
        this.comboBoxMetadataDao = comboBoxMetadataDao;
        this.layoutMetadataDao = layoutMetadataDao;
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        Map<String, Object> params = Map.of(
                "formName", formName,
                "lang", lang
        );
        return checkLogicType(
                formMetadataMapper::logicType,
                () -> jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                        (rs, index) -> {
                            FormMetadata metadata = formMetadataMapper.map(rs);
                            metadata.setLayouts(layoutMetadataDao.getLayoutMetadata(formName));
                            metadata.setComboBoxes(comboBoxMetadataDao
                                    .getComboBoxesByFormMetadataId(rs.getLong("id")));
                            return metadata;
                        }),
                formMetadataMapper::executeFunction
        );
    }

}
