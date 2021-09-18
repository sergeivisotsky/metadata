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

package io.github.sergeivisotsky.metadata.selector.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Test;

/**
 * @author Sergei Visotsky
 */
public class MetadataDaoImplTest {

    @Test
    public void testExecuteFunction() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres");
             CallableStatement stmt = conn.prepareCall("{? = call get_form_metadata(?, ?)}")) {

            stmt.registerOutParameter(1, Types.REF_CURSOR);
            stmt.setString(2, "main");
            stmt.setString(3, "en");

            stmt.execute();

        }
    }

}
