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

package io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.config;

import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.ProcedureParser;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.SelectParser;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.impl.PrimitiveProcedureParser;
import io.github.sergeivisotsky.metadata.selector.jdbc.sqlparser.impl.PrimitiveSelectParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergei Visotsky
 */
@Configuration
public class SQLParserConfig {

    @Bean
    public ProcedureParser procedureParser() {
        return new PrimitiveProcedureParser();
    }

    @Bean
    public SelectParser selectParser() {
        return new PrimitiveSelectParser();
    }
}
