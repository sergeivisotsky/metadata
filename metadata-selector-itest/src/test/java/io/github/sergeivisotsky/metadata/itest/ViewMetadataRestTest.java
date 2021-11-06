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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * An integration test for the whole framework.
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ITestBootstrap.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewMetadataRestTest {

    private static final ClassLoader CLASS_LOADER = ViewMetadataRestTest.class.getClassLoader();

    @LocalServerPort
    private int port;

    @Test
    public void testGetViewMetadata() throws IOException {
        InputStream jsonStream = CLASS_LOADER.getResourceAsStream("json/testViewMetadata.json");
        Optional.ofNullable(jsonStream).orElseThrow(IllegalStateException::new);

        TestRestTemplate restTemplate = new TestRestTemplate();
        String url = String.format("http://localhost:%s/api/v1/view/metadata/main/en", port);

        String expectedResponse = IOUtils.toString(jsonStream, StandardCharsets.UTF_8);
        String responseAsString = restTemplate.getForObject(url, String.class);

        assertEquals(expectedResponse, responseAsString);
    }

    @Test
    public void testGetLookupMetadata() throws IOException {
        InputStream jsonStream = CLASS_LOADER.getResourceAsStream("json/testLookupMetadata.json");
        Optional.ofNullable(jsonStream).orElseThrow(IllegalStateException::new);

        TestRestTemplate restTemplate = new TestRestTemplate();
        String url = String.format("http://localhost:%s/api/v1/lookup/metadata/CHECK_POINT/en", port);

        String expectedResponse = IOUtils.toString(jsonStream, StandardCharsets.UTF_8);
        String responseAsString = restTemplate.getForObject(url, String.class);

        assertEquals(expectedResponse, responseAsString);
    }

    @Test
    public void testGetFormMetadata() {
        InputStream jsonStream = CLASS_LOADER.getResourceAsStream("json/testLookupMetadata.json");
        Optional.ofNullable(jsonStream).orElseThrow(IllegalStateException::new);

        TestRestTemplate restTemplate = new TestRestTemplate();
        String url = String.format("http://localhost:%s/api/v1/form/metadata/EN", port);

        FormMetadataRequest request = new FormMetadataRequest();
        request.setFormName("feedback");

        String responseAsString = restTemplate.postForObject(url, request, String.class);

        System.out.println(responseAsString);
    }
}
