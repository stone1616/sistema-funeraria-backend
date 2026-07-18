package com.utp.sistemafuneraria.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import com.utp.sistemafuneraria.shared.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                
                // Módulo Configuración (ADMIN)
                .requestMatchers("/api/usuario/**").hasRole("ADMIN")
                
                // Módulo Personal (Lectura para ADMIN y COORDINADOR, Escritura solo ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/personal/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers("/api/personal/**").hasRole("ADMIN")
                
                // Recursos (Salas y Vehículos) (ADMIN y COORDINADOR)
                .requestMatchers("/api/sala/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers("/api/vehiculo/**").hasAnyRole("ADMIN", "COORDINADOR")
                
                // Módulo Servicios Funerarios (ADMIN y COORDINADOR)
                .requestMatchers("/api/servicio/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers("/api/reserva-sala/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers("/api/asignacion-vehiculo/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers("/api/destino-final/**").hasAnyRole("ADMIN", "COORDINADOR")
                
                // Módulo Financiera (ADMIN y ASESOR)
                .requestMatchers("/api/cotizacion/**").hasAnyRole("ADMIN", "ASESOR")
                .requestMatchers("/api/comprobante/**").hasAnyRole("ADMIN", "ASESOR")
                .requestMatchers("/api/pago/**").hasAnyRole("ADMIN", "ASESOR")
                
                // Módulo Inventario (Lectura para todos; Gestión solo ADMIN y COORDINADOR)
                .requestMatchers(HttpMethod.GET, "/api/producto/**").hasAnyRole("ADMIN", "COORDINADOR", "ASESOR")
                .requestMatchers("/api/producto/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers("/api/movimiento-inventario/**").hasAnyRole("ADMIN", "COORDINADOR")
                
                // Módulo Clientes y Difuntos (Lectura para todos; Escritura solo ADMIN y ASESOR)
                .requestMatchers(HttpMethod.GET, "/api/difunto/**").hasAnyRole("ADMIN", "ASESOR", "COORDINADOR")
                .requestMatchers(HttpMethod.GET, "/api/cliente/**").hasAnyRole("ADMIN", "ASESOR", "COORDINADOR")
                .requestMatchers("/api/difunto/**").hasAnyRole("ADMIN", "ASESOR")
                .requestMatchers("/api/cliente/**").hasAnyRole("ADMIN", "ASESOR")
                
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
