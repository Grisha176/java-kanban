package test;

import taskmanager.InMemoryTaskManager;
import taskmanager.SubTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    InMemoryTaskManager manager = new InMemoryTaskManager();

    @Test
    void equals() {
        SubTask subTask = new SubTask("SubTask1", "Description1");
        SubTask subTask2 = new SubTask("SubTask1", "Description1");
        assertEquals(subTask2, subTask);

    }

    @Test
    void noEqualsObj() {
        SubTask subTask = new SubTask("SubTask1", "Description1");
        SubTask subTask2 = new SubTask("SubTask2", "Description2");
        assertNotEquals(subTask2, subTask);

    }

    @Test
    void delete() {
        SubTask subTask = new SubTask("SubTask1", "Description1");
        manager.deleteSubTask(subTask.getId());
        assertEquals(0, subTask.getEpicId());

    }

}
