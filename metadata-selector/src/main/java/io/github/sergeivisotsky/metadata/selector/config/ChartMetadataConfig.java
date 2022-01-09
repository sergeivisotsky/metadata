package io.github.sergeivisotsky.metadata.selector.config;

import io.github.sergeivisotsky.metadata.selector.dao.ChartMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dao.impl.ChartMetadataDaoImpl;
import io.github.sergeivisotsky.metadata.selector.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.rest.ChartMetadataController;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ConditionalOnProperty(prefix = "metadata.active",
        name = "chart", havingValue = "true")
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
