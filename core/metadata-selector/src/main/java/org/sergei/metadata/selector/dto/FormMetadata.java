package org.sergei.metadata.selector.dto;

import java.util.List;

public class FormMetadata {

    private String cardinality;
    private Language lang;
    private String formName;
    private Integer offset;
    private Integer padding;
    private String font;
    private Integer fontSize;
    private String description;
    private ViewField viewField;
    private List<Layout> layouts;

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPadding() {
        return padding;
    }

    public void setPadding(Integer padding) {
        this.padding = padding;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ViewField getViewField() {
        return viewField;
    }

    public void setViewField(ViewField viewField) {
        this.viewField = viewField;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
