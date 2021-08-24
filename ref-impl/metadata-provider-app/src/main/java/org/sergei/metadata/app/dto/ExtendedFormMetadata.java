package org.sergei.metadata.app.dto;

import org.sergei.metadata.selector.dto.FormMetadata;

public class ExtendedFormMetadata extends FormMetadata {

    private String facet;

    public String getFacet() {
        return facet;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }
}
