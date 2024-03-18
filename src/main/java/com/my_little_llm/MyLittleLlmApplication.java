package com.my_little_llm;

import javax.sql.DataSource;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Log
public class MyLittleLlmApplication {

  public MyLittleLlmApplication(DataSource dataSource) {}

  public static void main(String[] args) {
    SpringApplication.run(MyLittleLlmApplication.class, args);
  }
}
