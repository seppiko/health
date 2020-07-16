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

package org.seppiko.health.configure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.seppiko.health.utils.IOUtil;
import org.seppiko.health.utils.JsonUtil;

/**
 * @author Leonard Woo
 */
@Slf4j
public class SeppikoHealthConfiguration {

  private static SeppikoHealthConfiguration instance = new SeppikoHealthConfiguration();
  public static SeppikoHealthConfiguration getInstance() {
    return instance;
  }
  private SeppikoHealthConfiguration() {
    init();
  }

  private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  @Getter
  private JdbcEntity jdbc;

  private void init() {
    try {
      String path = System.getProperty("seppiko.configFile", "config.yaml");
      BufferedReader reader = IOUtil.load(path);
      if(reader == null) {
        reader = IOUtil.load("config.yml");
      }
      if(reader == null) {
        throw new FileNotFoundException();
      }

      JsonNode healthJson = mapper.readTree(reader).get("health");

      jdbc = mapper.treeToValue(healthJson.get("datasource"), JdbcEntity.class);
      log.info("profile config:" + JsonUtil.toJson(jdbc));

    } catch (Exception e) {
      log.error("Load config file failed.", e);
    }
  }

}
