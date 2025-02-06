package taskmanager.model;

import taskmanager.manager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Epic extends Task {

    protected static ArrayList<Integer> subtaskIds = new ArrayList<>();
    InMemoryTaskManager manager = new InMemoryTaskManager();

    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = Task.count;
        Task.count += 1;
        this.progress = Progress.NEW;
        this.startTime = getStartTime();
        this.duration = getDuration();
    }


    @Override
    public LocalDateTime getEndTime() {
        LocalDateTime lcd = getStartTime().plus(duration);
        return manager.getSubtasks().stream()
                .map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(lcd);
    }

    @Override
    public LocalDateTime getStartTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime lcd = LocalDateTime.parse(dtf.format(LocalDateTime.now()));
        return manager.getSubtasks().stream()
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(lcd);
    }

    public Duration getDuration() {
        return manager.getSubtasks().stream()
                .map(SubTask::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
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
            System.out.println("Нельзя добавить эпик в виде подзадачи");
        }

    }

    public void removeSubTaskId(int id) {
        if (subtaskIds.contains(id)) {
            subtaskIds.remove((Integer) id);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                ", durationInMinutes= " + duration.toMinutes() +
                ", startTime=" + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) +
                '}';
    }

    public String toStrings() {
        return String.format("%d,%s,%s,%s,%s,%d,%s",
                id,
                "EPIC",
                name,
                progress,
                description,
                duration.toMinutes(),
                startTime);
    }

}

