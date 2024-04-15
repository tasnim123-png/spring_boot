package com.example.pfe.Employé.Service;

import com.example.pfe.Compétence.Skill;
import com.example.pfe.Compétence.SkillRepository;
import com.example.pfe.Employé.Entity.Employee;
import com.example.pfe.Employé.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public Employee update(Employee employee) {
        return   employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String deletebyId(Integer Matricule) {
        if(employeeRepository.existsById(Matricule)){
            employeeRepository.deleteById(Matricule);
            return "Employee Sucessfully deleted";
        }
        else{
            return "Not Found";
        }
    }


    @Override
    public Employee findByUsername(String username) {
       return (Employee) employeeRepository.findByUsername(username);
    }


    @Override
    public Employee updateEmployeeData( String Nom,String Prénom, String Email,String Poste ,String Lieu_de_travail,String Département,String username) {
        Employee employee = (Employee) employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        employee.setNom(Nom);
        employee.setPrénom(Prénom);
        employee.setEmail(Email);
        employee.setPoste(Poste);
        employee.setLieu_de_travail(Lieu_de_travail);
        employee.setDépartement(Département);
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee updatePassword(String username, String newPassword) {
        Employee employee = (Employee) employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
        return employee;
    }











}



