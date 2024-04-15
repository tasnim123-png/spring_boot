package com.example.pfe.Employee_Skill;

import com.example.pfe.Compétence.Skill;

import java.util.List;

public interface EmployeeSkillService {

   List<EmployeeSkill> getEmployeeSkills(Integer employé_matricule);

    EmployeeSkill addEmployeeSkill(Integer employé_matricule, Integer compétence_id);

    List<EmployeeSkill> getEmployeeSkill(Integer employé_matricule, Integer compétence_id);


    void deleteEmployeeSkill(Integer employé_matricule, Integer compétence_id);

    EmployeeSkill updateEmployeeSkill(Integer employé_matricule, Integer compétence_id, Skill updatedSkill);


}
