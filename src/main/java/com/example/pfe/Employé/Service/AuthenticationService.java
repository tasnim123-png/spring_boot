package com.example.pfe.Employé.Service;

import com.example.pfe.Employé.Dto.AuthenticationResponse;
import com.example.pfe.Employé.Entity.Employee;
import com.example.pfe.Employé.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@Service
public class AuthenticationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    // This method is used for registering a new user
    public AuthenticationResponse register(Employee request) {

        //Creating a new instance of employee
        Employee employee = new Employee();

        // setting the employee credentials
        employee.setMatricule(request.getMatricule());
        employee.setNom(request.getNom());
        employee.setPrénom(request.getPrénom());
        employee.setUsername(request.getUsername());
        employee.setEmail(request.getEmail());
        employee.setPoste(request.getPoste());
        employee.setDépartement(request.getDépartement());
        employee.setGrade(request.getGrade());
        employee.setNote(request.getNote());
        employee.setLieu_de_travail(request.getLieu_de_travail());

        employee.setPassword(passwordEncoder.encode(request.getPassword()));  //PasswordEncoder is used to hash the password provided by the employee

        employee.setRole(request.getRole());

        employee = employeeRepository.save(employee);  //Save the new employee in the database

        // Generate access and refresh tokens
        Map<String, String> tokens = jwtService.generateTokens(employee);

        return new AuthenticationResponse(tokens.get("accessToken"), tokens.get("refreshToken"));
    }


    //This method is used to logging a user
    public AuthenticationResponse login(@RequestBody Employee request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Employee employee = (Employee) employeeRepository.findByUsername(request.getUsername());

        // Generate access and refresh tokens
        Map<String, String> tokens = jwtService.generateTokens(employee);

        return new AuthenticationResponse(tokens.get("accessToken"), tokens.get("refreshToken"));
    }


    //This method is used when the access token is expired
    public AuthenticationResponse refreshToken(String authorizationHeader) {
        // Extract the token from the Authorization header
        String refreshToken = extractTokenFromHeader(authorizationHeader);

        // Validate the refresh token
        if (!jwtService.isValidRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Extract username from the refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Retrieve user details from the user details service
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Generate both access and refresh tokens for the user
        Map<String, String> tokens = jwtService.generateTokens((Employee) userDetails);

        // Return both tokens in the AuthenticationResponse
        return new AuthenticationResponse(tokens.get("accessToken"), tokens.get("refreshToken"));
    }


//method to extract token from header
    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        return authorizationHeader.substring(7); // remove  "Bearer " to get the token
    }

}














