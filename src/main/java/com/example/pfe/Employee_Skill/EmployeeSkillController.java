package com.example.pfe.Employee_Skill;

import com.example.pfe.Compétence.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;

    @GetMapping("/user/{userId}/skills")
    public ResponseEntity<List<EmployeeSkill>> getAllUserSkills(@PathVariable("userId") Integer employé_matricule) {
        List<EmployeeSkill> employeeSkills = employeeSkillService.getEmployeeSkills(employé_matricule);
        return ResponseEntity.ok(employeeSkills);
    }

    @PostMapping("/user/{employé_matricule}/skill/{compétence_id}")
    public ResponseEntity<EmployeeSkill> addUserSkill(@PathVariable("employé_matricule") Integer employé_matricule,
                                                      @PathVariable("compétence_id") Integer compétence_id) {
        EmployeeSkill employeeSkill = employeeSkillService.addEmployeeSkill(employé_matricule, compétence_id);
        return ResponseEntity.ok(employeeSkill);
    }

    @DeleteMapping("/user/{employé_matricule}/skill/{compétence_id}")
    public ResponseEntity<String> deleteUserSkill(@PathVariable("employé_matricule") Integer employé_matricule,
                                                  @PathVariable("compétence_id") Integer compétence_id) {
        employeeSkillService.deleteEmployeeSkill(employé_matricule, compétence_id);
        return ResponseEntity.status(HttpStatus.OK).body("User skill deleted successfully.");
    }

    @GetMapping("/user/{employé_matricule}/skill/{compétence_id}")
    public ResponseEntity<List<EmployeeSkill>> getUserSkill(@PathVariable("employé_matricule") Integer employé_matricule,
                                                            @PathVariable("compétence_id") Integer compétence_id) {
        List<EmployeeSkill> employeeSkill = employeeSkillService.getEmployeeSkill(employé_matricule, compétence_id);
        if (!employeeSkill.isEmpty()) {
            return ResponseEntity.ok(employeeSkill);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{employé_matricule}/skill/{compétence_id}")
    public ResponseEntity<EmployeeSkill> updateUserSkill(
            @PathVariable("employé_matricule") Integer employé_matricule,
            @PathVariable("compétence_id") Integer compétence_id,
            @RequestBody Skill updatedSkill) {
       EmployeeSkill  employeeSkill = employeeSkillService.updateEmployeeSkill(employé_matricule, compétence_id,updatedSkill);
        if (employeeSkill == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeSkill);
    }


}
