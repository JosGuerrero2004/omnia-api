package org.josiasguerrero.omnia_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "org.josiasguerrero.products",
    "org.josiasguerrero.shared",
    "org.josiasguerrero.omnia_api"
})
@EnableJpaRepositories({
    "org.josiasguerrero.products.infrastructure.persistence.repository"
})
@EntityScan({
    "org.josiasguerrero.products.infrastructure.persistence.entity"
})
public class OmniaApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(OmniaApiApplication.class, args);
  }

}
