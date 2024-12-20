package test;

import TaskManager.Epic;
import TaskManager.InMemoryTaskManager;
import TaskManager.SubTask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    Epic epic1 = new Epic("Epic1", "Description1");

    @Test
    void EqualsObj() {

        Epic epic2 = new Epic("Epic1", "Description1");
        assertEquals(epic1, epic2);
    }

    @Test
    void NoEqualsObj() {

        Epic epic2 = new Epic("Epic2", "Description2");
        assertNotEquals(epic1, epic2);
    }

    @Test
    void addEpicInSubTask() {
        assertEquals(0, epic1.getSubtaskIds().size());
        epic1.addSubtaskIds(epic1.getId());
        assertEquals(0, epic1.getSubtaskIds().size());
    }

    @Test
    void testSubTaskIds() {
        SubTask subTask1 = new SubTask("SubTask1", "Description1");
        SubTask subTask2 = new SubTask("SubTask2", "Description2");
        epic1.addSubtaskIds(subTask1.getId());
        epic1.addSubtaskIds(subTask2.getId());
        assertEquals(2, epic1.getSubtaskIds().size());
        epic1.addSubtaskIds(epic1.getId());
        assertFalse(epic1.getSubtaskIds().contains(epic1.getId()));
    }


}