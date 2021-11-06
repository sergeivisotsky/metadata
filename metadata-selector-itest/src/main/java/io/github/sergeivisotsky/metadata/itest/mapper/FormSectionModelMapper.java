package io.github.sergeivisotsky.metadata.itest.mapper;

import io.github.sergeivisotsky.metadata.itest.domain.ExtendedFormSection;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormSection;
import io.github.sergeivisotsky.metadata.selector.mapper.ModelMapper;

public class FormSectionModelMapper implements ModelMapper<FormSection, FormSection> {

    @Override
    public FormSection apply(FormSection section) {
        ExtendedFormSection resultSection = new ExtendedFormSection();
        resultSection.setFields(section.getFields());
        resultSection.setName(section.getName());
        resultSection.setParentSectionName(section.getParentSectionName());
        resultSection.setCardinality(section.getCardinality());
        resultSection.setOrderInForm(section.getOrderInForm());
        resultSection.setUiDescription(section.getUiDescription());
        resultSection.setUiName(section.getUiName());
        return resultSection;
    }
}