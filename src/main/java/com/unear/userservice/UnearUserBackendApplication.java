package com.unear.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UnearUserBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(UnearUserBackendApplication.class, args);
  }

}
