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

package io.github.sergeivisotsky.metadata.selector.dao.key;

import java.util.Objects;

/**
 * @author Sergei Visotsky
 */
public class ViewMetadataCacheKey extends MetadataCacheKey {
    private final String viewName;
    private final String lang;

    public ViewMetadataCacheKey(String viewName, String lang) {
        this.viewName = viewName;
        this.lang = lang;
    }

    public String getViewName() {
        return viewName;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ViewMetadataCacheKey that = (ViewMetadataCacheKey) obj;
        return viewName.equals(that.viewName) && lang.equals(that.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName, lang);
    }
}
