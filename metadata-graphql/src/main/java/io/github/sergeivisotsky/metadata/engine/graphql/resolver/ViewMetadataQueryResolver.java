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

import io.github.sergeivisotsky.metadata.engine.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.graphql.model.ViewMetadataDto;
import io.github.sergeivisotsky.metadata.graphql.resolver.GetViewMetadataQueryResolver;

/**
 * @author Sergei Visotsky
 */
public class ViewMetadataQueryResolver implements GetViewMetadataQueryResolver {

    private final ViewMetadataDao metadataDao;

    public ViewMetadataQueryResolver(ViewMetadataDao metadataDao) {
        this.metadataDao = metadataDao;
    }

    @Override
    public ViewMetadataDto getViewMetadata(String viewName, String lang) throws Exception {
        // TODO: Here should be mapper...
        //  Mapper should be customizable...
        //  Meaning, should be in preconfig and interface here.
//        return metadataDao.getViewMetadata(viewName, lang);
        return null;
    }

}
