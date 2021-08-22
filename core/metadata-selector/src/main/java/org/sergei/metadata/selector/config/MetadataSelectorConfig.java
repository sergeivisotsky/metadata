package org.sergei.metadata.selector.config;

import org.sergei.metadata.selector.dao.MetadataDao;
import org.sergei.metadata.selector.dao.impl.CacheableMetadataDao;
import org.sergei.metadata.selector.dao.impl.MetadataDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages = {
        "org.sergei.metadata.*"
})
public class MetadataSelectorConfig {

    @Bean
    public MetadataDao metadataDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new CacheableMetadataDao(new MetadataDaoImpl(jdbcTemplate));
    }
}
