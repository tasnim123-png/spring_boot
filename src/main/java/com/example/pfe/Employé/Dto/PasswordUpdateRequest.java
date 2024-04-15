package com.example.pfe.Employé.Dto;

import lombok.Data;

@Data
public class PasswordUpdateRequest {

    private String newPassword;
    private String confirmPassword;


}
