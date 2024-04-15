package com.example.pfe.Employé.Controller;

import com.example.pfe.Employé.Dto.AuthenticationResponse;
import com.example.pfe.Employé.Entity.Employee;
import com.example.pfe.Employé.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Employee request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Employee request){
        return  ResponseEntity.ok(authenticationService.login(request));

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        AuthenticationResponse response = authenticationService.refreshToken(authorizationHeader);
        return ResponseEntity.ok(response);
    }
}
