package TaskManager;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    public HashMap<Epic, ArrayList<SubTask>> task = new HashMap<>();

    public ArrayList<Task> watchid = new ArrayList<>();
    public ArrayList<TaskUneversal> taskUneversal = new ArrayList<>();


    public void setEpic(Epic epic) {
        if (task.containsKey(epic)) {
            System.out.println("задача уже есть в списке");
        } else {
            task.put(epic, new ArrayList<>());
            watchid.add(epic);

        }

    }

    public void setSubTask(Epic epic, SubTask subTask) {


        if (task.containsKey(epic)) {
            ArrayList<SubTask> subTasks = task.get(epic);
            subTasks.add(subTask);
            task.put(epic, subTasks);
            watchid.add(subTask);


        } else {
            System.out.println("надо сначало добавить задачу,а потом подзадачу");
        }

    }

    public void setTaskUneversal(TaskUneversal taskUneversals) {
        if (!taskUneversal.contains(taskUneversals)) {
            watchid.add(taskUneversals);
            taskUneversal.add(taskUneversals);
        } else {
            System.out.println("задача в списке");
        }

    }


    public void deleteSubTask(SubTask subTask) {

        for (ArrayList<SubTask> search : task.values()){
            for (int i = 0; i < search.size(); i++) {
                if (search.get(i).getId() == subTask.getId()) {
                    for (int j = 0; j < watchid.size(); j++) {
                        if(watchid.get(j).getId() == subTask.getId()){
                            watchid.remove(subTask);
                        }
                    }
                    search.remove(subTask);
                }
            }
        }

    }
    public void deleteEpic(Epic epic) {

        for(Epic ep : task.keySet()){
            if (ep.getId() == epic.getId()) {

                for (int j = 0; j < watchid.size(); j++) {
                    if(watchid.get(j).getId() == epic.getId()){
                        watchid.remove(epic);
                    }
                }

                task.remove(ep);
            }
        }
    }

    public void deleteTaskUneversal(TaskUneversal taskUneversals){
        for (int i = 0; i < taskUneversal.size(); i++) {
            if (taskUneversal.get(i).getId() == taskUneversals.getId()){
                for (int j = 0; j < watchid.size(); j++) {
                    if(watchid.get(j).getId() == taskUneversals.getId()){
                        watchid.remove(taskUneversals);
                    }
                }
                taskUneversal.remove(taskUneversals);
            }

        }
    }




    public void deleteAllTaskUneversal() {
        for (TaskUneversal task : taskUneversal) {
            for (int j = 0; j < watchid.size(); j++) {
                if(watchid.get(j).getId() == task.getId()){
                    watchid.remove(task);
                }
            }
        }
        taskUneversal.clear();
    }

    public void deleteAllEpic() {
        for (Epic epic : task.keySet()){
            for (int j = 0; j < watchid.size(); j++) {
                if(watchid.get(j).getId() == epic.getId()){
                    watchid.remove(epic);
                }
            }
        }
        task.clear();
    }

    public void deleteAllSubTask() {
        for (ArrayList<SubTask> subTasks : task.values()) {
            for (SubTask subTask : subTasks){
                for (int j = 0; j < watchid.size(); j++) {
                    if(watchid.get(j).getId() == subTask.getId()){
                        watchid.remove(subTask);
                    }
                }
            }
            subTasks.clear();
        }
    }



    public void updateEpic(int id, Epic epic) {
        for (Epic ep : task.keySet()) {
            if (id == ep.getId()) {
                ep.setName(epic);
            }
        }
    }
    public void updateTaskUneversal(int id,TaskUneversal taskUneversals) {
        for (TaskUneversal task : taskUneversal) {
            if (id == task.getId()) {
                task.setName(taskUneversals);
            }
        }
    }


    public void updateSubTask(int id, SubTask subTask) {
        for (ArrayList<SubTask> search : task.values()) {
            for (SubTask sb : search) {
                if (sb.getId() == id) {
                    sb.setName(subTask);
                }
            }
        }
    }



    public void getAllEpic() {
        for (Epic search : task.keySet()) {
            System.out.println(search);

        }
    }
    public void getAllSubTask() {
        for (Epic search : task.keySet()) {
            ArrayList<SubTask> subTask = task.get(search);
            System.out.println("Epic "+ search.name);
            for (SubTask sb : subTask) {
                System.out.println(sb);
            }

        }
    }


    public void getAllTaskUneversal() {
        for (TaskUneversal task : taskUneversal) {
            System.out.println(task);
        }
    }


    public void getID(int id) {
        for (Task search : watchid) {
            if (id == search.getId()) {
                System.out.println(search);
            }
        }
    }


}
