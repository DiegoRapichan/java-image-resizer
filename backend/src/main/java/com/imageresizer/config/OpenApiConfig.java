package com.imageresizer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Image Resizer & Optimizer API")
                        .version("1.0.0")
                        .description("API REST para redimensionamento, otimização e transformação de imagens")
                        .contact(new Contact()
                                .name("Diego Rapichan")
                                .email("direrapichan@gmail.com")
                                .url("https://github.com/DiegoRapichan"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
