package TaskManager;

public class Epic extends Task {

    private final int id;

    public Epic(String name, String desription) {

        this.name = name;
        this.description = desription;
        this.id = Task.count;
        Task.count++;
        this.progress = Progress.NEW;
    }
    public int getId() {
        return id;
    }

    public void setName(Epic epic) {
        this.name = epic.name;
        this.description = epic.description;
    }
    public void setProgress(Progress progress){
        this.progress = progress;
    }
}
