import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskmanager.exceptions.ManagerSaveException;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.manager.FileBackedTaskManager;
import taskmanager.model.Epic;
import taskmanager.model.SubTask;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTaskManagerTest {
    FileBackedTaskManager manager = new FileBackedTaskManager();
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Epic epic1 = new Epic("Epic1", "Description1");

    @Test
    public void testByTime() {
        Duration duration = Duration.ofMinutes(60);
        LocalDateTime lcd = LocalDateTime.now().plusHours(7);
        SubTask sub = new SubTask("test", "test", duration, lcd, epic1.getId());
        assertEquals(duration, sub.getDuration());
        assertEquals(lcd, sub.getStartTime());
    }


    @Test
    void loadFile() {
        File tempFile = createTempFile();
        //FileBackedTaskManager.loadFile(tempFile);
        //assertEquals(manager.allTasks, load.allTasks);
    }

    private File createTempFile() {
        try {
            return File.createTempFile("test-tasks", ".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

  /*  @Test
    void testLoadFileThrowException() {
        File file = new File("nonfile.csv");
        Assertions.assertThrows(ManagerSaveException.class, () ->
        {
          FileBackedTaskManager.loadFile(file);
        });
    }*/

    @Test
    void testDoSaveNotException() {
        FileBackedTaskManager manager1 = new FileBackedTaskManager();
        Epic epic = new Epic("name", "description");
        manager1.addEpic(epic);
        File tempFile = new File("taskFile.csv");
        Assertions.assertDoesNotThrow(() ->
        {
            manager1.save();
        });
    }

}