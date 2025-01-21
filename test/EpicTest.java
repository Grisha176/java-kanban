import taskmanager.model.Epic;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Progress;
import taskmanager.model.SubTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    Epic epic1 = new Epic("Epic1", "Description1");

    @Test
    void equalsObj() {
        Epic epic2 = new Epic("Epic1", "Description1");
        assertEquals(epic1, epic2);
    }

    @Test
    void noEqualsObj() {
        Epic epic2 = new Epic("Epic2", "Description2");
        assertNotEquals(epic1, epic2);
    }

    @Test
    void subTaskStatusNew() {
        SubTask subTask = new SubTask("SubTask1", "Description1", ofMinutes(50), LocalDateTime.now().plusHours(9), epic1.getId());
        SubTask subTask2 = new SubTask("SubTask2", "Description2", ofMinutes(90), LocalDateTime.now().plusHours(1), epic1.getId());
        SubTask subTask3 = new SubTask("SubTask3", "Description3", ofMinutes(10), LocalDateTime.now().plusHours(4), epic1.getId());
        subTask.setProgress(Progress.NEW);
        subTask.setProgress(Progress.NEW);
        subTask.setProgress(Progress.NEW);
        assertEquals(Progress.NEW, epic1.getStatus());

    }


}