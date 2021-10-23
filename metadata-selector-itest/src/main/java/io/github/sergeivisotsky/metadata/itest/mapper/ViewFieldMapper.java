package io.github.sergeivisotsky.metadata.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.itest.domain.ExtendedViewField;
import io.github.sergeivisotsky.metadata.selector.domain.FieldType;
import io.github.sergeivisotsky.metadata.selector.domain.ViewField;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

public class ViewFieldMapper implements MetadataMapper<ViewField> {

    @Override
    public String getSql() {
        return "SELECT vf.name,\n" +
                "       vf.enabled_by_default,\n" +
                "       vf.ui_control,\n" +
                "       vf.field_type,\n" +
                "       vf.ui_description,\n" +
                "       vf.ui_query_name,\n" +
                "       vf.ui_query_from_text,\n" +
                "       vf.ui_query_to_text,\n" +
                "       vf.ui_column_name,\n" +
                "       vf.ui_column_renderer,\n" +
                "       vf.can_show_in_query,\n" +
                "       vf.can_show_in_table,\n" +
                "       vf.show_in_query,\n" +
                "       vf.show_in_table,\n" +
                "       vf.include_in_select,\n" +
                "       vf.order_in_query,\n" +
                "       vf.order_in_table\n" +
                "FROM view_field vf\n" +
                "WHERE view_metadata_id = :viewId";
    }

    @Override
    public ExtendedViewField map(ResultSet rs) {
        try {
            ExtendedViewField viewField = new ExtendedViewField();

            viewField.setName(rs.getString("name"));
            viewField.setFieldType(FieldType.valueOf(rs.getString("field_type")));
            viewField.setUiDescription(rs.getString("ui_description"));
            viewField.setUiQueryName(rs.getString("ui_query_name"));
            viewField.setUiQueryFromText(rs.getString("ui_query_from_text"));
            viewField.setUiQueryToText(rs.getString("ui_query_to_text"));
            viewField.setUiColumnName(rs.getString("ui_column_name"));
            viewField.setUiColumnRenderer(rs.getString("ui_column_renderer"));
            viewField.setCanShowInTable(rs.getBoolean("can_show_in_table"));
            viewField.setCanShowInQuery(rs.getBoolean("can_show_in_query"));
            viewField.setShowInTable(rs.getBoolean("show_in_table"));
            viewField.setShowInQuery(rs.getBoolean("show_in_query"));
            viewField.setIncludeInSelect(rs.getBoolean("include_in_select"));
            viewField.setOrderInQuery(rs.getInt("order_in_query"));
            viewField.setOrderInTable(rs.getInt("order_in_table"));
            viewField.setEnabledByDefault(rs.getInt("enabled_by_default"));
            viewField.setUiControl(rs.getString("ui_control"));

            return viewField;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    ViewFieldMapper.class.getSimpleName(), e);
        }
    }
}
