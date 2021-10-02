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

package io.github.sergeivisotsky.metadata.selector.dao.keys;

import java.util.Objects;

/**
 * @author Sergei Visotsky
 */
public class FormMetadataCacheKey extends MetadataCacheKey {
    private final String formName;
    private final String lang;

    public FormMetadataCacheKey(String formName, String lang) {
        this.formName = formName;
        this.lang = lang;
    }

    public String getFormName() {
        return formName;
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
        FormMetadataCacheKey cacheKey = (FormMetadataCacheKey) obj;
        return formName.equals(cacheKey.formName) && lang.equals(cacheKey.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formName, lang);
    }
}
