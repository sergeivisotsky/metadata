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
package io.github.sergeivisotsky.metadata.selector.config.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Root cache configuration property holder class.
 *
 * @author Sergei Visotsky
 */
public class RootCacheConfigProperties {

    @NestedConfigurationProperty
    private CacheConfigProperties view;

    @NestedConfigurationProperty
    private CacheConfigProperties form;

    @NestedConfigurationProperty
    private CacheConfigProperties query;

    public CacheConfigProperties getView() {
        return view;
    }

    public void setView(CacheConfigProperties view) {
        this.view = view;
    }

    public CacheConfigProperties getForm() {
        return form;
    }

    public void setForm(CacheConfigProperties form) {
        this.form = form;
    }

    public CacheConfigProperties getQuery() {
        return query;
    }

    public void setQuery(CacheConfigProperties query) {
        this.query = query;
    }
}
