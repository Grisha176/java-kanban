package taskmanagerTest.managerTest;

import taskmanager.manager.InMemoryHistoryManager;
import taskmanager.model.Task;
import taskmanager.model.TaskUneversal;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskUneversal task = new TaskUneversal("поесть", "приготовить ужин", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
    InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @Test
    void add() {
        TaskUneversal task2 = new TaskUneversal("пойти за покупками", "купить фрукты", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        manager.addHistory(task);
        manager.addHistory(task2);
        List<Task> history = manager.getHistory();
        assertNotNull(history, "history not null");
        assertEquals(2, history.size(), "history not null");
    }

    @Test
    void removeNode() {
        TaskUneversal task = new TaskUneversal("поесть", "приготовить ужин", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        manager.addHistory(task);
        assertEquals(1, manager.getHistory().size());
        manager.addHistory(task);
        assertEquals(1, manager.getHistory().size());
    }

    @Test
    void nullHistory() {
        List<Task> list = new ArrayList<>();
        assertEquals(list, manager.getHistory());
    }

}