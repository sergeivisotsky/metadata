package io.github.sergeivisotsky.metadata.engine.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.engine.itest.domain.ExtendedChartMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartType;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

public class ChartMetadataMapper implements MetadataMapper<ChartMetadata> {

    @Override
    public String getSql() {
        return "SELECT c.id,\n" +
                "       c.name,\n" +
                "       c.gap,\n" +
                "       c.spacing,\n" +
                "       c.type,\n" +
                "       c.inactive_opacity\n" +
                "FROM chart c\n" +
                "WHERE name = :chartName";
    }

    @Override
    public ChartMetadata map(ResultSet rs) {
        try {
            ExtendedChartMetadata metadata = new ExtendedChartMetadata();

            metadata.setName(rs.getString("name"));
            metadata.setGap(rs.getInt("gap"));
            metadata.setSpacing(rs.getDouble("spacing"));
            metadata.setType(ChartType.valueOf(rs.getString("type")));
            metadata.setInactiveOpacity(rs.getDouble("inactive_opacity"));

            return metadata;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    ChartMetadata.class.getSimpleName(), e);
        }
    }
}
