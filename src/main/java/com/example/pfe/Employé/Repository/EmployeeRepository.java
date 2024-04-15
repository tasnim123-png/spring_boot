package com.example.pfe.Employé.Repository;

import com.example.pfe.Compétence.Skill;
import com.example.pfe.Employé.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    UserDetails findByUsername(String username);


}
