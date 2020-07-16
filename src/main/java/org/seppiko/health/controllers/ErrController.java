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

package org.seppiko.health.controllers;

import lombok.extern.slf4j.Slf4j;
import org.seppiko.health.model.JsonMessageEntity;
import org.seppiko.health.utils.ResponseUtil;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ERROR
 *
 * @author Leonard Woo
 */
@Slf4j
@RestController
public class ErrController implements ErrorController {

  @RequestMapping("/error")
  public ResponseEntity<byte[]> handlerError() {
    return ResponseUtil.sendJsonContent( HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new JsonMessageEntity(500, "SERVER ERROR!!!").toJsonString());
  }

  @RequestMapping("/error/{code}")
  public ResponseEntity<byte[]> handlerPageError(@PathVariable int code) {
    return ResponseUtil.sendJsonContent( HttpStatus.OK.value(),
        new JsonMessageEntity(code, HttpStatus.valueOf(code).getReasonPhrase()).toJsonString());
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @Bean
  public ErrorPageRegistrar errorPageRegistrar() {
    return new MyErrorPageRegistrar();
  }

  private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {

      registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
      registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401"));
      registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
      registry.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405"));

      registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
      registry.addErrorPages(new ErrorPage(HttpStatus.NOT_IMPLEMENTED, "/error/501"));
    }
  }
}
