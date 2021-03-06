package io.github.sergeivisotsky.metadata.engine.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.engine.itest.domain.ExtendedFormSection;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormSection;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormSectionCardinality;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

public class FormSectionMapper implements MetadataMapper<FormSection> {

    @Override
    public String getSql() {
        return "SELECT fs.id,\n" +
                "       fs.name,\n" +
                "       ParentSection.name AS parent_section_name,\n" +
                "       fs.cardinality,\n" +
                "       fs.order_in_form,\n" +
                "       tr.ui_name,\n" +
                "       tr.ui_description\n" +
                "FROM form_section fs\n" +
                "         LEFT JOIN form_section ParentSection ON fs.parent_section_id = ParentSection.id\n" +
                "         JOIN amd_translation tr ON tr.form_name = fs.name\n" +
                "         JOIN form_metadata fm on fs.form_metadata_id = fm.id\n" +
                "WHERE fs.name = :formName\n" +
                "  AND tr.lang = :lang";
    }

    @Override
    public FormSection map(ResultSet rs) {
        try {
            ExtendedFormSection formSection = new ExtendedFormSection();
            formSection.setName(rs.getString("name"));
            formSection.setParentSectionName(rs.getString("parent_section_name"));
            formSection.setOrderInForm(rs.getInt("order_in_form"));
            formSection.setCardinality(FormSectionCardinality.valueOf(rs.getString("cardinality")));
            formSection.setUiName(rs.getString("ui_name"));
            formSection.setUiDescription(rs.getString("ui_description"));
            return formSection;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    FormSectionMapper.class.getSimpleName(), e);
        }
    }
}
