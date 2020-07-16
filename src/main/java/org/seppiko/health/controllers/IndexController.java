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

import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import org.seppiko.health.model.JsonMessageEntity;
import org.seppiko.health.services.ActivityService;
import org.seppiko.health.utils.JsonUtil;
import org.seppiko.health.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * default index controller
 *
 * @author Leonard Woo
 */
@RestController
public class IndexController {

  @RequestMapping(value = "/")
  public ResponseEntity<byte[]> indexHandleControllerExecution() {
    return ResponseUtil.sendJsonContent(400,
        new JsonMessageEntity(400, "Bad request").toJsonString());
  }

  @Autowired
  private ActivityService activityService;

  @GetMapping(value = "/show")
  public ResponseEntity<byte[]> showHandleControllerExecution() {
    return ResponseUtil.sendJsonContent(200, JsonUtil.toJson(activityService.showList()));
  }

  @PostMapping(value = "/add")
  public ResponseEntity<byte[]> postHandleControllerExecution(@RequestBody String requestBody) {

    JsonObject obj = JsonUtil.fromJson(requestBody).getAsJsonObject();

    if ( !activityService.addData( LocalDateTime.parse(obj.get("start").getAsString()),
        LocalDateTime.parse(obj.get("end").getAsString()),
        obj.get("mode").getAsShort() ) ) {
      return ResponseUtil.sendJsonContent(200, new JsonMessageEntity(400, "add failed").toJsonString() );
    }

    return ResponseUtil.sendJsonContent(200, new JsonMessageEntity(200, "add success").toJsonString() );
  }
}