package com.example.pfe.Compétence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//http://localhost:8080/api/skill/

@RestController
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill){
     Skill savedSkill = skillService.insert(skill);
        return new ResponseEntity<>(savedSkill, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable("id") Integer id, @RequestBody Skill skill){
        skill.setId(id);
        Skill updatedSkill= skillService.update(skill);
        return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Integer id) {
       Skill skill = (Skill) skillService.getbyId(id);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills(){
        List<Skill> allskills = skillService.getAllSkills();
        return new ResponseEntity<>(allskills,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable Integer id) {
        String status =  skillService.deletebyId(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
