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

package io.github.sergeivisotsky.metadata.engine.rest;

import io.github.sergeivisotsky.metadata.engine.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.form.FormMetadata;
import io.github.sergeivisotsky.metadata.engine.rest.dto.FormMetadataRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/api/v1/form")
public class FormMetadataController {

    private final FormMetadataDao formMetadataDao;

    public FormMetadataController(FormMetadataDao formMetadataDao) {
        this.formMetadataDao = formMetadataDao;
    }

    @PostMapping("/metadata/{lang}")
    public FormMetadata getFormMetadata(@PathVariable("lang") String lang,
                                        @RequestBody FormMetadataRequest request) {
        return formMetadataDao.getFormMetadata(request.getFormName(), lang);
    }
}
