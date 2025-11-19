package com.jdk21.academia.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura la política CORS para permitir el acceso desde el frontend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica la regla a TODOS los endpoints (incluyendo /graphql)
                // CAMBIA ESTO si tu puerto de frontend es diferente a 5173 
                .allowedOrigins("http://localhost:5173") 
                // Permite los métodos necesarios para el CRUD y GraphQL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600); 
    }
}
