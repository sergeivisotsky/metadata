/*
 * Copyright 2021 the original author or authors.
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

package io.github.sergeivisotsky.metadata.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import io.github.sergeivisotsky.metadata.itest.dto.ExtendedViewMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.ViewMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.Language;
import io.github.sergeivisotsky.metadata.selector.dto.ViewField;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class FormMetadataMapper implements MetadataMapper<ViewMetadata> {

    @Override
    public String getSql() {
        return "SELECT fm.id,\n" +
                "       fm.form_name,\n" +
                "       fm.cardinality,\n" +
                "       fm.language,\n" +
                "       fm.\"offset\",\n" +
                "       fm.padding,\n" +
                "       fm.font,\n" +
                "       fm.font_size,\n" +
                "       fm.description,\n" +
                "       vf.enabled_by_default,\n" +
                "       vf.ui_control\n" +
                "FROM form_metadata fm\n" +
                "         LEFT JOIN view_field vf on fm.id = vf.form_metadata_id\n" +
                "WHERE fm.form_name = :formName\n" +
                "  AND fm.language = :lang";
    }

    @Override
    public ExtendedViewMetadata map(ResultSet rs) {
        try {
            ExtendedViewMetadata metadata = new ExtendedViewMetadata();
            metadata.setViewName(rs.getString("form_name"));
            metadata.setCardinality(rs.getString("cardinality"));
            metadata.setLang(Language.valueOf(rs.getString("language")
                    .toUpperCase(Locale.ROOT)));
            metadata.setOffset(rs.getInt("offset"));
            metadata.setPadding(rs.getInt("padding"));
            metadata.setFont(rs.getString("font"));
            metadata.setFontSize(rs.getInt("font_size"));
            metadata.setDescription(rs.getString("description"));
            ViewField viewField = new ViewField();
            viewField.setEnabledByDefault(rs.getInt("enabled_by_default"));
            viewField.setUiControl(rs.getString("ui_control"));
            metadata.setViewField(viewField);
            return metadata;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    FormMetadataMapper.class.getSimpleName(), e);
        }
    }
}
