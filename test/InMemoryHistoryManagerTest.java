package test;

import taskmanager.manager.InMemoryHistoryManager;
import taskmanager.Task;
import taskmanager.TaskUneversal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskUneversal task = new TaskUneversal("поесть", "приготовить ужин");
    InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @Test
    void add() {
        TaskUneversal task2 = new TaskUneversal("пойти за покупками", "купить фрукты");
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        manager.addHistory(task);
        manager.addHistory(task2);
        List<Task> history = manager.getHistory();
        assertNotNull(history, "history not null");
        assertEquals(2, history.size(), "history not null");
    }

    @Test
    void removeNode() {
        TaskUneversal task = new TaskUneversal("поесть", "приготовить ужин");
        manager.addHistory(task);
        assertEquals(1, manager.getHistory().size());
        manager.addHistory(task);
        assertEquals(1, manager.getHistory().size());


    }

}