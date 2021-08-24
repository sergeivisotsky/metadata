package org.sergei.metadata.selector.config;

import java.sql.ResultSet;

import org.sergei.metadata.selector.MetadataMapper;
import org.sergei.metadata.selector.dao.MetadataDao;
import org.sergei.metadata.selector.dao.impl.CacheableMetadataDao;
import org.sergei.metadata.selector.dao.impl.MetadataDaoImpl;
import org.sergei.metadata.selector.dto.FormMetadata;
import org.sergei.metadata.selector.dto.Layout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Sergei Visotsky
 */
@Configuration
@ComponentScan(basePackages = {
        "org.sergei.metadata.*"
})
public class MetadataSelectorConfig {

    @Bean
    public MetadataDao metadataDao(NamedParameterJdbcTemplate jdbcTemplate,
                                   MetadataMapper<ResultSet, FormMetadata> formMetadataMapper,
                                   MetadataMapper<ResultSet, Layout> layoutMapper) {
        return new CacheableMetadataDao(new MetadataDaoImpl(jdbcTemplate, formMetadataMapper, layoutMapper));
    }
}
