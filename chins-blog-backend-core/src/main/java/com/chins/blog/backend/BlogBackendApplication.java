package com.chins.blog.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chins.blog.backend.provider.mapper")
public class BlogBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogBackendApplication.class, args);
  }
}
