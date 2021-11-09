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
import io.github.sergeivisotsky.metadata.selector.dao.ComboBoxMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.NavigationMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.ProcedureParser;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.ProcedureMetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.SQLMetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class ViewMetadataDaoImpl extends AbstractMetadataDao implements ViewMetadataDao {

    private final MetadataMapper<ViewField> viewFieldMetadataMapper;
    private final MetadataMapper<ViewMetadata> formMetadataMapper;
    private final ComboBoxMetadataDao comboBoxMetadataDao;
    private final LayoutMetadataDao layoutMetadataDao;
    private final NavigationMetadataDao navigationMetadataDao;

    public ViewMetadataDaoImpl(MetadataMapper<ViewField> viewFieldMetadataMapper,
                               MetadataMapper<ViewMetadata> formMetadataMapper,
                               ComboBoxMetadataDao comboBoxMetadataDao,
                               LayoutMetadataDao layoutMetadataDao,
                               NavigationMetadataDao navigationMetadataDao) {
        this.viewFieldMetadataMapper = viewFieldMetadataMapper;
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
        return checkLogicType(
                () -> formMetadataMapper,
                () -> List.of(executeViewQuery(params)),
                () -> List.of(executeViewProcedure(params))
        );
    }

    private ViewMetadata executeViewProcedure(Map<String, Object> params) {
        String procedure = formMetadataMapper.getSql();

        String viewName = (String) params.get("viewName");
        String lang = (String) params.get("lang");
        try (Connection connection = dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall(procedure)) {

            stmt.setString(0, viewName);
            stmt.setString(1, lang);
            return ((ProcedureMetadataMapper<ViewMetadata>) formMetadataMapper).executeAndMap(stmt);
        } catch (Exception e) {
            throw new MetadataStorageException(e, "Failure to get view metadata with the following " +
                    "parameters: viewName={} and lang={}", viewName, lang);
        }
    }

    private ViewMetadata executeViewQuery(Map<String, Object> params) {
        String viewName = (String) params.get("viewName");
        return jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                (rs, index) -> {
                    Long viewId = rs.getLong("id");

                    ViewMetadata metadata = ((SQLMetadataMapper<ViewMetadata>) formMetadataMapper).map(rs);
                    metadata.setLayouts(layoutMetadataDao.getLayoutMetadata(viewName));
                    metadata.setViewField(getViewFields(viewId));
                    metadata.setComboBoxes(comboBoxMetadataDao.getComboBoxesByFormMetadataId(viewId));
                    metadata.setNavigation(navigationMetadataDao.getNavigationMetadata(viewName));

                    return metadata;
                }
        );
    }

    private List<ViewField> getViewFields(Long viewId) {
        Map<String, Object> params = Map.of("viewId", viewId);
        return checkLogicType(
                () -> viewFieldMetadataMapper,
                () -> jdbcTemplate.query(viewFieldMetadataMapper.getSql(), params,
                        (rs, index) -> ((SQLMetadataMapper<ViewField>) viewFieldMetadataMapper).map(rs)),
                () -> {
                    String procedure = viewFieldMetadataMapper.getSql();
                    try (Connection connection = dataSource.getConnection();
                         CallableStatement stmt = connection.prepareCall(procedure)) {

                        stmt.setLong(0, viewId);
                        return List.of(((ProcedureMetadataMapper<ViewField>) viewFieldMetadataMapper).executeAndMap(stmt));
                    } catch (Exception e) {
                        throw new MetadataStorageException(e, "Failure to get view metadata with the following " +
                                "parameters: viewId={}", viewId);
                    }
                });
    }
}
