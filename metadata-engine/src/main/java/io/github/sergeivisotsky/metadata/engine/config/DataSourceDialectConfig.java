/*
 * Copyright 2022 the original author or authors.
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

import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.MSSQLDialect;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.MySQLDialect;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.OracleSQLDialect;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.PostgreSQLDialect;
import io.github.sergeivisotsky.metadata.engine.jdbc.sqlgen.dialect.SQLDialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataSource dialect configuration.
 *
 * @author Sergei Visotsky
 */
@Configuration
public class DataSourceDialectConfig {

    @Bean
    @ConditionalOnProperty(
            prefix = "datasource",
            name = "sql-dialect",
            havingValue = "oracle"
    )
    public SQLDialect oracleSqlDialect() {
        return new OracleSQLDialect();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "datasource",
            name = "sql-dialect",
            havingValue = "postgresql"
    )
    public SQLDialect postgreSqlDialect() {
        return new PostgreSQLDialect();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "datasource",
            name = "sql-dialect",
            havingValue = "mssql"
    )
    public SQLDialect msSqlDialect() {
        return new MSSQLDialect();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "datasource",
            name = "sql-dialect",
            havingValue = "mysql"
    )
    public SQLDialect mySqlDialect() {
        return new MySQLDialect();
    }
}
