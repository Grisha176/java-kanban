package TaskManager;
import java.util.Objects;

public class Task {

    String name;
    int id;
    String description;
    Progress progress;
    static Integer count  = 1;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(progress, task.progress);

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
                ", progress='"+progress +'}';
    }

    public int getId() {
        return id;
    }
}