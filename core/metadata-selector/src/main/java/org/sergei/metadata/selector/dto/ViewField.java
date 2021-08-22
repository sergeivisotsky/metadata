package org.sergei.metadata.selector.dto;

public final class ViewField {

    private final Integer enabledByDefault;
    private final String uiControl;

    ViewField(ViewFieldBuilder builder) {
        this.enabledByDefault = builder.enabledByDefault;
        this.uiControl = builder.uiControl;
    }

    public static ViewFieldBuilder builder() {
        return new ViewFieldBuilder();
    }

    public Integer getEnabledByDefault() {
        return this.enabledByDefault;
    }

    public String getUiControl() {
        return this.uiControl;
    }

    public static class ViewFieldBuilder {
        private Integer enabledByDefault;
        private String uiControl;

        ViewFieldBuilder() {
        }

        public ViewFieldBuilder enabledByDefault(Integer enabledByDefault) {
            this.enabledByDefault = enabledByDefault;
            return this;
        }

        public ViewFieldBuilder uiControl(String uiControl) {
            this.uiControl = uiControl;
            return this;
        }

        public ViewField build() {
            return new ViewField(this);
        }
    }
}
