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

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import io.github.sergeivisotsky.metadata.engine.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.engine.domain.ViewMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.ViewQueryResult;
import io.github.sergeivisotsky.metadata.engine.exception.UrlParseException;
import io.github.sergeivisotsky.metadata.engine.rest.filtering.UrlViewQueryParser;
import io.github.sergeivisotsky.metadata.engine.filtering.dto.ViewQuery;
import io.github.sergeivisotsky.metadata.engine.rest.dto.ViewQueryResultResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/api/v1/view")
public class ViewMetadataController {

    private static final Logger LOG = LoggerFactory.getLogger(ViewMetadataController.class);

    private final ViewMetadataDao metadataDao;
    private final ViewQueryDao queryDao;
    private final UrlViewQueryParser filterService;

    public ViewMetadataController(ViewMetadataDao metadataDao,
                                  ViewQueryDao queryDao,
                                  UrlViewQueryParser filterService) {
        this.metadataDao = metadataDao;
        this.queryDao = queryDao;
        this.filterService = filterService;
    }

    @GetMapping("/metadata/{viewName}/{lang}")
    public ViewMetadata getViewMetadata(@PathVariable("viewName") String viewName,
                                        @PathVariable("lang") String lang) {
        return metadataDao.getViewMetadata(viewName, lang);
    }

    @GetMapping("/{viewName}/{lang}/query")
    public ResponseEntity<ViewQueryResultResponse> query(@PathVariable("viewName") String viewName,
                                                         @PathVariable("lang") String lang,
                                                         HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();

        ViewMetadata metadata = metadataDao.getViewMetadata(viewName, lang);

        ViewQuery query;
        try {
            query = filterService.constructViewQuery(metadata, params);
        } catch (UrlParseException e) {
            LOG.error("Invalid query: {}, StackTrace: {}", req.getRequestURI(),
                    ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ViewQueryResult queryResult = queryDao.query(metadata, query);

        ViewQueryResultResponse response = new ViewQueryResultResponse(queryResult);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
