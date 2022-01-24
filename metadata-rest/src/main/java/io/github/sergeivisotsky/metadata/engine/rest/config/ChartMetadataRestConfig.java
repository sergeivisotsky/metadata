/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.sergeivisotsky.metadata.engine.rest.config;

import io.github.sergeivisotsky.metadata.engine.dao.ChartMetadataDao;
import io.github.sergeivisotsky.metadata.engine.rest.ChartMetadataController;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chart metadata REST services config.
 *
 * @author Sergei Visotsky
 */
@Configuration
@ConditionalOnProperty(prefix = "metadata.active",
        name = "chart", havingValue = "true")
public class ChartMetadataRestConfig {

    @Bean
    public ChartMetadataController chartMetadataController(AutowireCapableBeanFactory beanFactory,
                                                           ChartMetadataDao chartMetadataDao) {
        @RestController
        class ChartMetadataControllerBean extends ChartMetadataController {
            public ChartMetadataControllerBean(ChartMetadataDao chartMetadataDao) {
                super(chartMetadataDao);
            }
        }
        ChartMetadataControllerBean bean = new ChartMetadataControllerBean(chartMetadataDao);
        beanFactory.autowireBean(bean);
        return bean;
    }
}
