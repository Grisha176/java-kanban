package test;

import org.junit.jupiter.api.Test;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Epic;
import taskmanager.model.SubTask;
import taskmanager.model.Task;
import taskmanager.model.TaskUneversal;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    @Test
    void addTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        TaskUneversal task = new TaskUneversal("убрать квартиру", "убрать кухню", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        int taskId = taskManager.addTask(task);
        Task id = taskManager.getTask(taskId);
        assertNotNull(id, "Задачи нету");
        assertEquals(task, id, "Задачи не совпадают");


        ArrayList<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращены");
        assertEquals(1, tasks.size(), "размер не совпадает");
        assertEquals(task, tasks.getFirst(), "задачи не совпадают");
    }

    @Test
    void addEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("убрать квартиру", "убрать кухню");

        int epicId = taskManager.addEpic(epic);
        Epic id = taskManager.getEpic(epicId);
        assertNotNull(id, "Задачи нету");
        assertEquals(epic, id, "Задачи не совпадают");

        ArrayList<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Задачи не возвращены");
        assertEquals(1, epics.size(), "размер не совпадает");
        assertEquals(epic, epics.get(0), "задачи не совпадают");
    }

    @Test
    void addSub() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("убрать квартиру", "убрать кухню");
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask("убрать квартиру", "убрать кухню", Duration.ofMinutes(54), LocalDateTime.now(), epic.getId());
        int subtaskId = taskManager.addSubTask(subTask);
        SubTask id = taskManager.getSubTask(subtaskId);
        assertNotNull(id, "Задачи нету");
        assertEquals(subTask, id, "Задачи не совпадают");

        ArrayList<SubTask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Задачи не возвращены");
        assertEquals(1, subtasks.size(), "размер не совпадает");
        assertEquals(subTask, subtasks.get(0), "задачи не совпадают");
    }

    @Test
    void addInManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        TaskUneversal task = new TaskUneversal("task1", "description1", Duration.ofMinutes(30), LocalDateTime.now().plusMinutes(30));
        int taskId = taskManager.addTask(task);
        Task task2 = taskManager.getTask(taskId);
        assertEquals(task.getName(), task2.getName());
        assertEquals(task.getDescription(), task2.getDescription());
        assertEquals(task.getStatus(), task2.getStatus());

    }

    @Test
    void testCheckTimeOverlapsTrue() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        TaskUneversal task = new TaskUneversal("task", "descriprion1", Duration.ofMinutes(30), LocalDateTime.now().plusMinutes(30));
        TaskUneversal task2 = new TaskUneversal("task2", "descriprion2", Duration.ofMinutes(10), LocalDateTime.now().plusMinutes(50));
        assertTrue(taskManager.checkTimeOverlaps(task, task2));
    }

    @Test
    void testCheckTimeOverlapsFalse() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        TaskUneversal task = new TaskUneversal("task", "descriprion1", Duration.ofMinutes(30), LocalDateTime.now().plusMinutes(30));
        TaskUneversal task2 = new TaskUneversal("task2", "descriprion2", Duration.ofMinutes(10), LocalDateTime.now().plusHours(2));
        assertFalse(taskManager.checkTimeOverlaps(task, task2));
    }

}