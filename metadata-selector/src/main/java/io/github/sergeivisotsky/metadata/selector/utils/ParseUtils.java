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

package io.github.sergeivisotsky.metadata.selector.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
public final class ParseUtils {

    private ParseUtils() {
        // noop
    }

    public static List<String> splitEscaped(String str, char delim) {
        List<String> result = new ArrayList<>();

        StringBuilder curStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (delim == curChar) {
                if (i < str.length() - 1 && delim == str.charAt(i + 1)) {
                    i++;
                } else {
                    if (curStr.length() > 0) {
                        result.add(curStr.toString());
                        curStr.delete(0, curStr.length());
                    }
                    continue;
                }
            }
            curStr.append(curChar);
        }

        if (curStr.length() > 0) {
            result.add(curStr.toString());
        }

        return result;
    }
}
