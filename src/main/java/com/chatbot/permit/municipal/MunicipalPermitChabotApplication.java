package com.chatbot.permit.municipal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MunicipalPermitChabotApplication {

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        SpringApplication.run(MunicipalPermitChabotApplication.class, args);
  }
}
