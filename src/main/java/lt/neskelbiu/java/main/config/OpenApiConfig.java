package lt.neskelbiu.java.main.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Vilius",
                        email = "email@gmail.com",
                        url = "#"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specifications - Vilius",
                version = "1.0",
                license= @License(
                        name = "License name",
                        url = "some url"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = { @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "Product url"
                )
        },
        security = {
                @SecurityRequirement( // naudoti jei aprasome securityScheme visiem enpoints
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme( // naudoti jei aprasome securityScheme atskiriems endpoints, taip pat reikia naudoti anotacijas prie kontroleriu, pvz @SecurityRequirement(name = "bearerAuth")
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
