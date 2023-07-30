package net.erasmatov.crudapp.service;

import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.jdbc.JdbcDeveloperRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceTest {
    @InjectMocks
    private DeveloperService developerService;
    @Mock
    private JdbcDeveloperRepositoryImpl developerRepository;

    private List<Developer> developerList;
    private Developer developer1;
    private Developer developer2;

    @BeforeEach
    void setUp() {
        developer1 = new Developer(1, "Erlan", "Smatov",
                List.of(new Skill(1, "Smart", Status.ACTIVE),
                        new Skill(2, "Fast", Status.ACTIVE)),
                new Specialty(1, "Java", Status.ACTIVE),
                Status.ACTIVE);

        developer2 = new Developer(2, "Maria", "Mudunova",
                List.of(new Skill(3, "Reliable", Status.ACTIVE),
                        new Skill(2, "Fast", Status.ACTIVE)),
                new Specialty(2, "Javascript", Status.ACTIVE),
                Status.ACTIVE);

        developerList = List.of(developer1, developer2);
    }

    @Test
    void getAllDevelopers() {
        when(developerRepository.getAll()).thenReturn(List.of(developer1, developer2));

        List<Developer> resultDevList = developerService.getAllDevelopers();
        assertIterableEquals(developerList, resultDevList,
                "list of developers is not equals");
    }

    @Test
    void createDeveloper() {
        when(developerRepository.save(developer1)).thenReturn(developer1);

        Developer resultDeveloper = developerService.createDeveloper(developer1);
        assertSame(developer1, resultDeveloper,
                "saved developer is not same");
    }

    @Test
    void getDeveloperById() {
        when(developerRepository.getById(isA(Integer.class))).thenAnswer(invocation -> {
            switch ((Integer) invocation.getArgument(0)) {
                case 1:
                    return developer1;
                case 2:
                    return developer2;
                default:
                    return null;
            }
        });
        Developer resultDeveloper = developerService.getDeveloperById(2);

        ArgumentCaptor<Integer> requestCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(developerRepository, times(1)).getById(requestCaptor.capture());

        assertEquals(resultDeveloper.getId(), requestCaptor.getValue());
        assertSame(developer2, resultDeveloper, "received developer is not same");
    }

    @Test
    void updateDeveloper() {
        when(developerRepository.update(any(Developer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, Developer.class));
        developerService.updateDeveloper(developer1);
        verify(developerRepository, times(1)).update(any(Developer.class));
    }

    @Test
    void deleteDeveloperById() {
        doNothing().when(developerRepository).deleteById(isA(Integer.class));
        developerService.deleteDeveloperById(2);
        verify(developerRepository, times(1)).deleteById(isA(Integer.class));
    }

}