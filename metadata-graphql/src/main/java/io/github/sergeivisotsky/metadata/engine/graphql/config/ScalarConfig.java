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
package io.github.sergeivisotsky.metadata.engine.graphql.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring {@link Configuration} for scalars.
 *
 * @author Sergei Visotsky
 */
@Configuration
public class ScalarConfig {

    @Bean
    public GraphQLScalarType objectScalar() {
        return ExtendedScalars.Object;
    }

    @Bean
    public GraphQLScalarType longScalar() {
        return ExtendedScalars.GraphQLLong;
    }

    @Bean
    public GraphQLScalarType doubleScalar() {
        return GraphQLScalarType.newScalar()
                .name("Double")
                .description("Double scalar")
                .coercing(new Coercing<Double, Double>() {
                    @Override
                    public Double serialize(Object input) {
                        return (Double) input;
                    }

                    @Override
                    public Double parseValue(Object input) {
                        return (Double) input;
                    }

                    @Override
                    public Double parseLiteral(Object input) {
                        return (Double) input;
                    }
                }).build();
    }
}
