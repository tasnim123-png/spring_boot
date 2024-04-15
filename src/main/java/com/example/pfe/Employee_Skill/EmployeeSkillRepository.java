package com.example.pfe.Employee_Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill,Integer> {


    @Query("SELECT e FROM EmployeeSkill e WHERE e.employee.Matricule= :employé_matricule")
    List<EmployeeSkill> findByUserId(@Param("employé_matricule") Integer employé_matricule);


    @Query("SELECT e FROM EmployeeSkill e WHERE e.employee.Matricule= :employé_matricule AND e.skill.id = :compétence_id")
    List<EmployeeSkill> findByUserIdAndSkillId(@Param("employé_matricule") Integer employé_matricule, @Param("compétence_id") Integer compétence_id);

    @Query("SELECT e FROM EmployeeSkill e WHERE e.skill.id = :compétence_id")
    List<EmployeeSkill> findBySkillId(@Param("compétence_id") Integer compétence_id);


}
