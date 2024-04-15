package com.example.pfe.Employé.Dto;

import com.example.pfe.Employé.Enum.Role;
import lombok.Data;

@Data
public class EmployeeRequest {


        private String nom;

        private String prénom;

        private String email;

        private String poste ;

        private String lieu_de_travail;

        private String département ;

        private Role role ;


    }








