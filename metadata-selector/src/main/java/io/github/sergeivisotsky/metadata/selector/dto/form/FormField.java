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

/**
 * @author Sergei Visotsky
 */
public class FormField {

    private String name;
    private FieldType type;
    private Integer length;
    private Integer decimalScale;
    private Boolean editable;
    private String formSectionName;
    private UIControlType uiControlType;
    private String uiName;
    private String uiDescription;
    private Boolean hasLanguage;
    private Integer order;
    private Boolean controlsDynamicChange;
    private LookupInfo lookupInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDecimalScale() {
        return decimalScale;
    }

    public void setDecimalScale(Integer decimalScale) {
        this.decimalScale = decimalScale;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getFormSectionName() {
        return formSectionName;
    }

    public void setFormSectionName(String formSectionName) {
        this.formSectionName = formSectionName;
    }

    public UIControlType getUiControlType() {
        return uiControlType;
    }

    public void setUiControlType(UIControlType uiControlType) {
        this.uiControlType = uiControlType;
    }

    public String getUiDescription() {
        return uiDescription;
    }

    public void setUiDescription(String uiDescription) {
        this.uiDescription = uiDescription;
    }

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public Boolean getHasLanguage() {
        return hasLanguage;
    }

    public void setHasLanguage(Boolean hasLanguage) {
        this.hasLanguage = hasLanguage;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getControlsDynamicChange() {
        return controlsDynamicChange;
    }

    public void setControlsDynamicChange(Boolean controlsDynamicChange) {
        this.controlsDynamicChange = controlsDynamicChange;
    }

    public LookupInfo getLookupInfo() {
        return lookupInfo;
    }

    public void setLookupInfo(LookupInfo lookupInfo) {
        this.lookupInfo = lookupInfo;
    }
}
