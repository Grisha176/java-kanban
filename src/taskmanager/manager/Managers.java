package taskmanager.manager;

public class Managers {

    public static TaskManager1 getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}