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
                description = """
                              All documentation and all current REST api endpoints are shown below. If you see lock on \s
                              the right of the endpoint, it means it must be authenticated with Bearer Token. You can\s
                              get Bearer Access Token by using Authentication Controller. You must on the client side save\s
                              user's id, username, email, access token when user is authenticated, because those attributes\s
                              will be used for further endpoint accessing.
                              """,
                title = "NeSkelbiu.lt REST API",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "Product url"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
