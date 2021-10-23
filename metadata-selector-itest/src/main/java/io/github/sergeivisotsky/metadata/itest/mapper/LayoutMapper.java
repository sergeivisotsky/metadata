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

import io.github.sergeivisotsky.metadata.itest.domain.ExtendedLayout;
import io.github.sergeivisotsky.metadata.selector.domain.Area;
import io.github.sergeivisotsky.metadata.selector.domain.Layout;
import io.github.sergeivisotsky.metadata.selector.mapper.MetadataMapper;

public class LayoutMapper implements MetadataMapper<Layout> {

    @Override
    public String getSql() {
        return "SELECT l.area,\n" +
                "       l.weight,\n" +
                "       l.height,\n" +
                "       l.font,\n" +
                "       l.font_size\n" +
                "FROM layout l\n" +
                "WHERE l.view_name = :viewName";
    }

    @Override
    public ExtendedLayout map(ResultSet rs) {
        try {
            ExtendedLayout layout = new ExtendedLayout();
            layout.setFont(rs.getString("font"));
            layout.setFontSize(rs.getInt("font_size"));
            layout.setArea(Area.valueOf(rs.getString("area")));
            layout.setWeight(rs.getInt("weight"));
            layout.setHeight(rs.getInt("height"));
            return layout;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    LayoutMapper.class.getSimpleName(), e);
        }
    }
}
