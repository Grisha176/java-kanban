package taskManager;

public class SubTask extends Task {

    int idEpic;

    public SubTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.progress = Progress.NEW;
        this.id = Task.count;
        Task.count++;

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


    void setName(SubTask subTask) {
        this.name = subTask.name;
        this.description = subTask.description;
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
