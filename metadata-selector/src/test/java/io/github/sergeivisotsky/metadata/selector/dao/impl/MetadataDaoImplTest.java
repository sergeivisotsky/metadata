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

import java.util.ArrayList;
import java.util.List;

import io.github.sergeivisotsky.metadata.selector.dto.ComboBox;
import io.github.sergeivisotsky.metadata.selector.dto.ComboBoxContent;
import io.github.sergeivisotsky.metadata.selector.dto.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.Layout;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergei Visotsky
 */
public class MetadataDaoImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<FormMetadata> formMetadataMapper;

    @Mock
    private MetadataMapper<Layout> layoutMapper;

    @Mock
    private MetadataMapper<ComboBox> comboBoxMapper;

    @InjectMocks
    private MetadataDaoImpl metadataDao;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldObfuscateCombos() {
        List<ComboBox> comboBoxes = new ArrayList<>();

        ComboBox comboBoxOne = new ComboBox();
        comboBoxOne.setId(1L);
        comboBoxOne.setComboContent(List.of(new ComboBoxContent("firstKey", "firstValue", 1L)));

        comboBoxes.add(comboBoxOne);

        ComboBox comboBoxTwo = new ComboBox();
        comboBoxTwo.setId(1L);
        comboBoxTwo.setComboContent(List.of(new ComboBoxContent("secondKey", "SecondValue", 1L)));

        comboBoxes.add(comboBoxTwo);

        ComboBox comboBoxThree = new ComboBox();
        comboBoxThree.setId(1L);
        comboBoxThree.setComboContent(List.of(new ComboBoxContent("thirdKey", "thirdValue", 1L)));

        comboBoxes.add(comboBoxThree);

        List<ComboBox> resultCombos = metadataDao.normalizeCombos(comboBoxes);

        assertEquals(1, resultCombos.size());
        assertEquals(3, resultCombos.get(0).getComboContent().size());
    }
}
