package org.sergei.metadata.app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.sergei.metadata.app.dto.ExtendedFormMetadata;
import org.sergei.metadata.selector.MetadataMapper;
import org.sergei.metadata.selector.dto.FormMetadata;
import org.sergei.metadata.selector.dto.Language;
import org.sergei.metadata.selector.dto.ViewField;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class FormMetadataMapper implements MetadataMapper<ResultSet, FormMetadata> {

    @Override
    public String getSql() {
        return "SELECT fm.form_name,\n" +
                "       fm.cardinality,\n" +
                "       fm.language,\n" +
                "       fm.offset,\n" +
                "       fm.padding,\n" +
                "       fm.font,\n" +
                "       fm.font_size,\n" +
                "       fm.description,\n" +
                "       fm.facet,\n" +
                "       vf.enabled_by_default,\n" +
                "       vf.ui_control\n" +
                "FROM form_metadata fm\n" +
                "         LEFT JOIN view_field vf on fm.id = vf.form_metadata_id\n" +
                "WHERE fm.form_name = :formName\n" +
                "  AND fm.language = :lang";
    }

    @Override
    public ExtendedFormMetadata apply(ResultSet rs) {
        try {
            ExtendedFormMetadata metadata = new ExtendedFormMetadata();
            metadata.setFormName(rs.getString("form_name"));
            metadata.setCardinality(rs.getString("cardinality"));
            metadata.setLang(Language.valueOf(rs.getString("language")
                    .toUpperCase(Locale.ROOT)));
            metadata.setOffset(rs.getInt("offset"));
            metadata.setPadding(rs.getInt("padding"));
            metadata.setFont(rs.getString("font"));
            metadata.setFontSize(rs.getInt("font_size"));
            metadata.setDescription(rs.getString("description"));
            ViewField viewField = new ViewField();
            viewField.setEnabledByDefault(rs.getInt("enabled_by_default"));
            viewField.setUiControl(rs.getString("ui_control"));
            metadata.setViewField(viewField);
            metadata.setFacet(rs.getString("facet"));
            return metadata;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    FormMetadataMapper.class.getSimpleName(), e);
        }
    }
}
