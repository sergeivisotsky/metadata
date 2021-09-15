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
import io.github.sergeivisotsky.metadata.selector.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.Layout;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class LayoutMetadataDaoImpl extends AbstractMetadataDao implements LayoutMetadataDao {

    private final MetadataMapper<Layout> layoutMapper;

    public LayoutMetadataDaoImpl(MetadataMapper<Layout> layoutMapper) {
        this.layoutMapper = layoutMapper;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public List<Layout> getLayoutMetadata(String formName) {
        Map<String, Object> params = Map.of("formName", formName);
        return checkLogicType(
                layoutMapper::logicType,
                () -> executeQuery(params, layoutMapper),
                () -> jdbcCall
                        .withFunctionName(layoutMapper.getSql())
                        .executeFunction(List.class)
        );
    }
}
