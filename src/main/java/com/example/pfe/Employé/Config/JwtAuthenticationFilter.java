package com.example.pfe.Employé.Config;


import com.example.pfe.Employé.Service.JwtService;
import com.example.pfe.Employé.Service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Intercept incoming HTTP requests before they reach the servlet or endpoint responsible for handling them
//JWT Token Validation
// Extracting User Details from Token
//Setting Authentification
//Allowing Request to Proceed

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    //Filtering HTTP requests
    @Override
    protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull   HttpServletResponse response,
          @NonNull  FilterChain filterChain)
            throws ServletException, IOException {

        //extract "Authorization" header from the HTTP request
        String authHeader = request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        //extracts the JWT token from the "Authorization" header
        String token = authHeader.substring(7);

        //extract the username from the JWT token
        String username = jwtService.extractUsername(token);

        // fetch the user details (such as roles and authorities) based on the username extracted from the JWT token.
       UserDetails userDetails = userDetailsService.loadUserByUsername(username);

       //validating the token : check token expiration && verifying user credentials
       if(jwtService.isValid(token,userDetails)){
           //creating a new authentication token
           UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
                   userDetails , null , userDetails.getAuthorities()
           );
           //setting additional details for the authentication token (such as the remote address and the session ID)
           authtoken.setDetails(
                   new WebAuthenticationDetailsSource().buildDetails(request)
           );

           SecurityContextHolder.getContext().setAuthentication(authtoken);


        }

       filterChain.doFilter(request,response);




    }
}
