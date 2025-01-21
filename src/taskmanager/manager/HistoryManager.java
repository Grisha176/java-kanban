package taskmanager.manager;

import taskmanager.model.Task;

import java.util.List;

public interface HistoryManager {
    void addHistory(Task task);

    List<Task> getHistory();

    void removeID(int id);
}