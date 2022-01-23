package io.github.sergeivisotsky.metadata.engine.itest.config;

import java.util.List;

import io.github.sergeivisotsky.metadata.engine.itest.mapper.ChartMetadataMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.ComboBoxMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.FormFieldMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.FormMetadataMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.FormSectionMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.FormSectionModelMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.LayoutMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.LookupHolderMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.LookupMetadataMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.NavigationMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.ViewFieldMapper;
import io.github.sergeivisotsky.metadata.engine.itest.mapper.ViewMetadataMapper;
import io.github.sergeivisotsky.metadata.engine.domain.ComboBox;
import io.github.sergeivisotsky.metadata.engine.domain.Layout;
import io.github.sergeivisotsky.metadata.engine.domain.LookupHolder;
import io.github.sergeivisotsky.metadata.engine.domain.LookupMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.Navigation;
import io.github.sergeivisotsky.metadata.engine.domain.ViewField;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormField;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormSection;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import io.github.sergeivisotsky.metadata.engine.mapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public MetadataMapper<ViewMetadata> metadataMetadataMapper() {
        return new ViewMetadataMapper();
    }

    @Bean
    public MetadataMapper<ComboBox> comboBoxMetadataMapper() {
        return new ComboBoxMapper();
    }

    @Bean
    public MetadataMapper<Layout> layoutMetadataMapper() {
        return new LayoutMapper();
    }

    @Bean
    public MetadataMapper<LookupHolder> lookupHolderMetadataMapper() {
        return new LookupHolderMapper();
    }

    @Bean
    public MetadataMapper<LookupMetadata> lookupMetadataMetadataMapper() {
        return new LookupMetadataMapper();
    }

    @Bean
    public MetadataMapper<List<Navigation>> navigationMapper() {
        return new NavigationMapper();
    }

    @Bean
    public MetadataMapper<FormField> formFieldMetadataMapper() {
        return new FormFieldMapper();
    }

    @Bean
    public MetadataMapper<FormSection> formSectionMetadataMapper() {
        return new FormSectionMapper();
    }

    @Bean
    public MetadataMapper<FormMetadata> formMetadataMetadataMapper() {
        return new FormMetadataMapper();
    }

    @Bean
    public ModelMapper<FormSection, FormSection> formSectionModelMapper() {
        return new FormSectionModelMapper();
    }

    @Bean
    public MetadataMapper<ChartMetadata> chartMetadataMetadataMapper() {
        return new ChartMetadataMapper();
    }

    @Bean
    public MetadataMapper<ViewField> viewFieldMetadataMapper() {
        return new ViewFieldMapper();
    }
}
