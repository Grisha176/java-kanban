package taskmanager.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskUneversal extends Task {
    //private final int id;

    public TaskUneversal(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = Task.count;
        Task.count += 1;
        this.progress = Progress.NEW;
        this.duration = Duration.ofMinutes(duration.toMinutes());
        this.startTime = startTime;
    }



    public void setName(TaskUneversal taskUneversal) {
        this.name = taskUneversal.name;
        this.description = taskUneversal.description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                ", duration=" + duration.toMinutes() +
                ", startTime=" + startTime +
                '}';
    }
    public String toStrings() {
        return String.format("%d,%s,%s,%s,%s,%s,%s",
                id,
                "TASK",
                name,
                progress,
                description,
                duration.toMinutes(),
                startTime);
    }
}
