package test;

import taskmanager.InMemoryTaskManager;
import taskmanager.Task;
import taskmanager.TaskUneversal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task = new Task();
    TaskUneversal taskUneversal = new TaskUneversal("name1", "description1");
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void count() {
        inMemoryTaskManager.addTask(taskUneversal);
        assertNotEquals(task.count, taskUneversal.getId());
    }


}