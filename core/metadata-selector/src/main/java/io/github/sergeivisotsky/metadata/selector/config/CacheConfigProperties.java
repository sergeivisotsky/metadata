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

package io.github.sergeivisotsky.metadata.selector.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cache")
public class CacheConfigProperties {

    private Integer initialCapacity;
    private Integer maximumSize;
    private Integer expireAfterAccess;
    private TimeUnit expirationAfterAccessUnits;

    public Integer getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(Integer initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Integer getExpireAfterAccess() {
        return expireAfterAccess;
    }

    public void setExpireAfterAccess(Integer expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
    }

    public TimeUnit getExpirationAfterAccessUnits() {
        return expirationAfterAccessUnits;
    }

    public void setExpirationAfterAccessUnits(TimeUnit expirationAfterAccessUnits) {
        this.expirationAfterAccessUnits = expirationAfterAccessUnits;
    }
}
