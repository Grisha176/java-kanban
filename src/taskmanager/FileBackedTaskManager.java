package taskmanager;

import taskmanager.exceptions.ManagerSaveException;
import taskmanager.manager.HistoryManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String FAILNAME = "tasks,cvs";

    public static InMemoryTaskManager loadFile(File file) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] mass = line.split(",");
                if (mass.length == 6) {
                    int id = Integer.parseInt(mass[0]);
                    String type = mass[1];
                    String name = mass[2];
                    String descr = mass[4];
                    switch (type) {
                        case "TASK":
                            manager.addTask(new TaskUneversal(name, descr));
                            break;
                        case "EPIC":
                            manager.addEpic(new Epic(name, descr));
                            break;
                        case "SUBTASK":
                            manager.addSubTask(new SubTask(name, descr));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка: " + e.getMessage());
        }
        return manager;
    }

    private void save() {
        File file = new File(FAILNAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка: " + e.getMessage());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!file.exists()) {
                writer.append("id,type,name,status,description,epic").append("\n");
            }
            String line = toStrings(allTasks.get(allTasks.size() - 1));
            writer.append(line).append('\n');
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка: " + e.getMessage());
        }

    }

    private String toStrings(Task task) {
        return task.toString();
    }

    @Override
    public Integer addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
        return subTask.getId();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void deleteEpics(int id) {
        super.deleteEpics(id);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        save();
    }

    @Override
    public void updateEpicStatus(int id, Epic epic) {
        super.updateEpicStatus(id, epic);
        save();
    }

    @Override
    public void updateTask(int id, TaskUneversal taskUneversal) {
        super.updateTask(id, taskUneversal);
        save();
    }

    @Override
    public void updateSubTask(int id, SubTask subTask) {
        super.updateSubTask(id, subTask);
        save();
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public Epic getEpic(int id) {
        return super.getEpic(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        return super.getSubTask(id);
    }

    @Override
    public HistoryManager getHistory() {
        return super.getHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public ArrayList<SubTask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public int addTask(Task task) {
        super.addTask(task);
        save();
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic.getId();
    }


}




