package io.github.sergeivisotsky.metadata.engine.itest.config;

import io.github.sergeivisotsky.metadata.engine.dao.ChartMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.impl.ChartMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.engine.rest.ChartMetadataController;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class ChartMetadataConfig {

    @Bean
    public ChartMetadataController chartMetadataController(AutowireCapableBeanFactory beanFactory,
                                                           MetadataMapper<ChartMetadata> chartMetadataMapper) {
        @RestController
        class ChartMetadataControllerBean extends ChartMetadataController {
            public ChartMetadataControllerBean(ChartMetadataDao chartMetadataDao) {
                super(chartMetadataDao);
            }
        }
        ChartMetadataControllerBean bean = new ChartMetadataControllerBean(chartMetadataDao(chartMetadataMapper));
        beanFactory.autowireBean(bean);
        return bean;
    }

    @Bean
    public ChartMetadataDao chartMetadataDao(MetadataMapper<ChartMetadata> chartMetadataMapper) {
        return new ChartMetadataDaoImpl(chartMetadataMapper);
    }
}
