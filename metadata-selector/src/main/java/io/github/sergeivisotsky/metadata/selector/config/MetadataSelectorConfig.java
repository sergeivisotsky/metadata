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

package io.github.sergeivisotsky.metadata.selector.config;

import java.util.List;

import io.github.sergeivisotsky.metadata.selector.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.selector.dao.ComboBoxMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.LookupMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.NavigationMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.impl.CacheableMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.impl.ComboBoxMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dao.impl.FormMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dao.impl.LayoutMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dao.impl.LookupMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dao.impl.NavigationMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.dao.impl.ViewMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.domain.ComboBox;
import io.github.sergeivisotsky.metadata.selector.domain.Layout;
import io.github.sergeivisotsky.metadata.selector.domain.LookupHolder;
import io.github.sergeivisotsky.metadata.selector.domain.LookupMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.Navigation;
import io.github.sergeivisotsky.metadata.selector.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormField;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormSection;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.mapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @ConditionalOnMissingBean
    public ViewMetadataDao metadataDao(@Qualifier("simpleMetadataDao") ViewMetadataDao viewMetadataDao,
                                       FormMetadataDao formMetadataDao,
                                       CacheConfigProperties cacheConfigProperties) {
        return new CacheableMetadataDao(viewMetadataDao, cacheConfigProperties, formMetadataDao);
    }

    @Bean("simpleMetadataDao")
    public ViewMetadataDao simpleMetadataDao(MetadataMapper<ViewMetadata> formMetadataMapper,
                                             ComboBoxMetadataDao comboBoxMetadataDao,
                                             LayoutMetadataDao layoutMetadataDao,
                                             NavigationMetadataDao navigationMetadataDao) {
        return new ViewMetadataDaoImpl(formMetadataMapper, comboBoxMetadataDao, layoutMetadataDao, navigationMetadataDao);
    }

    @Bean
    public LayoutMetadataDao layoutMetadataDao(MetadataMapper<Layout> layoutMapper) {
        return new LayoutMetadataDaoImpl(layoutMapper);
    }

    @Bean
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
}
