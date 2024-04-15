package com.example.pfe.Employee_Skill;

import com.example.pfe.Compétence.Skill;
import com.example.pfe.Employé.Entity.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "employés_compétences")
public class EmployeeSkill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_employeeskill;

    @ManyToOne()
    @JoinColumn(name = "employé_matricule",nullable = false)
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name = "compétence_id",nullable = false)
    private Skill skill;



}