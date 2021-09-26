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
import io.github.sergeivisotsky.metadata.selector.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormField;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormSection;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class FormMetadataDaoImpl extends AbstractMetadataDao implements FormMetadataDao {

    private final MetadataMapper<FormMetadata> formMetadataMapper;
    private final MetadataMapper<FormSection> formSectionMapper;
    private final MetadataMapper<FormField> formFieldMapper;

    public FormMetadataDaoImpl(MetadataMapper<FormMetadata> formMetadataMapper,
                               MetadataMapper<FormSection> formSectionMapper,
                               MetadataMapper<FormField> formFieldMapper) {
        this.formMetadataMapper = formMetadataMapper;
        this.formSectionMapper = formSectionMapper;
        this.formFieldMapper = formFieldMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormMetadata getFormMetadata(String lang, String formName) {
        Map<String, Object> params = Map.of(
                "lang", lang,
                "formName", formName
        );

        List<FormField> formFields = jdbcTemplate.query(formSectionMapper.getSql(), params,
                (rs, index) -> formFieldMapper.map(rs));

        List<FormSection> formSections = jdbcTemplate.query(formSectionMapper.getSql(), params,
                (rs, index) -> {
                    FormSection section = formSectionMapper.map(rs);
                    section.setFields(formFields
                            .stream()
                            .collect(Collectors.groupingBy(FormField::getFormSectionName))
                            .getOrDefault(rs.getString("name"), List.of()));
                    return section;
                });

        return jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                (rs, index) -> {
                    FormMetadata metadata = formMetadataMapper.map(rs);
                    metadata.setSections(formSections);
                    return metadata;
                });
    }
}
