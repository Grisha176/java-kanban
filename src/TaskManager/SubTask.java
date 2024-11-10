package TaskManager;

public class SubTask extends Task {

    private final int id;
    public SubTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.progress = Progress.NEW;
        this.id = Task.count;
        Task.count++;
    }
    public int getId() {
        return id;
    }

    public void setProgress(Progress status){
        this.progress = status;
    }



    public void setName(SubTask subTask) {
        this.name = subTask.name;
        this.description = subTask.description;
    }


}