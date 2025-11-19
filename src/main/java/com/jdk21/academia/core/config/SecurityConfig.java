package com.jdk21.academia.core.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Define la fuente de configuración de CORS como un Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // **Añade el origen de tu frontend React (5173)**
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); 
        
        // Define los métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Define las cabeceras permitidas (especialmente importante para JWT)
        configuration.setAllowedHeaders(List.of("*")); 
        
        // Permite enviar cookies/credenciales
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuración a todas las rutas (/**)
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Deshabilitar CSRF para APIs REST (se usará JWT en el futuro)
            .csrf(csrf -> csrf.disable())
            
            // Permitir acceso público a todos los endpoints de la API
            // Restringir acceso cuando se implemente JWT
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**", "/graphql/**").permitAll()
                .anyRequest().permitAll()
            )
            
            // No crear sesiones (stateless, ideal para APIs REST)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        
        return http.build();
    }


    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf().disable() // desactiva CSRF
    //         .authorizeHttpRequests()
    //         .anyRequest().permitAll(); // permite todas las rutas sin autenticación

    //     return http.build();
    // }
}

