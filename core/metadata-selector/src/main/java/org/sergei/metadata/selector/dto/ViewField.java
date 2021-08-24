package org.sergei.metadata.selector.dto;

/**
 * @author Sergei Visotsky
 */
public class ViewField {

    private Integer enabledByDefault;
    private String uiControl;

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
