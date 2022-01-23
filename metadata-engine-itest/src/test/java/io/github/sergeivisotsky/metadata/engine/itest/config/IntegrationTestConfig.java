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

package io.github.sergeivisotsky.metadata.engine.itest.config;

import javax.sql.DataSource;

import io.github.sergeivisotsky.metadata.engine.config.properties.DataSourceConfigProperties;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.PostgreSQLDialect;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.SQLDialect;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Sergei Visotsky
 */
@Configuration
public class IntegrationTestConfig {

    private final DataSourceConfigProperties dbProperties;
    private final String liquibaseChangeLog;

    public IntegrationTestConfig(DataSourceConfigProperties dbProperties,
                                 @Value("${spring.liquibase.change-log}") String liquibaseChangeLog) {
        this.dbProperties = dbProperties;
        this.liquibaseChangeLog = liquibaseChangeLog;
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.failOnEmptyBeans(false);
        return builder;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(dbProperties.getUrl());
        dataSource.setDriverClassName(dbProperties.getDriverClassName());
        dataSource.setUsername(dbProperties.getUsername());
        dataSource.setPassword(dbProperties.getPassword());

        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog(liquibaseChangeLog);
        return liquibase;
    }

    @Bean
    public SQLDialect sqlDialect() {
        return new PostgreSQLDialect();
    }
}
