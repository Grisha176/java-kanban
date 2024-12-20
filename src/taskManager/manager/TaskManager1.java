package taskManager.manager;

import taskManager.Epic;
import taskManager.SubTask;
import taskManager.Task;
import taskManager.TaskUneversal;

import java.util.ArrayList;

public interface TaskManager1 {

    HistoryManager getHistory();

    ArrayList<Task> getTasks();

    Task getTask(int id);

    Epic getEpic(int id);

    SubTask getSubTask(int id);

    ArrayList<SubTask> getSubtasks();


    ArrayList<Epic> getEpics();


    int addTask(Task task);


    int addEpic(Epic epic);

    Integer addSubTask(SubTask subTasks);


    void deleteSubTask(int id);

    void deleteEpics(int id);


    void deleteTask(int id);


    void deleteAllTask();


    void deleteAllEpic();


    void deleteAllSubTask();


    void updateEpicStatus(int id, Epic epic);

    void updateTask(int id, TaskUneversal taskUneversal);


    void updateSubTask(int id, SubTask subTask);


    void getID(int id);

}