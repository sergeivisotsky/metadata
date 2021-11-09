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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.selector.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.Layout;
import io.github.sergeivisotsky.metadata.selector.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.ProcedureMetadataMapper;

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
    @Override
    public List<Layout> getLayoutMetadata(String viewName) {
        try {
            Map<String, Object> params = Map.of("viewName", viewName);
            return checkLogicType(
                    () -> layoutMapper,
                    () -> executeQuery(params, layoutMapper),
                    () -> {
                        try (Connection connection = dataSource.getConnection();
                             CallableStatement stmt = connection.prepareCall(layoutMapper.getSql())) {

                            stmt.setString(0, viewName);
                            return ((ProcedureMetadataMapper<Layout>) layoutMapper).executeAndMap(stmt);
                        } catch (Exception e) {
                            throw new MetadataStorageException(e, "Failure to get view metadata with the following " +
                                    "parameters: viewName={}", viewName);
                        }
                    });
        } catch (Exception e) {
            throw new MetadataStorageException(e, "Unable to get a layout metadata with the " +
                    "following parameters: viewName={}", viewName);
        }
    }
}
