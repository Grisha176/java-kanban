package test;

import TaskManager.TaskUneversal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskUneversalTest {

    @Test
    void equalsObj() {
        TaskUneversal task1 = new TaskUneversal("Task1", "Description1");
        TaskUneversal task2 = new TaskUneversal("Task1", "Description1");
        assertEquals(task2, task1);
    }

    @Test
    void noEqualsObj() {
        TaskUneversal task1 = new TaskUneversal("Task1", "Description1");
        TaskUneversal task2 = new TaskUneversal("Task2", "Description2");
        assertNotEquals(task1, task2);
    }

}