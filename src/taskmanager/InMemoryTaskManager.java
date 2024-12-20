package taskmanager;

import taskmanager.manager.HistoryManager;
import taskmanager.manager.Managers;
import taskmanager.manager.TaskManager1;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager1 {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subtask = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.addHistory(task);
        return tasks.get(id);

    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.addHistory(epic);
        return epics.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subtask.get(id);
        historyManager.addHistory(subTask);
        return subtask.get(id);
    }

    @Override
    public HistoryManager getHistory() {
        return historyManager;
    }


    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtask.values());

    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public int addTask(Task task) {
        task.id = Task.count++;
        tasks.put(task.id, task);
        return task.id;
    }

    @Override
    public int addEpic(Epic epic) {
        epic.id = Task.count++;
        epics.put(epic.id, epic);
        return epic.id;
        //historyManager.addHistory();
    }

    @Override
    public Integer addSubTask(SubTask subTasks) {
        subTasks.id = Task.count++;
        subtask.put(subTasks.id, subTasks);
        Epic ep = epics.get(subTasks.idEpic);
        ep.subtaskIds.add(subTasks.id);
        return subTasks.id;
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask subTasks = subtask.remove(id);
        if (subTasks == null) {
            return;
        }
        Epic epic = epics.get(subTasks.getEpicId());
        if (epic != null) {
            epic.removeSubTaskId(id);
            updateEpicStatus(epic.getId(), epic);
        }

    }

    @Override
    public void deleteEpics(int id) {
        final Epic epic = epics.remove(id);
        if (null == epic.getSubtaskIds()) {
             System.out.println("пустой эпик");
        } else {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtask.remove((subtaskId));
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() {
        epics.clear();
        subtask.clear();
    }

    @Override
    public void deleteAllSubTask() {
        ArrayList<Task> task = new ArrayList<>();
        task = getTasks();
        for (Epic epic : epics.values()) {
            epic.cleansubtaskIds();
            updateEpicStatus(epic.getId(), epic);
        }
        subtask.clear();
    }

    @Override
    public void updateEpicStatus(int id, Epic epic) {
        Epic foundEpic = epics.get(id);
        if (foundEpic == null) {
            return;
        }
        int totalSubtasks = foundEpic.getSubtaskIds().size();
        int doneSubtasks = 0;
        for (int subtaskId : foundEpic.getSubtaskIds()) {
            SubTask subTask = subtask.get(subtaskId);
            if (subTask.getStatus() == Progress.DONE) {
                doneSubtasks++;
            } else if (doneSubtasks == 0) {
                foundEpic.setProgress(Progress.NEW);
            } else {
                foundEpic.setProgress(Progress.IN_PROGRESS);
            }
        }

        if (totalSubtasks > 0) {
            if (doneSubtasks == totalSubtasks) {
                foundEpic.setProgress(Progress.DONE);
            } else if (doneSubtasks == 0) {
                foundEpic.setProgress(Progress.NEW);
            } else {
                foundEpic.setProgress(Progress.IN_PROGRESS);
            }
        }

        historyManager.getHistory().add(foundEpic);
    }

    @Override
    public void updateTask(int id, TaskUneversal taskUneversal) {
        for (Task tk : tasks.values()) {
            if (id == taskUneversal.getId()) {
                taskUneversal.setName(taskUneversal);
            }
        }
        historyManager.getHistory().add(taskUneversal);
    }

    @Override
    public void updateSubTask(int id, SubTask subTask) {
        for (SubTask search : subtask.values()) {
            if (search.getId() == id) {
                search.setName(subTask);
            }
        }
        historyManager.getHistory().add(subTask);
    }

    @Override
    public void getID(int id) {
        Task sd = new Task();
        for (Integer search : tasks.keySet()) {
            if (id == search) {
                System.out.println(search);
            }
        }
    }
}


