package com.example.pfe.Employee_Skill;

import com.example.pfe.Compétence.Skill;
import com.example.pfe.Compétence.SkillRepository;
import com.example.pfe.Employé.Entity.Employee;
import com.example.pfe.Employé.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;


    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

@Override
    public List<EmployeeSkill> getEmployeeSkills(Integer employé_matricule) {
        return employeeSkillRepository.findByUserId(employé_matricule);
    }

    @Override
    public EmployeeSkill addEmployeeSkill(Integer employé_matricule, Integer compétence_id) {
        Employee employee = employeeRepository.findById(employé_matricule).orElse(null);
        Skill skill = skillRepository.findById(compétence_id).orElse(null);

        // Check if both employee and skill exist
        if (employee == null || skill == null) {
            // Handle error or return null
            return null;
        }
        // Create a new EmployeeSkill instance and set associations
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);

        // Save the EmployeeSkill entity
        return employeeSkillRepository.save(employeeSkill);
    }

    @Override
    public List<EmployeeSkill> getEmployeeSkill(Integer employé_matricule, Integer compétence_id) {
        return employeeSkillRepository.findByUserIdAndSkillId(employé_matricule, compétence_id);
    }

    @Override
    public void deleteEmployeeSkill(Integer employé_matricule, Integer compétence_id) {
        List<EmployeeSkill> EmployeeSkill = employeeSkillRepository.findByUserIdAndSkillId(employé_matricule, compétence_id);
        employeeSkillRepository.deleteAll(EmployeeSkill);
    }


 @Override
    public EmployeeSkill updateEmployeeSkill(Integer employé_matricule, Integer compétence_id, Skill updatedSkill) {
        // Fetch EmployeeSkill based on the skill id provided in the path
        List<EmployeeSkill> employeeSkillsWithSkillId = employeeSkillRepository.findBySkillId(compétence_id);
        EmployeeSkill existingEmployeeSkill = null;

        // Fetch EmployeeSkill based on the Employee id provided in the path
        for (EmployeeSkill employeeSkill : employeeSkillsWithSkillId) {
            if (employeeSkill.getEmployee().getMatricule().equals(employé_matricule)) {
                existingEmployeeSkill = employeeSkill;
                break;
            }
        }
        // if skill id / employee id are wrong or Employeeskill does not exist return null
        if (existingEmployeeSkill == null) {
            return null;
        }

        // Find the existing skill in the database in the skill table
        Skill existingSkill = skillRepository.findById(compétence_id).orElse(null);
        if (existingSkill == null) {
            return null;
        }

        // Update attributes of existingSkill with attributes from updatedSkill
        existingSkill.setNom_compétence(updatedSkill.getNom_compétence());
        existingSkill.setNiveau(updatedSkill.getNiveau());
        existingSkill.setDomaine(updatedSkill.getDomaine());

        // Save the updated skill in the skill table
        Skill savedSkill = skillRepository.save(existingSkill);

        // Update the skill reference in existingEmployeeSkill
        existingEmployeeSkill.setSkill(savedSkill);

        // Save the updated Employee skill in the EmployeeSkill Table
        return employeeSkillRepository.save(existingEmployeeSkill);
    }
}
