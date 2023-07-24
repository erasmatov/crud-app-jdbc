package net.erasmatov.crudapp.service;

import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.repository.SkillRepository;
import net.erasmatov.crudapp.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

    public List<Skill> getSkills() {
        return skillRepository.getAll();
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill getSkillById(Integer id) {
        return skillRepository.getById(id);
    }

    public Skill updateSkill(Skill skill) {
        return skillRepository.update(skill);
    }

    public void deleteSkillById(Integer id) {
        skillRepository.deleteById(id);
    }
}
