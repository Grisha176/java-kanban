package taskmanager.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SubTask extends Task {

    private int idEpic;


    public SubTask(String name, String description, Duration duration, LocalDateTime startTime, Integer idEpic) {
        this.name = name;
        this.description = description;
        this.progress = Progress.NEW;
        this.id = Task.count;
        Task.count += 1;
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
                "idEpic=" + idEpic +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                ", durationInMinutes= " + duration.toMinutes() +
                ", startTime=" + startTime.format(DateTimeFormatter.ofPattern("  yyyy-MM-ddHH:mm")) +
                '}';
    }
    public String toStrings() {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%d",
                id,
                "SUBTASK",
                name,
                progress,
                description,
                duration.toMinutes(),
                startTime,
                idEpic);
    }
}
