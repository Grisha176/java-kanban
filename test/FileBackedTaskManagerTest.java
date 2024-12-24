import org.junit.jupiter.api.Test;
import taskmanager.Epic;
import taskmanager.FileBackedTaskManager;
import taskmanager.InMemoryTaskManager;
import taskmanager.TaskUneversal;

import java.io.File;
import java.io.IOException;

import static java.io.File.createTempFile;
import static org.junit.jupiter.api.Assertions.*;


class FileBackedTaskManagerTest {
    FileBackedTaskManager manager = new FileBackedTaskManager();
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void loadFile() {
        File tempFile = createTempFile();
        InMemoryTaskManager load = FileBackedTaskManager.loadFile(tempFile);
        assertEquals(manager.allTasks, load.allTasks);
    }

    private File createTempFile() {
        try {
            return File.createTempFile("test-tasks", ".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        TaskUneversal taskUneversal = new TaskUneversal("name", "descr");
        Epic epic = new Epic("name", "descr");
        manager.addTask(taskUneversal);
        manager.addEpic(epic);
        assertNotNull(inMemoryTaskManager.allTasks);
    }
}