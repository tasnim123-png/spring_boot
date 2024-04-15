package com.example.pfe.Employé.Service;

import com.example.pfe.Employé.Entity.Employee;


import java.util.List;
import java.util.Optional;

public interface EmployeeService {


    public Employee update(Employee employee);

    public List<Employee> getAllEmployees();

    public  String deletebyId(Integer Matricule);

    public Employee findByUsername(String username);

    public  Employee updateEmployeeData(String Nom, String Prénom, String Email, String Poste, String Lieu_de_travail,
                            String Département, String username);

    public Employee updatePassword(String username, String newPassword);







}
