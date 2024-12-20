package taskmanager;

import java.util.ArrayList;

public class Epic extends Task {

    protected static ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {

        this.name = name;
        this.description = description;
        this.id = Task.count;
        Task.count++;
        this.progress = Progress.NEW;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    void setName(Epic epic) {
        this.name = epic.name;
        this.description = epic.description;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public void cleansubtaskIds() {
        subtaskIds.clear();
    }

    public void addSubtaskIds(int id) {
        if (id != this.id && !subtaskIds.contains(id)) {
            subtaskIds.add(id);
        } else {
            System.out.println("Нельзя добавить эпик в вмиде подзадачи");
        }

    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                '}';
    }

    public void removeSubTaskId(int id) {
        if (subtaskIds.contains(id)) {
            subtaskIds.remove((Integer) id);
        }
    }
}

