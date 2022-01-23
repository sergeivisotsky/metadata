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

package io.github.sergeivisotsky.metadata.itest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import io.github.sergeivisotsky.metadata.selector.rest.dto.FormMetadataRequest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * An integration test for the whole framework.
 *
 * @author Sergei Visotsky
 */
@ActiveProfiles("test")
@SpringBootTest(classes = ITestBootstrap.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewMetadataRestTest {

    private static final ClassLoader CLASS_LOADER = ViewMetadataRestTest.class.getClassLoader();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetViewMetadata() throws IOException {
        InputStream jsonStream = CLASS_LOADER.getResourceAsStream("json/testViewMetadata.json");
        Optional.ofNullable(jsonStream).orElseThrow(IllegalStateException::new);

        String url = String.format("http://localhost:%s/api/v1/view/metadata/main/en", port);
        String responseAsString = restTemplate.getForObject(url, String.class);

        assertEquals(
                getExpectedResponse("json/testViewMetadata.json"),
                normalizeJson(responseAsString)
        );
    }

    @Test
    void testGetLookupMetadata() throws IOException {
        InputStream jsonStream = CLASS_LOADER.getResourceAsStream("json/testLookupMetadata.json");
        Optional.ofNullable(jsonStream).orElseThrow(IllegalStateException::new);

        String url = String.format("http://localhost:%s/api/v1/lookup/metadata/CHECK_POINT/en", port);
        String responseAsString = restTemplate.getForObject(url, String.class);

        assertEquals(
                getExpectedResponse("json/testLookupMetadata.json"),
                normalizeJson(responseAsString)
        );
    }

    @Test
    @Disabled
    void testGetFormMetadata() throws IOException {
        InputStream jsonStream = CLASS_LOADER.getResourceAsStream("json/testLookupMetadata.json");
        Optional.ofNullable(jsonStream).orElseThrow(IllegalStateException::new);

        FormMetadataRequest request = new FormMetadataRequest();
        request.setFormName("feedback");

        String url = String.format("http://localhost:%s/api/v1/form/metadata/EN", port);
        String responseAsString = restTemplate.postForObject(url, request, String.class);

        assertEquals(
                getExpectedResponse("json/testLookupMetadata.json"),
                normalizeJson(responseAsString)
        );
    }

    private static String getExpectedResponse(String path) throws IOException {
        InputStream stream = ViewMetadataRestTest.class.getClassLoader().getResourceAsStream(path);
        return normalizeJson(IOUtils.toString(stream, StandardCharsets.UTF_8));
    }

    private static String normalizeJson(String json) {
        return json.replaceAll("[\\t\\n\\r]", "").replaceAll(" ", "");
    }

}
