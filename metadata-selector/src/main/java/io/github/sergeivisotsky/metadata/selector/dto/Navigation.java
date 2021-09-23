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

package io.github.sergeivisotsky.metadata.selector.dto;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class Navigation {

    private Long id;
    private NavigationType type;
    private Integer numberOfElements;
    private Boolean fixed;
    private Boolean resizable;
    private List<NavigationElement> elements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NavigationType getType() {
        return type;
    }

    public void setType(NavigationType type) {
        this.type = type;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Boolean getResizable() {
        return resizable;
    }

    public void setResizable(Boolean resizable) {
        this.resizable = resizable;
    }

    public List<NavigationElement> getElements() {
        return elements;
    }

    public void setElements(List<NavigationElement> elements) {
        this.elements = elements;
    }
}
