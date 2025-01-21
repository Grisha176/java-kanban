package taskmanager.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {

    int idEpic;


    public SubTask(String name, String description, Duration duration, LocalDateTime startTime, Integer idEpic) {
        this.name = name;
        this.description = description;
        this.progress = Progress.NEW;
        this.id = Task.count;
        Task.count++;
        this.startTime = startTime;
        this.duration = duration;
        this.idEpic = idEpic;

    }

    public Duration getDuration() {
        return duration;
    }

    public Integer setEpicId() {
        idEpic = 0;
        return idEpic;
    }

    public Integer getEpicId() {
        return idEpic;
    }

    public int getId() {
        return id;
    }

    Progress getStatus(SubTask subTask) {
        return subTask.progress;
    }

    public void setProgress(Progress status) {
        this.progress = status;
    }

    int getIdEpic() {
        return idEpic;
    }


    public void setName(SubTask subTask) {
        this.name = subTask.name;
        this.description = subTask.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return idEpic == subTask.idEpic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idEpic);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                '}';
    }
}
