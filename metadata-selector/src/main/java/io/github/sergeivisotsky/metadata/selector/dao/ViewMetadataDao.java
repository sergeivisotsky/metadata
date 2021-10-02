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

package io.github.sergeivisotsky.metadata.selector.dao;

import io.github.sergeivisotsky.metadata.selector.dto.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormMetadata;

/**
 * Interface to be considered both API and SPI for a metadata retrieval functionality.
 * In general do not supposed to be fully SPIed apart from this a corresponding
 * metadata DAO class should be extended and enhanced to support an additional fields.
 *
 * @author Sergei Visotsky
 */
public interface ViewMetadataDao {

    /**
     * Gets the all metadata.
     *
     * @param viewName name of the view to retrieve metadata for.
     * @param lang     language on which metadata should be handled.
     * @return metadata DTO.
     */
    ViewMetadata getViewMetadata(String viewName, String lang);

}
