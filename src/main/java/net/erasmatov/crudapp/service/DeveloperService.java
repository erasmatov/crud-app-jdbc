package net.erasmatov.crudapp.service;

import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.repository.DeveloperRepository;
import net.erasmatov.crudapp.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    DeveloperRepository developerRepository = new JdbcDeveloperRepositoryImpl();

    public List<Developer> getAllDevelopers() {
        return developerRepository.getAll();
    }

    public Developer createDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    public Developer getDeveloperById(Integer id) {
        return developerRepository.getById(id);
    }

    public Developer updateDeveloper(Developer developer) {
        return developerRepository.update(developer);
    }

    public void deleteDeveloperById(Integer id) {
        developerRepository.deleteById(id);
    }

}
