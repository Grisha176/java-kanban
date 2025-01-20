package taskmanager;

import taskmanager.manager.HistoryManager;
import taskmanager.manager.Managers;
import taskmanager.manager.TaskManager1;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager1 {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subtask = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    public final List<Task> allTasks = new ArrayList<>();

    public List<Task> getPrioritizedTasks() {
        List<Task> sortedByTime = new ArrayList<>(allTasks);
        sortedByTime.sort(Comparator.comparing(Task::getStartTime));
        return sortedByTime;

    }

    public boolean checkTimeOverlaps(Task existing, Task newTask) {
        return !(existing.getStartTime().isBefore(newTask.getStartTime()) && existing.getEndTime().isBefore(newTask.startTime));
    }


    public List<LocalDateTime> getSubTaskSortByTime() {
        List<LocalDateTime> lcd = new ArrayList<>();
        subtask.values().stream()
                .map(SubTask::getStartTime)
                .forEach(lcd::add);
        if (!lcd.isEmpty()) {
            Collections.sort(lcd);
            return lcd;
        }
        return List.of(LocalDateTime.now());


    }


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
        boolean hasOverlap = tasks.values().stream()
                .anyMatch(existing -> checkTimeOverlaps(existing, task));
        if (hasOverlap) {
            throw new IllegalArgumentException("Задача пересекается с другой задачей в списке");
        }
        task.id = Task.count++;
        tasks.put(task.id, task);
        allTasks.add(task);
        return task.id;


    }

    @Override
    public int addEpic(Epic epic) {

        boolean hasOverlap = epics.values().stream()
                .anyMatch(existing -> checkTimeOverlaps(existing, epic));
        if (hasOverlap) {
            throw new IllegalArgumentException("Задача пересекается с другой задачей в списке");
        }

        epic.id = Task.count++;
        epics.put(epic.id, epic);
        allTasks.add(epic);
        return epic.id;

    }

    @Override
    public Integer addSubTask(SubTask subTasks) {

        boolean hasOverlap = subtask.values().stream()
                .anyMatch(existing -> checkTimeOverlaps(existing, subTasks));
        if (hasOverlap) {
            throw new IllegalArgumentException("Задача пересекается с другой задачей в списке");
        }

        subTasks.id = Task.count++;
        subtask.put(subTasks.id, subTasks);
        Epic ep = epics.get(subTasks.idEpic);
        System.out.println(ep);
        ep.subtaskIds.add(subTasks.id);
        updateEpicStatus(ep.getId(), ep);
        allTasks.add(subTasks);
        return subTasks.id;
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask subTask1 = subtask.get(id);
        subTask1.setEpicId();
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
            epic.getSubtaskIds().stream()
                    .map(subTaskId -> subtask.remove(subTaskId))
                    .forEach(removed -> {
                    });


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

        epics.values().stream()
                .peek(epic -> epic.cleansubtaskIds())
                .forEach(epic -> updateEpicStatus(epic.getId(), epic));

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


        tasks.values().stream()
                .filter(task -> id == task.getId())
                .forEach(task -> taskUneversal.setName(taskUneversal));

        historyManager.getHistory().add(taskUneversal);
    }

    @Override
    public void updateSubTask(int id, SubTask subTask) {
        subtask.values()
                .stream().filter(subTasks -> subTasks.getId() == id)
                .forEach(subTasks -> subTasks.setName(subTask));

        historyManager.getHistory().add(subTask);
    }

    @Override
    public void getID(int id) {
        Task sd = new Task();

        tasks.keySet().stream()
                .filter(search -> id == search)
                .forEach(System.out::println);
    }
}


