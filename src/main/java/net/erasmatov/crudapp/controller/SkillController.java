package net.erasmatov.crudapp.controller;

import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.service.SkillService;

import java.util.List;

public class SkillController {
    SkillService skillService = new SkillService();

    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    public Skill createSkill(Skill skill) {
        return skillService.createSkill(skill);
    }

    public Skill getSkillById(Integer id) {
        return skillService.getSkillById(id);
    }

    public Skill updateSkill(Skill skill) {
        return skillService.updateSkill(skill);
    }

    public void deleteSkillById(Integer id) {
        skillService.deleteSkillById(id);
    }
}
