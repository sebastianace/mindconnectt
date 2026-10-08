package springboot.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import springboot.infrastructure.security.filter.JwtAuthenticationFilter;
import springboot.infrastructure.security.filter.JwtValidationFilter;

/**
 * Seguridad stateless con JWT (Spring Security 6 del taller; con Spring Boot 4 el proyecto corre sobre
 * Spring Security 7, que mantiene este mismo DSL con lambdas).
 *
 * Reglas:
 * - POST /api/auth/login y POST /api/users/register: públicas.
 * - /api/users (crear y listar usuarios): solo ROLE_ADMIN.
 * - DELETE en cualquier /api/**: solo ROLE_ADMIN.
 * - Todo lo demás: cualquier usuario autenticado.
 */
@Configuration
public class SpringSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager,
            TokenJwtConfig tokenConfig) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, JwtAuthenticationFilter.LOGIN_URL, "/api/users/register")
                                .permitAll()
                        .requestMatchers("/api/users", "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(new RestAccessDeniedHandler()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager, tokenConfig))
                .addFilterBefore(new JwtValidationFilter(tokenConfig), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
