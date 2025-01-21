package taskmanager.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskUneversal extends Task {
    private int id;

    public TaskUneversal(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = Task.count;
        Task.count++;
        this.progress = Progress.NEW;
        this.duration = duration;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setName(TaskUneversal taskUneversal) {
        this.name = taskUneversal.name;
        this.description = taskUneversal.description;
    }
}
