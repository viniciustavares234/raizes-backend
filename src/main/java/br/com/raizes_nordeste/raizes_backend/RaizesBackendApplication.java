package br.com.raizes_nordeste.raizes_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.com.raizes.entity")
@EnableJpaRepositories("br.com.raizes.repository")
public class RaizesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaizesBackendApplication.class, args);
	}

}
