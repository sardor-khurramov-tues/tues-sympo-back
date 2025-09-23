package sympo.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
//                .addSecurityItem(securityItem())
//                .components(createComponent())
                .info(info());
    }

//    private SecurityRequirement securityItem() {
//        return new SecurityRequirement()
//                .addList("Bearer Authentication");
//    }

//    private Components createComponent() {
//        return new Components()
//                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());
//    }

//    private SecurityScheme createAPIKeyScheme() {
//        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
//                .bearerFormat("JWT")
//                .scheme("bearer");
//    }

    private Info info() {
        return new Info()
                .title("OUT")
                .description("OUT application")
                .version("V1.0")
                .contact(contact());
    }

    private Contact contact() {
        return new Contact()
                .name("Sardor Khurramov")
                .email("sorryka1999@gmail.com");
    }
}
