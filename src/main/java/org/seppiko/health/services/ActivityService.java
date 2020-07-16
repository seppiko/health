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

package org.seppiko.health.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.seppiko.health.mappers.ActivityMapper;
import org.seppiko.health.model.ActivityMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leonard Woo
 */
@Slf4j
@Service
public class ActivityService {

  @Autowired
  private ActivityMapper activityMapper;

  public ArrayList<ActivityMapperEntity> showList() {
    return activityMapper.show();
  }

  public ActivityMapperEntity showToday() {
    return activityMapper.queryByDay().get(0);
  }

  public boolean addData(LocalDateTime startTime, LocalDateTime endTime, short mode) {
    return activityMapper.addData(startTime, endTime, mode) > 0;
  }
}
