package com.example.pfe.Compétence;

import java.util.List;

public interface SkillService {
    public Skill insert(Skill skill);

    public Skill update(Skill skill);

    public Object getbyId(Integer id);

    public List<Skill> getAllSkills();

    public  String deletebyId(Integer id);
}
