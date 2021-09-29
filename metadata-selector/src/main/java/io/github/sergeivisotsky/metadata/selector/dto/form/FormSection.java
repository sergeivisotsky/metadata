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

package io.github.sergeivisotsky.metadata.selector.dto.form;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class FormSection {

    private String name;
    private String uiName;
    private String uiDescription;
    private Integer orderInForm;
    private FormSectionCardinality cardinality;
    private List<FormField> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public String getUiDescription() {
        return uiDescription;
    }

    public void setUiDescription(String uiDescription) {
        this.uiDescription = uiDescription;
    }

    public Integer getOrderInForm() {
        return orderInForm;
    }

    public void setOrderInForm(Integer orderInForm) {
        this.orderInForm = orderInForm;
    }

    public FormSectionCardinality getCardinality() {
        return cardinality;
    }

    public void setCardinality(FormSectionCardinality cardinality) {
        this.cardinality = cardinality;
    }

    public List<FormField> getFields() {
        return fields;
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }

}
