package test;


import taskManager.Epic;
import taskManager.manager.InMemoryHistoryManager;
import taskManager.manager.Managers;
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
