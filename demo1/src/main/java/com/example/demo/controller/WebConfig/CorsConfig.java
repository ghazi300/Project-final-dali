package com.example.demo.controller.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // permet à toutes les URLs de votre application d'accéder aux ressources de votre backend
                .allowedOrigins("http://localhost:4200") // autorise uniquement les requêtes provenant de http://localhost:4200
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // autorise les méthodes HTTP GET, POST, PUT, DELETE
    }
}
