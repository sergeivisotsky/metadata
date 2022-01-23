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

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public class ComboBox {

    private Long id;
    // Codifier in this case is supposed to be a code
    // which will be handled on UI to place a combo-box
    // in a proper place. OOTB form is CD_001
    private String codifier;
    private String font;
    private Integer fontSize;
    private Integer weight;
    private Integer height;
    private Boolean displayable;
    private Boolean immutable;
    private List<ComboBoxContent> comboContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodifier() {
        return codifier;
    }

    public void setCodifier(String codifier) {
        this.codifier = codifier;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Boolean displayable) {
        this.displayable = displayable;
    }

    public Boolean getImmutable() {
        return immutable;
    }

    public void setImmutable(Boolean immutable) {
        this.immutable = immutable;
    }

    public List<ComboBoxContent> getComboContent() {
        return comboContent;
    }

    public void setComboContent(List<ComboBoxContent> comboContent) {
        this.comboContent = comboContent;
    }
}
