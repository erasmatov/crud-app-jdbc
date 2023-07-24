package net.erasmatov.crudapp.service;

import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.jdbc.JdbcDeveloperRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceTest {
    @Mock
    JdbcDeveloperRepositoryImpl developerRepository;
    @InjectMocks
    DeveloperService developerService = new DeveloperService();
    List<Developer> developersList;
    Developer developerFirst;
    Developer developerSecond;


    @Test
    void getAllDevelopers() {
        when(developerRepository.getAll()).thenReturn(developersList);
        List<Developer> currentList = developerService.getAllDevelopers();
        assertEquals(developersList.get(0), currentList.get(0));
    }


    @Test
    void createDeveloper() {
        when(developerRepository.save(developerSecond)).thenReturn(developerSecond);
        developersList.add(developerService.createDeveloper(developerSecond));
        assertSame(developerSecond, developersList.get(1));
    }


    @Test
    void getDeveloperById() {
        when(developerRepository.getById(1)).thenReturn(developerFirst);
        Developer developer = developerService.getDeveloperById(1);
        assertSame(developerFirst, developer);
    }


    @Test
    void updateDeveloper() {
        Developer developer = developerFirst;
        developer.setId(4);
        when(developerRepository.update(developer)).thenReturn(developer);

        Developer updatedDeveloper = developerRepository.update(developer);
        assertEquals(developer.getId(), updatedDeveloper.getId());
    }


    @Test
    void deleteDeveloperById() {
        Mockito.doNothing().when(developerRepository).deleteById(any(Integer.class));
        Mockito.verify(developerRepository, times(1)).deleteById(any());
        developerService.deleteDeveloperById(1);
    }


    @BeforeEach
    void setUp() {
        developersList = new ArrayList<>();

        developerFirst = new Developer();
        developerFirst.setId(1);
        developerFirst.setFirstName("Erlan");
        developerFirst.setLastName("Smatov");
        developerFirst.setSkills(new ArrayList<>(Arrays.asList(new Skill(1, "Proactive", Status.ACTIVE))));
        developerFirst.setSpecialty(new Specialty(2, "Java", Status.ACTIVE));
        developerFirst.setStatus(Status.ACTIVE);

        developerSecond = new Developer();
        developerSecond.setId(2);
        developerSecond.setFirstName("Mariia");
        developerSecond.setLastName("Mudunova");
        developerSecond.setSkills(new ArrayList<>(Arrays.asList(new Skill(3, "Fast", Status.ACTIVE), new Skill(4, "Responsible", Status.ACTIVE))));
        developerSecond.setSpecialty(new Specialty(3, "JavaScript", Status.ACTIVE));
        developerSecond.setStatus(Status.ACTIVE);

        developersList.add(developerFirst);
    }
}