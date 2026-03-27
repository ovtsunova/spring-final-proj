package com.example.appliance.config;

import com.example.appliance.repositories.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final AccountRepository accountRepository;

    public SecurityConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return login -> accountRepository.findByUserLogin(login)
                .map(acc -> User.builder()
                        .username(acc.getUserLogin())
                        .password(acc.getUserPassword())
                        .roles(acc.getRole().getRoleLabel())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Аккаунт не найден"));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService,
                                                  BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            boolean isManager = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_MANAGER"));

            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

            if (isAdmin) {
                response.sendRedirect("/admin");
            } else if (isManager) {
                response.sendRedirect("/manager");
            } else if (isUser) {
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/login");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authProvider) throws Exception {
        http
                .authenticationProvider(authProvider)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/register/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        .requestMatchers("/admin/accounts/**", "/admin/roles/**").hasRole("ADMIN")

                        .requestMatchers(
                                "/admin/products/**",
                                "/admin/categories/**",
                                "/admin/brands/**",
                                "/admin/customers/**",
                                "/admin/orders/**",
                                "/admin/order-items/**",
                                "/admin/reviews/**",
                                "/admin/payments/**",
                                "/manager/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers("/products/**").authenticated()

                        .requestMatchers("/profile/**", "/my-orders/**", "/my-reviews/**")
                        .hasAnyRole("USER", "ADMIN", "MANAGER")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}