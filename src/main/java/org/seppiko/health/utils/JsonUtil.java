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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.util.ArrayList;

/**
 * @author Leonard Woo
 */
public class JsonUtil {

  private static Gson gson = new Gson();

  public static JsonElement fromJson(String json) {
    return JsonParser.parseString(json);
  }

  public static JsonElement fromJson(Reader reader) {
    return JsonParser.parseReader(reader);
  }

  public static <T> ArrayList<T> fromJsonArray(JsonArray array, Class<T> classOfT) {
    ArrayList<T> list = new ArrayList<>();
    if(array.size() == 1) {
      list.add( toT( array.get(0).getAsJsonObject(), classOfT ) );
    } else {
      array.forEach(
          jsonElement -> {
            list.add(toT(jsonElement.getAsJsonObject(), classOfT));
          });
    }
    return list;
  }

  public static <T> T toT(JsonObject jObj, Class<T> classOfT) {
    return gson.fromJson(jObj, classOfT);
  }

  public static String toJson(Object obj) {
    return gson.toJson(obj);
  }
}
