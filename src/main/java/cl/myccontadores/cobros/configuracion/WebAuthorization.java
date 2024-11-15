package cl.myccontadores.cobros.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebAuthorization {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers("/web/index.html").permitAll()
//                        .requestMatchers("/api/login/**").permitAll()
//                        .requestMatchers("/web/css/**", "/web/img/**", "/web/js/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/clients").permitAll()
//                        .requestMatchers("/swagger-ui.html").permitAll()
//                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .requestMatchers("/v3/api-docs").permitAll()
//                        .requestMatchers("/actuator/**").hasAuthority("ADMIN")
//                        .requestMatchers("/h2-console/**").hasAuthority("ADMIN")
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/rest/**").hasAuthority("ADMIN")
//                        .requestMatchers("/**").permitAll())


                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((req, res, exc) -> {
                            res.sendRedirect("/web/index.html");
                        }));

        return http.build();
    }
}
