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

package io.github.sergeivisotsky.metadata.selector.rest;

import io.github.sergeivisotsky.metadata.selector.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.form.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.rest.dto.FormMetadataRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sergei Visotsky
 */
@RequestMapping("/api/v1/form")
public class FormMetadataController {

    private final FormMetadataDao formMetadataDao;

    public FormMetadataController(FormMetadataDao formMetadataDao) {
        this.formMetadataDao = formMetadataDao;
    }

    @GetMapping("/metadata")
    public FormMetadata getFormMetadata(@RequestBody FormMetadataRequest request) {
        return formMetadataDao.getFormMetadata(request.getFormName());
    }
}