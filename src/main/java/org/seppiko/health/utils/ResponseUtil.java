/*
 * Copyright 2020 the original author or authors.
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

package org.seppiko.health.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Leonard Woo
 */
public class ResponseUtil {

  public static ResponseEntity<byte[]> sendOkContent(MediaType type, byte[] content) {
    return sendResponseContent(HttpStatus.OK, type, content);
  }

  public static ResponseEntity<byte[]> sendOkJsonContent(String json) {
    return sendResponseContent(HttpStatus.OK, MediaType.APPLICATION_JSON, json.getBytes());
  }

  public static ResponseEntity<byte[]> sendJsonContent(int status, String json) {
    return sendResponseContent(status, MediaType.APPLICATION_JSON, json.getBytes());
  }

  public static ResponseEntity<byte[]> sendResponseContent(int status, MediaType type, byte[] content) {
    return sendResponseContent(HttpStatus.valueOf(status), type, content);
  }

  public static ResponseEntity<byte[]> sendResponseContent(HttpStatus status, MediaType type, byte[] content) {
    return ResponseEntity.status(status).contentType(type)
        .contentLength(content.length)
        .body(content);
  }
}
