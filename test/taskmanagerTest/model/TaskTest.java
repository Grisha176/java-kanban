package taskmanagerTest.model;

import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Task;
import taskmanager.model.TaskUneversal;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task = new Task();
    TaskUneversal taskUneversal = new TaskUneversal("name1", "description1", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void count() {
        inMemoryTaskManager.addTask(taskUneversal);
        assertNotEquals(task.count, taskUneversal.getId());
    }


}