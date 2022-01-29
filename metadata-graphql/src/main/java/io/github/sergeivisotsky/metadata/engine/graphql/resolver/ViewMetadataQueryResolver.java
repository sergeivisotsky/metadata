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
package io.github.sergeivisotsky.metadata.engine.graphql.resolver;

import io.github.sergeivisotsky.metadata.graphql.model.ViewMetadataTO;
import io.github.sergeivisotsky.metadata.graphql.model.ViewQueryResultResponseTO;
import io.github.sergeivisotsky.metadata.graphql.resolver.GetViewMetadataQueryResolver;
import io.github.sergeivisotsky.metadata.graphql.resolver.QueryQueryResolver;

/**
 * @author Sergei Visotsky
 */
public class ViewMetadataQueryResolver implements GetViewMetadataQueryResolver, QueryQueryResolver {
    @Override
    public ViewMetadataTO getViewMetadata(String viewName, String lang) throws Exception {
        return null;
    }

    @Override
    public ViewQueryResultResponseTO query(String viewName, String lang) throws Exception {
        return null;
    }
}
