package com.example.pfe.Compétence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;


    @Override
    public Skill insert(Skill skill) {
        return   skillRepository.save(skill) ;
    }

    @Override
    public Skill update(Skill skill) {
        return   skillRepository.save(skill) ;
    }

    @Override
    public Object getbyId(Integer id) {
        Optional<Skill> findbyId = skillRepository.findById(id);
        return findbyId.get();
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public String deletebyId(Integer id) {
        if(skillRepository.existsById(id)){
           skillRepository.deleteById(id);
            return "Skill Sucessfully deleted";
        }
        else{
            return "Not Found";
        }

    }
}
