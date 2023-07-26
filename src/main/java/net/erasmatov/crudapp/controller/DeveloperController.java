package net.erasmatov.crudapp.controller;

import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService = new DeveloperService();

    public List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    public Developer createDeveloper(Developer developer) {
        return developerService.createDeveloper(developer);
    }

    public Developer getDeveloperById(Integer id) {
        return developerService.getDeveloperById(id);
    }

    public Developer updateDeveloper(Developer developer) {
        return developerService.updateDeveloper(developer);
    }

    public void deleteDeveloperById(Integer id) {
        developerService.deleteDeveloperById(id);
    }

}
