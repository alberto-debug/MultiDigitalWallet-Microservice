package com.alberto.security;

import com.alberto.authFeature.ui.LoginView;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure your static resources with public access
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/images/*.png", "/icons/**")
                .permitAll()
                .requestMatchers("/public/**")
                .anonymous()
                .requestMatchers("/admin/**")
                .hasRole("ADMIN"));

        // Configure Vaadin's security using VaadinSecurityConfigurer
        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            // This is important to register your login view to the
            // navigation access control mechanism:
            configurer.loginView(LoginView.class);

            // You can add any possible extra configurations of your own
            // here (the following is just an example):
            // configurer.enableCsrfConfiguration(false);
        });

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

}
