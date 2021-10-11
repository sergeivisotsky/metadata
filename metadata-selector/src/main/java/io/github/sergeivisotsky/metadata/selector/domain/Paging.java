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

package io.github.sergeivisotsky.metadata.selector.domain;

/**
 * @author Sergei Visotsky
 */
public class Paging {

    private final Long totalElements;
    private final Long offset;
    private final Boolean hasMoreElements;

    Paging(Long totalElements, Long offset, Boolean hasMoreElements) {
        this.totalElements = totalElements;
        this.offset = offset;
        this.hasMoreElements = hasMoreElements;
    }

    public static PagingBuilder builder() {
        return new PagingBuilder();
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Long getOffset() {
        return offset;
    }

    public Boolean getHasMoreElements() {
        return hasMoreElements;
    }

    public static class PagingBuilder {
        private Long totalElements;
        private Long offset;
        private Boolean hasMoreElements;

        PagingBuilder() {
        }

        public PagingBuilder totalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PagingBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }

        public PagingBuilder hasMoreElements(Boolean hasMoreElements) {
            this.hasMoreElements = hasMoreElements;
            return this;
        }

        public Paging build() {
            return new Paging(totalElements, offset, hasMoreElements);
        }
    }
}
