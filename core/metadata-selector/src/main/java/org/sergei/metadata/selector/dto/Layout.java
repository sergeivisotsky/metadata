package org.sergei.metadata.selector.dto;

public final class Layout {

    private final Area area;
    private final Integer weight;
    private final Integer height;
    private final String font;
    private final Integer fontSize;

    Layout(LayoutBuilder builder) {
        this.area = builder.area;
        this.weight = builder.weight;
        this.height = builder.height;
        this.font = builder.font;
        this.fontSize = builder.fontSize;
    }

    public Area getArea() {
        return area;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public String getFont() {
        return font;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public static LayoutBuilder builder() {
        return new LayoutBuilder();
    }

    public static class LayoutBuilder {
        private Area area;
        private Integer weight;
        private Integer height;
        private String font;
        private Integer fontSize;

        LayoutBuilder() {
        }

        public LayoutBuilder area(Area area) {
            this.area = area;
            return this;
        }

        public LayoutBuilder weight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public LayoutBuilder height(Integer height) {
            this.height = height;
            return this;
        }

        public LayoutBuilder font(String font) {
            this.font = font;
            return this;
        }

        public LayoutBuilder fontSize(Integer fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Layout build() {
            return new Layout(this);
        }
    }
}
