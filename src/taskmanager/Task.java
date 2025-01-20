package taskmanager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    protected String name;
    protected int id;
    protected String description;
    protected Progress progress;
    public static Integer count = 1;
    protected Duration duration;
    protected LocalDateTime startTime;

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(progress, task.progress);

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Progress getStatus() {
        return this.progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }


    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = name.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        hash = hash * 31;
        if (progress != null) {
            hash = hash + progress.hashCode();
        }


        return hash;
    }

    @Override
    public String toString() {
        return "Задача {" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", progress='" + progress + '}';
    }

    public int getId() {
        return id;
    }
}