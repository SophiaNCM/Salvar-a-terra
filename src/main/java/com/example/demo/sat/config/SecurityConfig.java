package com.example.demo.sat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.sat.sevice.UsuarioService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UsuarioService service;

    public SecurityConfig(UsuarioService service) {
        this.service = service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(service);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
//========================================================Liberando acesso por causa do security===================================================
        http
            .authenticationProvider(authenticationProvider())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login",
                    "/login-error",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/usuario/registro",
                    "/usuario","/posts",
                    "/usuario/save",
                    "/posts/listar",
                    "/posts/CriarPost",
                    "/posts/CriarPost/save",
                    "/usuario/perfilProprio",
                    "/usuario/editPerfil"
                ).permitAll()
                .anyRequest().authenticated())
//===================================================================================================================================================
//=========================================================Configurando o form de login==============================================================
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/posts/listar", true)
                .failureUrl("/login-error")
                .permitAll())
//===================================================================================================================================================
            .logout(logout -> logout
                .logoutSuccessUrl("/").permitAll());

        return http.build();
    }
}