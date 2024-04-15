package com.example.pfe.Compétence;


import com.example.pfe.Employee_Skill.EmployeeSkill;
import com.example.pfe.Employé.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="compétences")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Skill {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id ;

        @Column(nullable = false)
        private String nom_compétence;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Niveau niveau;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Domaine domaine;


}
