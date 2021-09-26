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
import io.github.sergeivisotsky.metadata.selector.dto.form.FormField;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormSection;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.ModelMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Sergei Visotsky
 */
public class FormMetadataDaoImplTest extends AbstractMetadataDao {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<FormMetadata> formMetadataMapper;

    @Mock
    private MetadataMapper<FormSection> formSectionMapper;

    @Mock
    private MetadataMapper<FormField> formFieldMapper;

    @Mock
    private ModelMapper<FormSection, FormSection> formSectionModelMapper;

    @InjectMocks
    private FormMetadataDaoImpl dao;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetFormMetadata() {
        FormSection sectionOne = new FormSection();
        sectionOne.setUiName("someUi");
        sectionOne.setUiDescription("some ui description");
        sectionOne.setName("feedback");
        sectionOne.setParentSectionName(null);


        FormSection sectionTwo = new FormSection();
        sectionTwo.setUiName("someUi");
        sectionTwo.setUiDescription("some ui description");
        sectionTwo.setName("feedbackTwo");
        sectionTwo.setParentSectionName("feedback");


        FormSection sectionThree = new FormSection();
        sectionThree.setUiName("someUi");
        sectionThree.setUiDescription("some ui description");
        sectionThree.setName("feedbackThree");
        sectionThree.setParentSectionName("feedback");

        List<FormSection> sections = List.of(sectionOne, sectionTwo, sectionThree);

        List<FormSection> hierarchyList = dao.getHierarchyList(sections);

        System.out.println(hierarchyList);
    }
}
