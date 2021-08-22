package org.sergei.metadata.selector.dto;

import java.util.List;

public final class FormMetadata {

    private final String cardinality;
    private final Language lang;
    private final String formName;
    private final Integer offset;
    private final Integer padding;
    private final String font;
    private final Integer fontSize;
    private final String description;
    private final ViewField viewField;
    private final List<Layout> layouts;

    FormMetadata(FormMetadataBuilder builder) {
        this.cardinality = builder.cardinality;
        this.formName = builder.formName;
        this.lang = builder.lang;
        this.offset = builder.offset;
        this.padding = builder.padding;
        this.font = builder.font;
        this.fontSize = builder.fontSize;
        this.description = builder.description;
        this.viewField = builder.viewField;
        this.layouts = builder.layouts;
    }

    public String getCardinality() {
        return cardinality;
    }

    public String getFormName() {
        return formName;
    }

    public Language getLang() {
        return lang;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getPadding() {
        return padding;
    }

    public String getFont() {
        return font;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public String getDescription() {
        return description;
    }

    public ViewField getViewField() {
        return viewField;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public static FormMetadataBuilder builder() {
        return new FormMetadataBuilder();
    }

    public static class FormMetadataBuilder {
        private String cardinality;
        private String formName;
        private Language lang;
        private Integer offset;
        private Integer padding;
        private String font;
        private Integer fontSize;
        private String description;
        private ViewField viewField;
        private List<Layout> layouts;

        FormMetadataBuilder() {
        }

        public FormMetadataBuilder cardinality(String cardinality) {
            this.cardinality = cardinality;
            return this;
        }

        public FormMetadataBuilder formName(String formName) {
            this.formName = formName;
            return this;
        }

        public FormMetadataBuilder lang(Language lang) {
            this.lang = lang;
            return this;
        }

        public FormMetadataBuilder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public FormMetadataBuilder padding(Integer padding) {
            this.padding = padding;
            return this;
        }

        public FormMetadataBuilder font(String font) {
            this.font = font;
            return this;
        }

        public FormMetadataBuilder fontSize(Integer fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public FormMetadataBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FormMetadataBuilder viewField(ViewField viewField) {
            this.viewField = viewField;
            return this;
        }

        public FormMetadataBuilder layouts(List<Layout> layouts) {
            this.layouts = layouts;
            return this;
        }

        public FormMetadata build() {
            return new FormMetadata(this);
        }
    }
}
