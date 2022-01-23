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

package io.github.sergeivisotsky.metadata.engine.domain.chart;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class ChartMetadata {

    private String name;
    private ChartType type;
    private Double inactiveOpacity;
    private Integer gap;
    private Double spacing;
    private List<Category> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChartType getType() {
        return type;
    }

    public void setType(ChartType type) {
        this.type = type;
    }

    public Double getInactiveOpacity() {
        return inactiveOpacity;
    }

    public void setInactiveOpacity(Double inactiveOpacity) {
        this.inactiveOpacity = inactiveOpacity;
    }

    public Integer getGap() {
        return gap;
    }

    public void setGap(Integer gap) {
        this.gap = gap;
    }

    public Double getSpacing() {
        return spacing;
    }

    public void setSpacing(Double spacing) {
        this.spacing = spacing;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
