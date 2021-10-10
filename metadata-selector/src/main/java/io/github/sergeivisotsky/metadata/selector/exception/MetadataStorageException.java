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

package io.github.sergeivisotsky.metadata.selector.exception;

/**
 * @author Sergei Visotsky
 */
public class MetadataStorageException extends RuntimeException {

    private static final long serialVersionUID = 7120862327018623860L;

    public MetadataStorageException() {
        super();
    }

    public MetadataStorageException(String message) {
        super(message);
    }

    public MetadataStorageException(String message, Object... params) {
        super(String.format(message.replace("{}", "%s"), params));
    }

    public MetadataStorageException(Throwable cause, String message, Object... params) {
        super(String.format(message.replace("{}", "%s"), params), cause);
    }

    public MetadataStorageException(Throwable cause) {
        super(cause);
    }
}
