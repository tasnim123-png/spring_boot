package com.example.pfe.Employé.Config;


import com.example.pfe.Employé.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Configuring Spring Security : authentication, authorization, logout handling, and exception handling.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return  http
                .csrf(AbstractHttpConfigurer::disable)

                //Configuring access control for different URL
                .authorizeHttpRequests(
                        req ->req.requestMatchers("/login/**","/register/**","/refresh" , "api/skill/**" , "/api/user/**")  //Requests to URLs starting with /login/** and /register/** are permitted without authentication.
                                .permitAll()
                                .requestMatchers("/employee/**").hasAuthority("Employé") //Requests to URLs starting with /employee/** require the user to have the authority "Employé".
                                .requestMatchers("/admin/**").hasAuthority("Admin")       //Requests to URLs starting with /admin/** require the user to have the authority "Admin".
                                .requestMatchers("/manager/**").hasAuthority("Manager")   //Requests to URLs starting with /manager/** require the user to have the authority "Manager".
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsService)   //loading user details during authentication.

                //Spring Security will not create or use sessions to store authentication state.
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                //configures exception handling for access denied and unauthorized requests
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)   //Setting the HTTP status code to 403 (Forbidden)in case of access denied requests
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))   //Setting the HTTP status code to 401 (Unauthorized) in case of unauthorized requests


                // Configuring logout handling
                .logout(l->l.logoutUrl("/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler(
                                (request,response,authentication)-> SecurityContextHolder.clearContext()
                        )
                )
                .build();

    }


    @Bean
    // Password hashing using BCrypt hashing algorithm
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    // Creating an AuthenticationManager to authenticate users
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return  configuration.getAuthenticationManager();
    }
}
