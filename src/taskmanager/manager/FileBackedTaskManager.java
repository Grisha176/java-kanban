package taskmanager.manager;

import taskmanager.exceptions.ManagerSaveException;
import taskmanager.model.Epic;
import taskmanager.model.SubTask;
import taskmanager.model.Task;
import taskmanager.model.TaskUneversal;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String FAILNAME = "tasks.csv";

    public void loadFile(File file) {

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] mass = line.split(",");
                if (mass.length == 7 || mass.length == 8) {
                    int id = Integer.parseInt(mass[0]);
                    String type = mass[1];
                    String name = mass[2];
                    String status = mass[3];
                    String description = mass[4];

                    switch (type) {
                        case "TASK":
                            Duration duration = Duration.parse(mass[5]);
                            LocalDateTime ldt = LocalDateTime.parse(mass[6]);
                            super.addTask(new TaskUneversal(name, description, duration, ldt));
                            break;
                        case "EPIC":
                            super.addEpic(new Epic(name, description));
                            break;
                        case "SUBTASK":
                            int epicId = Integer.parseInt(mass[8]);
                            Duration duration1 = Duration.parse(mass[5]);
                            LocalDateTime ldt2 = LocalDateTime.parse(mass[6]);
                            Epic epic1 = this.getEpic(epicId);
                            if (epic1 != null) {
                                super.addSubTask(new SubTask(name, description, duration1, ldt2, epicId));
                            } else {
                                System.out.println("Эпик с ID " + epicId + " не найден.");
                            }

                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Пересечение по времени");
        }

    }

    public void save() {
        File file = new File(FAILNAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка: " + e.getMessage());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!allTasks.isEmpty()) {
                String line = toStrings(allTasks.getLast());
                writer.append(line).append('\n');
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка: " + e.getMessage());
        }

    }

    private String toStrings(Task task) {
        return task.toStrings();
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




