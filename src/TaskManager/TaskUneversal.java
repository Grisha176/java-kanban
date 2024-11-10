package TaskManager;

public class TaskUneversal extends Task {
        private int id;
        public TaskUneversal(String name, String description) {
            this.name = name;
            this.description = description;
            this.id = Task.count;
            Task.count++;
            this.progress = Progress.NEW;
        }

        public void setProgress(Progress progress){
            this.progress = progress;
        }

        public int getId() {
            return id;
        }

        public void setName(TaskUneversal taskUneversal) {
            this.name = taskUneversal.name;
            this.description = taskUneversal.description;
        }
    }

