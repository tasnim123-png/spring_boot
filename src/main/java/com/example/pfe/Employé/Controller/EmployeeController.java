package com.example.pfe.Employé.Controller;


import com.example.pfe.Employé.Dto.EmployeeRequest;
import com.example.pfe.Employé.Dto.PasswordUpdateRequest;
import com.example.pfe.Employé.Entity.Employee;
import com.example.pfe.Employé.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user/employee")



public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/{username}")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
        Employee employee = employeeService.findByUsername(username);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{username}/update")
    public ResponseEntity<Employee> updateEmployeeByUsername(@PathVariable String username, @RequestBody EmployeeRequest request) {
        Employee employee = employeeService.findByUsername(username);
        try {
            employeeService.updateEmployeeData(request.getNom(), request.getPrénom(), request.getEmail(), request.getPoste(), request.getLieu_de_travail(), request.getDépartement(), username);
            return ResponseEntity.ok(employee);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allemployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allemployees, HttpStatus.OK);
    }

    @DeleteMapping("/{Matricule}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer Matricule) {
        String status = employeeService.deletebyId(Matricule);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    @PutMapping("/{username}/password")
    public ResponseEntity<Employee> updatePassword(@PathVariable String username, @RequestBody PasswordUpdateRequest request) {
        Employee employee = employeeService.findByUsername(username);
        employeeService.updatePassword(username, request.getNewPassword());
        return ResponseEntity.ok(employee);
    }



}
