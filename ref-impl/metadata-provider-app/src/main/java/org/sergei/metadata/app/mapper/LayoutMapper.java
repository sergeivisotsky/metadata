package org.sergei.metadata.app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sergei.metadata.app.dto.ExtendedLayout;
import org.sergei.metadata.selector.MetadataMapper;
import org.sergei.metadata.selector.dto.Area;
import org.sergei.metadata.selector.dto.Layout;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class LayoutMapper implements MetadataMapper<ResultSet, Layout> {

    @Override
    public String getSql() {
        return "SELECT l.area,\n" +
                "       l.weight,\n" +
                "       l.height,\n" +
                "       l.font,\n" +
                "       l.font_size\n" +
                "FROM layout l\n" +
                "WHERE l.form_name = :formName";
    }

    @Override
    public ExtendedLayout apply(ResultSet rs) {
        try {
            ExtendedLayout layout = new ExtendedLayout();
            layout.setFont(rs.getString("font"));
            layout.setFontSize(rs.getInt("font_size"));
            layout.setArea(Area.valueOf(rs.getString("area")));
            layout.setWeight(rs.getInt("weight"));
            layout.setHeight(rs.getInt("height"));
            return layout;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    LayoutMapper.class.getSimpleName(), e);
        }
    }
}
