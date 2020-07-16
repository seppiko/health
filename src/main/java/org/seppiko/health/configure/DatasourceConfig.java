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

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Leonard Woo
 */
@Configuration
public class DatasourceConfig {

  private SeppikoHealthConfiguration configuration = SeppikoHealthConfiguration.getInstance();

  @Bean
  public DataSource getDatasource() {
    JdbcEntity jdbc = configuration.getJdbc();
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName(jdbc.getDriverClassName());
    dataSource.setJdbcUrl(jdbc.getUrl());
    dataSource.setUsername(jdbc.getUsername());
    dataSource.setPassword(jdbc.getPassword());
    return dataSource;
  }
}
