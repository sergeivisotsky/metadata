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

package io.github.sergeivisotsky.metadata.selector.rest.advice;

import java.sql.SQLException;

import io.github.sergeivisotsky.metadata.selector.exception.DataAccessException;
import io.github.sergeivisotsky.metadata.selector.rest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Sergei Visotsky
 */
@ControllerAdvice
public class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<ErrorResponse> handleSQLException(Exception ex, WebRequest req) {
        return ResponseEntity.badRequest()
                .body(getErrorInternalErrorBuilder()
                        .message("Unexpected data access Exception")
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex, WebRequest req) {
        return ResponseEntity.badRequest().body(getErrorInternalErrorBuilder().build());
    }

    private ErrorResponse.ErrorResponseBuilder getErrorInternalErrorBuilder() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ErrorResponse.builder()
                .code(status.value())
                .error(status.getReasonPhrase());
    }
}
