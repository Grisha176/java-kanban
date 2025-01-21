

import taskmanager.model.TaskUneversal;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskUneversalTest {

    @Test
    void equalsObj() {
        TaskUneversal task1 = new TaskUneversal("Task1", "Description1", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        TaskUneversal task2 = new TaskUneversal("Task1", "Description1", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        assertEquals(task2, task1);
    }

    @Test
    void noEqualsObj() {
        TaskUneversal task1 = new TaskUneversal("Task1", "Description1", Duration.ofMinutes(70), LocalDateTime.now().plusHours(1));
        TaskUneversal task2 = new TaskUneversal("Task2", "Description2", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        assertNotEquals(task1, task2);
    }

}