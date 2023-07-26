package net.erasmatov.crudapp.controller;

import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.service.SpecialtyService;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyService specialtyService = new SpecialtyService();

    public List<Specialty> getSpecialties() {
        return specialtyService.getSpecialties();
    }

    public Specialty createSpecialty(Specialty specialty) {
        return specialtyService.createSpecialty(specialty);
    }

    public Specialty getSpecialtyById(Integer id) {
        return specialtyService.getSpecialtyById(id);
    }

    public Specialty updateSpecialty(Specialty specialty) {
        return specialtyService.updateSpecialty(specialty);
    }

    public void deleteSpecialtyById(Integer id) {
        specialtyService.deleteSpecialtyById(id);
    }

}
