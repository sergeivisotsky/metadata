package org.sergei.metadata.selector.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.sergei.metadata.selector.Mapper;
import org.sergei.metadata.selector.dao.MetadataDao;
import org.sergei.metadata.selector.dto.FormMetadata;
import org.sergei.metadata.selector.dto.Layout;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MetadataDaoImpl implements MetadataDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Mapper<ResultSet, FormMetadata> formMetadataMapper;
    private final Mapper<ResultSet, Layout> layoutMapper;

    public MetadataDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,
                           Mapper<ResultSet, FormMetadata> formMetadataMapper,
                           Mapper<ResultSet, Layout> layoutMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.formMetadataMapper = formMetadataMapper;
        this.layoutMapper = layoutMapper;
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        Map<String, Object> params = Map.of(
                "formName", formName,
                "lang", lang
        );
        return jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                (rs, index) -> {
                    FormMetadata metadata = formMetadataMapper.apply(rs);
                    metadata.setLayouts(getLayoutMetadata(formName));
                    return metadata;
                });
    }

    private List<Layout> getLayoutMetadata(String formName) {
        Map<String, String> params = Map.of("formName", formName);
        return jdbcTemplate.query(layoutMapper.getSql(), params, (rs, index) -> layoutMapper.apply(rs));
    }
}
