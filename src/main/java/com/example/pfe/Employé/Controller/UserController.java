package com.example.pfe.Employé.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

   @GetMapping("/employee")
    public ResponseEntity<String> Employee() {
        return ResponseEntity.ok("Hello, this is Employee Interface");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> Admin() {
        return ResponseEntity.ok("Hello, this is Admin Interface");
    }

    @GetMapping("/manager")
    public ResponseEntity<String> Manager() {
        return ResponseEntity.ok("Hello, this is Manager Interface");
    }


}
