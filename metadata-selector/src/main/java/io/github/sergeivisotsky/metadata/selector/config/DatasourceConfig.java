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

import javax.sql.DataSource;

import io.github.sergeivisotsky.metadata.selector.config.properties.DataSourceConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Sergei Visotsky
 */
@Configuration
public class DatasourceConfig {

    private final DataSourceConfigProperties configProperties;

    public DatasourceConfig(DataSourceConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(configProperties.getDriverClassName());
        dataSource.setUrl(configProperties.getUrl());
        dataSource.setUsername(configProperties.getUsername());
        dataSource.setPassword(configProperties.getPassword());

        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public NamedParameterJdbcTemplate namedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
