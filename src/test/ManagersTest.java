package test;


import TaskManager.Epic;
import TaskManager.Manager.InMemoryHistoryManager;
import TaskManager.Manager.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void add() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Epic epic = new Epic("new epic", "description");
        inMemoryHistoryManager.addHistory(epic);
        Managers manager = new Managers();
        assertNotNull(manager.getDefaultHistory());
    }


}
