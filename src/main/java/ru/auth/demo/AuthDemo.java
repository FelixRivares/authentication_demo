package ru.auth.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuthDemo {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(AuthDemo.class)
        .run(args);
  }

}
