package com.backend.springboot.app.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/proyecto/listar",
                        "/api/detalle/listar",
                        "/api/usuarios/usuarios",
                        "/api/detalle/ver/{id}/cantidad/{cantidad}",
                        "/api/proyecto/ver/{id}").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/usuarios/usuarios/{id}").hasAnyAuthority("ROL_ADMIN", "ROL_USER")
                .pathMatchers("/api/proyecto/**", "/api/detalle/**", "/api/usuarios/usuarios/**").hasAuthority("ROL_ADMIN")
                .anyExchange().authenticated()
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }
}
