package io.github.sergeivisotsky.metadata.engine.config;

import io.github.sergeivisotsky.metadata.engine.dao.ChartMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.impl.ChartMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "metadata.active",
        name = "chart", havingValue = "true")
public class ChartMetadataConfig {

    @Bean
    public ChartMetadataDao chartMetadataDao(MetadataMapper<ChartMetadata> chartMetadataMapper) {
        return new ChartMetadataDaoImpl(chartMetadataMapper);
    }
}
