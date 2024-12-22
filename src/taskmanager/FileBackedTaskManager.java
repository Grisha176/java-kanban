package taskmanager;

import java.io.*;
import java.util.Scanner;

public class FileBackedTaskManager extends InMemoryTaskManager {
    static final String FAILNAME = "tasks,cvs";

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
        } catch (FileNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }
        return manager;
    }

    private void save() {
        File file = new File(FAILNAME);
        InMemoryTaskManager inMemoryTaskManager = FileBackedTaskManager.loadFile(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!file.exists()) {
                writer.append("id,type,name,status,description,epic").append("\n");
            }
            String line = toStrings(allTasks.get(allTasks.size() - 1));
            writer.append(line).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
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




