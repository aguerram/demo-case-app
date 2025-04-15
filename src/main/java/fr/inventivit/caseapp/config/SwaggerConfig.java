package fr.inventivit.caseapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Case Management API",
        description = "Demo application for managing cases using Spring Boot and Clean Architecture",
        version = "1.0.0",
        contact = @Contact(
            name = "Mostafa AGUERRAM",
            email = "mostafa.aguerram@gmail.com"
        ),
        license = @License(
            name = "MIT License"
        )
    )
)
public class SwaggerConfig {
}