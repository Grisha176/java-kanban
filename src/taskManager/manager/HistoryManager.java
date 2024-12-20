package taskManager.manager;

import taskManager.Task;

import java.util.List;

public interface HistoryManager {
    void addHistory(Task task);

    List<Task> getHistory();

    void removeID(int id);
}