package com.example.binarfud.security;

import com.example.binarfud.security.jwt.JwtAuthTokenFilter;
import com.example.binarfud.security.jwt.JwtUtils;
import com.example.binarfud.security.service.UserDetailsServiceImpl;
import com.example.binarfud.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    final UserDetailsServiceImpl userDetailService;
    final JwtUtils jwtUtils;
    final UserService userService;

    public SecurityConfig(UserDetailsServiceImpl userDetailService, JwtUtils jwtUtils, UserService userService) {
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/users/register", "/users/validate-otp", "/users/forgot-password", "/users/reset-password").permitAll()
                                .requestMatchers("/auth/signin/**", "/auth/signin", "/auth/register").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger*/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService())
                        )
                        .successHandler((request, response, authentication) -> {
                            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                            userService.createUserPostLogin(oidcUser.getAttribute("email"),
                                    oidcUser.getAttribute("email"));

                            response.sendRedirect("/auth/oauth2/success");
                        })
                );

        return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Bean
    public JwtAuthTokenFilter authTokenFilter(){
        return new JwtAuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "https://google.com",
                "https://accounts.google.com/",
                "https://binarfud-api-production.up.railway.app",
                "http://localhost:8080"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
