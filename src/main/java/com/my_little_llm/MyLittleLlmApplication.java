package com.my_little_llm;

import javax.sql.DataSource;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Log
public class MyLittleLlmApplication {

  private final DataSource dataSource;

  public MyLittleLlmApplication(DataSource dataSource) {
    this.dataSource = dataSource;
    dbHealthCheck();
  }

  public static void main(String[] args) {
    SpringApplication.run(MyLittleLlmApplication.class, args);
  }

  private void dbHealthCheck() {
    log.info("Our DataSource is = " + dataSource);
    try (var connection = dataSource.getConnection()) {
      final var template = new JdbcTemplate(dataSource);
      template.execute("select 1;");
      log.info("We have a connection to our DB");
    } catch (Exception e) {
      log.info("Our connection failed");
    }
  }
}
