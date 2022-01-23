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

package io.github.sergeivisotsky.metadata.engine.domain.form;

/**
 * @author Sergei Visotsky
 */
public class LookupInfo {

    private LookupType lookupType;
    private String viewName;
    private String valueField;
    private String textInField;
    private Boolean isTextEnabled;
    private String comboTextTemplate;

    public LookupType getLookupType() {
        return lookupType;
    }

    public void setLookupType(LookupType lookupType) {
        this.lookupType = lookupType;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getTextInField() {
        return textInField;
    }

    public void setTextInField(String textInField) {
        this.textInField = textInField;
    }

    public Boolean getTextEnabled() {
        return isTextEnabled;
    }

    public void setTextEnabled(Boolean textEnabled) {
        isTextEnabled = textEnabled;
    }

    public String getComboTextTemplate() {
        return comboTextTemplate;
    }

    public void setComboTextTemplate(String comboTextTemplate) {
        this.comboTextTemplate = comboTextTemplate;
    }
}
