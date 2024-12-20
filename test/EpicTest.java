import taskmanager.Epic;
import taskmanager.InMemoryTaskManager;
import taskmanager.SubTask;
import org.junit.jupiter.api.Test;

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




}