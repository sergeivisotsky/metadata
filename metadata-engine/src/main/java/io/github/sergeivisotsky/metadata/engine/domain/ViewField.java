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

package io.github.sergeivisotsky.metadata.engine.domain;

/**
 * @author Sergei Visotsky
 */
public class ViewField {

    private String name;
    private FieldType fieldType;
    private String uiDescription;
    private String uiQueryName;
    private String uiQueryFromText;
    private String uiQueryToText;
    private String uiColumnName;
    private String uiColumnRenderer;
    private Boolean canShowInQuery;
    private Boolean canShowInTable;
    private Boolean showInQuery;
    private Boolean showInTable;
    private Boolean includeInSelect;
    private Integer orderInQuery;
    private Integer orderInTable;
    private Integer enabledByDefault;
    private String uiControl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getUiDescription() {
        return uiDescription;
    }

    public void setUiDescription(String uiDescription) {
        this.uiDescription = uiDescription;
    }

    public String getUiQueryName() {
        return uiQueryName;
    }

    public void setUiQueryName(String uiQueryName) {
        this.uiQueryName = uiQueryName;
    }

    public String getUiQueryFromText() {
        return uiQueryFromText;
    }

    public void setUiQueryFromText(String uiQueryFromText) {
        this.uiQueryFromText = uiQueryFromText;
    }

    public String getUiQueryToText() {
        return uiQueryToText;
    }

    public void setUiQueryToText(String uiQueryToText) {
        this.uiQueryToText = uiQueryToText;
    }

    public String getUiColumnName() {
        return uiColumnName;
    }

    public void setUiColumnName(String uiColumnName) {
        this.uiColumnName = uiColumnName;
    }

    public String getUiColumnRenderer() {
        return uiColumnRenderer;
    }

    public void setUiColumnRenderer(String uiColumnRenderer) {
        this.uiColumnRenderer = uiColumnRenderer;
    }

    public Boolean isCanShowInQuery() {
        return canShowInQuery;
    }

    public void setCanShowInQuery(Boolean canShowInQuery) {
        this.canShowInQuery = canShowInQuery;
    }

    public Boolean isCanShowInTable() {
        return canShowInTable;
    }

    public void setCanShowInTable(Boolean canShowInTable) {
        this.canShowInTable = canShowInTable;
    }

    public Boolean isShowInQuery() {
        return showInQuery;
    }

    public void setShowInQuery(Boolean showInQuery) {
        this.showInQuery = showInQuery;
    }

    public Boolean isShowInTable() {
        return showInTable;
    }

    public void setShowInTable(Boolean showInTable) {
        this.showInTable = showInTable;
    }

    public Boolean isIncludeInSelect() {
        return includeInSelect;
    }

    public void setIncludeInSelect(Boolean includeInSelect) {
        this.includeInSelect = includeInSelect;
    }

    public Integer getOrderInQuery() {
        return orderInQuery;
    }

    public void setOrderInQuery(Integer orderInQuery) {
        this.orderInQuery = orderInQuery;
    }

    public Integer getOrderInTable() {
        return orderInTable;
    }

    public void setOrderInTable(Integer orderInTable) {
        this.orderInTable = orderInTable;
    }

    public Integer getEnabledByDefault() {
        return enabledByDefault;
    }

    public void setEnabledByDefault(Integer enabledByDefault) {
        this.enabledByDefault = enabledByDefault;
    }

    public String getUiControl() {
        return uiControl;
    }

    public void setUiControl(String uiControl) {
        this.uiControl = uiControl;
    }
}
