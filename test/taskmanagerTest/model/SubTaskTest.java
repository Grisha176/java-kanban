package taskmanagerTest.model;

import org.junit.jupiter.api.BeforeEach;
import taskmanager.model.Epic;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.SubTask;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    Epic epic1;

    @BeforeEach
    void setUp() {
        epic1 = new Epic("Epic1", "Description1");
    }

    @Test
    void equals() {
        SubTask subTask = new SubTask("SubTask1", "Description1", ofMinutes(90), LocalDateTime.now().plusHours(1), epic1.getId());
        SubTask subTask2 = new SubTask("SubTask1", "Description1", ofMinutes(90), LocalDateTime.now().plusHours(1), epic1.getId());
        assertEquals(subTask2, subTask);

    }

    @Test
    void noEqualsObj() {
        SubTask subTask = new SubTask("SubTask1", "Description1", ofMinutes(50), LocalDateTime.now().plusHours(9), epic1.getId());
        SubTask subTask2 = new SubTask("SubTask2", "Description2", ofMinutes(90), LocalDateTime.now().plusHours(1), epic1.getId());
        assertNotEquals(subTask2, subTask);

    }

    @Test
    void delete() {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpic(epic1);
        SubTask subTask = new SubTask("SubTask1", "Description1", Duration.ofMinutes(98), LocalDateTime.now().plusHours(1), epic1.getId());
        manager.addSubTask(subTask);
        manager.deleteSubTask(subTask.getId());
        assertEquals(0, subTask.getEpicId());

    }

    @Test
    void subtaskHaveEpic() {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpic(epic1);
        SubTask subTask = new SubTask("SubTask1", "Description1", Duration.ofMinutes(98), LocalDateTime.now().plusHours(1), epic1.getId());
        manager.addSubTask(subTask);
        Integer EpicId = epic1.getId();
        assertEquals(EpicId, subTask.getEpicId());
    }
}
