package test;

import taskManager.Epic;
import taskManager.InMemoryTaskManager;
import taskManager.SubTask;
import taskManager.Task;
import taskManager.TaskUneversal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addTask() {

        TaskUneversal task = new TaskUneversal("убрать квартиру", "убрать кухню");
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

        SubTask subTask = new SubTask("убрать квартиру", "убрать кухню");
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
        TaskUneversal task = new TaskUneversal("task1", "description1");
        int taskId = taskManager.addTask(task);
        Task task2 = taskManager.getTask(taskId);
        assertEquals(task.getName(), task2.getName());
        assertEquals(task.getDescription(), task2.getDescription());
        assertEquals(task.getStatus(), task2.getStatus());

    }

}