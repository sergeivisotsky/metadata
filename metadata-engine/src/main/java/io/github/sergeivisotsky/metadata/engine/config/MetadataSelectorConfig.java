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

package io.github.sergeivisotsky.metadata.engine.config;

import java.util.List;

import io.github.sergeivisotsky.metadata.engine.dao.ComboBoxMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.LookupMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.NavigationMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.engine.dao.impl.ComboBoxMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.dao.impl.FormMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.dao.impl.LayoutMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.dao.impl.LookupMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.dao.impl.NavigationMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.dao.impl.ViewMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.dao.impl.ViewQueryDaoImpl;
import io.github.sergeivisotsky.metadata.engine.domain.ComboBox;
import io.github.sergeivisotsky.metadata.engine.domain.Layout;
import io.github.sergeivisotsky.metadata.engine.domain.LookupHolder;
import io.github.sergeivisotsky.metadata.engine.domain.LookupMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.Navigation;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormField;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormSection;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.SQLDialect;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.engine.mapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergei Visotsky
 */
@Configuration
@ComponentScan(basePackages = {
        "io.github.sergeivisotsky.metadata.*"
})
public class MetadataSelectorConfig {

    @Bean
    public ViewMetadataDao viewMetadataDao(MetadataMapper<ViewField> viewFieldMetadataMapper,
                                           MetadataMapper<ViewMetadata> formMetadataMapper,
                                           ComboBoxMetadataDao comboBoxMetadataDao,
                                           LayoutMetadataDao layoutMetadataDao,
                                           NavigationMetadataDao navigationMetadataDao) {
        return new ViewMetadataDaoImpl(viewFieldMetadataMapper, formMetadataMapper, comboBoxMetadataDao, layoutMetadataDao, navigationMetadataDao);
    }

    @Bean
    @ConditionalOnMissingBean
    public LayoutMetadataDao layoutMetadataDao(MetadataMapper<Layout> layoutMapper) {
        return new LayoutMetadataDaoImpl(layoutMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public ComboBoxMetadataDao comboBoxMetadataDao(MetadataMapper<ComboBox> comboBoxMapper) {
        return new ComboBoxMetadataDaoImpl(comboBoxMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public LookupMetadataDao lookupMetadataDao(MetadataMapper<LookupHolder> lookupHolderMapper,
                                               MetadataMapper<LookupMetadata> lookupMetadataMapper) {
        return new LookupMetadataDaoImpl(lookupHolderMapper, lookupMetadataMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public NavigationMetadataDao navigationMetadataDao(MetadataMapper<List<Navigation>> navigationMapper) {
        return new NavigationMetadataDaoImpl(navigationMapper);
    }

    @Bean
    public FormMetadataDao formMetadataDao(MetadataMapper<FormMetadata> formMetadataMapper,
                                           MetadataMapper<FormSection> formSectionMapper,
                                           MetadataMapper<FormField> formFieldMapper,
                                           ModelMapper<FormSection, FormSection> formSectionModelMapper) {
        return new FormMetadataDaoImpl(formMetadataMapper, formSectionMapper, formFieldMapper, formSectionModelMapper);
    }

    @Bean
    public ViewQueryDao viewQueryDao(SQLDialect sqlDialect) {
        return new ViewQueryDaoImpl(sqlDialect);
    }
}
