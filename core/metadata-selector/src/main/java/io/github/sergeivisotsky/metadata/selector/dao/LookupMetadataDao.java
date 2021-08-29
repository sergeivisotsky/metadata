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

import io.github.sergeivisotsky.metadata.selector.dto.LookupHolder;

/**
 * An interface to get a metadata persisted data for a lookup values to be displayed.
 *
 * @author Sergei Visotsky
 */
public interface LookupMetadataDao {

    /**
     * Get a lookup metadata by lookup name;
     *
     * @param lookupName name of the lookup to get data for.
     * @param lang       language on which lookup values should be returned.
     * @return lookup metadata holder.
     */
    LookupHolder getLookupMetadata(String lookupName, String lang);
}
