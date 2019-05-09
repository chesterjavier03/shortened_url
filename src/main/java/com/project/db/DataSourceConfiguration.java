package com.project.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by chesterjavier.
 */
@Configuration
@Slf4j
public class DataSourceConfiguration {

  private static String DB_SCRIPT_PATH;

  @Value("${memory.datasource.path}")
  public void setDbScriptPath(String dbScriptPath) {
    DB_SCRIPT_PATH = dbScriptPath;
  }

  @Bean
  @Primary
  public DataSource dataSource() {
    DataSource dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL).addScript(DB_SCRIPT_PATH).build();
    log.info("DataSource initialized.");
    return dataSource;
  }
}
