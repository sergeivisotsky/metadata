package io.github.sergeivisotsky.metadata.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.itest.domain.ExtendedFormMetadata;
import io.github.sergeivisotsky.metadata.selector.domain.LogicType;
import io.github.sergeivisotsky.metadata.selector.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

import static io.github.sergeivisotsky.metadata.selector.domain.LogicType.FUNCTION;

public class FormMetadataMapper implements MetadataMapper<FormMetadata> {

    @Override
    public String getSql() {
        return "SELECT f.id,\n" +
                "       f.name,\n" +
                "       tr.ui_name,\n" +
                "       tr.ui_description\n" +
                "FROM form_metadata f\n" +
                "         LEFT JOIN amd_translation tr on tr.form_name = f.name\n" +
                "WHERE f.name = :formName\n" +
                "  AND tr.lang = :lang";
    }

    @Override
    public FormMetadata map(ResultSet rs) {
        try {
            ExtendedFormMetadata metadata = new ExtendedFormMetadata();
            metadata.setId(rs.getLong("id"));
            metadata.setName(rs.getString("name"));
            metadata.setUiName(rs.getString("ui_name"));
            metadata.setUiDescription(rs.getString("ui_description"));
            return metadata;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    FormMetadataMapper.class.getSimpleName(), e);
        }
    }

    @Override
    public LogicType logicType() {
        return FUNCTION;
    }
}
