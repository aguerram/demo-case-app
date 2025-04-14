package fr.inventivit.caseapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CaseAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseAppApplication.class, args);
    }

}
