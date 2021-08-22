package org.sergei.metadata.selector.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.sergei.metadata.selector.dao.MetadataDao;
import org.sergei.metadata.selector.dto.Area;
import org.sergei.metadata.selector.dto.FormMetadata;
import org.sergei.metadata.selector.dto.Language;
import org.sergei.metadata.selector.dto.Layout;
import org.sergei.metadata.selector.dto.ViewField;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MetadataDaoImpl implements MetadataDao {

    private static final String QUERY_FORM_METADATA = "SELECT fm.form_name,\n" +
            "       fm.cardinality,\n" +
            "       fm.language,\n" +
            "       fm.offset,\n" +
            "       fm.padding,\n" +
            "       fm.font,\n" +
            "       fm.font_size,\n" +
            "       fm.description,\n" +
            "       vf.enabled_by_default,\n" +
            "       vf.ui_control\n" +
            "FROM form_metadata fm\n" +
            "         LEFT JOIN view_field vf on fm.id = vf.form_metadata_id\n" +
            "WHERE fm.form_name = :formName\n" +
            "  AND fm.language = :lang";

    private static final String QUERY_LAYOUT_METADATA = "SELECT l.area,\n" +
            "       l.weight,\n" +
            "       l.height,\n" +
            "       l.font,\n" +
            "       l.font_size\n" +
            "FROM layout l\n" +
            "WHERE l.form_name = :formName";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MetadataDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        Map<String, Object> params = Map.of(
                "formName", formName,
                "lang", lang
        );
        return jdbcTemplate.queryForObject(QUERY_FORM_METADATA, params, (rs, index) -> FormMetadata.builder()
                .formName(rs.getString("form_name"))
                .cardinality(rs.getString("cardinality"))
                .lang(Language.valueOf(rs.getString("language")
                        .toUpperCase(Locale.ROOT)))
                .offset(rs.getInt("offset"))
                .padding(rs.getInt("padding"))
                .font(rs.getString("font"))
                .fontSize(rs.getInt("font_size"))
                .description(rs.getString("description"))
                .viewField(ViewField.builder()
                        .enabledByDefault(rs.getInt("enabled_by_default"))
                        .uiControl(rs.getString("ui_control"))
                        .build())
                .layouts(getLayoutMetadata(formName))
                .build());
    }

    private List<Layout> getLayoutMetadata(String formName) {
        Map<String, String> params = Map.of("formName", formName);
        return jdbcTemplate.query(QUERY_LAYOUT_METADATA, params, (rs, index) -> Layout.builder()
                .font(rs.getString("font"))
                .fontSize(rs.getInt("font_size"))
                .area(Area.valueOf(rs.getString("area")))
                .weight(rs.getInt("weight"))
                .height(rs.getInt("height"))
                .build());
    }
}
