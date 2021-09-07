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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.sergeivisotsky.metadata.selector.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.dao.MetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.ComboBox;
import io.github.sergeivisotsky.metadata.selector.dto.ComboBoxContent;
import io.github.sergeivisotsky.metadata.selector.dto.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.Layout;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Sergei Visotsky
 */
public class MetadataDaoImpl implements MetadataDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MetadataMapper<ResultSet, FormMetadata> formMetadataMapper;
    private final MetadataMapper<ResultSet, Layout> layoutMapper;
    private final MetadataMapper<ResultSet, ComboBox> comboBoxMapper;

    public MetadataDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,
                           MetadataMapper<ResultSet, FormMetadata> formMetadataMapper,
                           MetadataMapper<ResultSet, Layout> layoutMapper,
                           MetadataMapper<ResultSet, ComboBox> comboBoxMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.formMetadataMapper = formMetadataMapper;
        this.layoutMapper = layoutMapper;
        this.comboBoxMapper = comboBoxMapper;
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        Map<String, Object> params = Map.of(
                "formName", formName,
                "lang", lang
        );
        return jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                (rs, index) -> {
                    FormMetadata metadata = formMetadataMapper.apply(rs);
                    metadata.setLayouts(getLayoutMetadata(formName));
                    metadata.setComboBoxes(getComboBoxes(rs.getLong("id")));
                    return metadata;
                });
    }

    private List<Layout> getLayoutMetadata(String formName) {
        Map<String, Object> params = Map.of("formName", formName);
        return executeQuery(params, layoutMapper);
    }

    private List<ComboBox> getComboBoxes(Long id) {
        Map<String, Object> params = Map.of("formMetadataId", id);
        List<ComboBox> combos = jdbcTemplate.query(comboBoxMapper.getSql(), params, (rs, index) -> {
            ComboBox comboBox = comboBoxMapper.apply(rs);
            List<ComboBoxContent> comboContent = new ArrayList<>();
            comboContent.add(new ComboBoxContent(
                    rs.getString("content_key"),
                    rs.getString("content_value"),
                    rs.getLong("id")));
            comboBox.setComboContent(comboContent);
            return comboBox;
        });
        return normalizeCombos(combos);
    }

    protected List<ComboBox> normalizeCombos(List<ComboBox> combos) {
        List<ComboBox> resultingCombos = new ArrayList<>();
        List<ComboBoxContent> resultingComboContent = combos.stream()
                .flatMap(combo -> combo.getComboContent()
                        .stream()
                        .filter(comboContent -> combo.getId().equals(comboContent.getComboId())))
                .collect(Collectors.toList());
        for (int i = 0; i < combos.size() - 1; i++) {
            if (combos.get(i).getId().equals(combos.get(i++).getId())) {
                ComboBox resultingCombo = combos.get(i);
                resultingCombo.setComboContent(resultingComboContent
                        .stream()
                        .filter(cont -> cont.getComboId().equals(resultingCombo.getId()))
                        .collect(Collectors.toList()));
                resultingCombos.add(resultingCombo);
            }
        }
        return resultingCombos;
    }

    private <T> List<T> executeQuery(Map<String, Object> params, MetadataMapper<ResultSet, T> mapper) {
        return jdbcTemplate.query(mapper.getSql(), params, (rs, index) -> mapper.apply(rs));
    }
}
