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

package io.github.sergeivisotsky.metadata.engine.dao;

import java.util.List;

import io.github.sergeivisotsky.metadata.engine.domain.Layout;

/**
 * A layout metadata access object.
 *
 * @author Sergei Visotsky
 */
public interface LayoutMetadataDao {

    /**
     * Performs the main logic of getting a layout metadata.
     *
     * @param viewName view for which a metadata is being queried.
     * @return a collection of all layouts containing a metadata for each.
     */
    List<Layout> getLayoutMetadata(String viewName);
}
