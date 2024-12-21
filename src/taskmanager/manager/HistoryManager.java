package taskmanager.manager;

import taskmanager.Task;

import java.util.List;

public interface HistoryManager {
    void addHistory(Task task);

    List<Task> getHistory();

    void removeID(int id);
}