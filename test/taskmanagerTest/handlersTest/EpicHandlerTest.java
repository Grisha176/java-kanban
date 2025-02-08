package taskmanagerTest.handlersTest;

import com.google.gson.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.handlers.HttpTaskServer;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Epic;
import taskmanager.model.SubTask;
import taskmanager.model.Task;


import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EpicHandlerTest {

    HttpTaskServer taskServer = new HttpTaskServer();
    InMemoryTaskManager manager = taskServer.getManager();
    Gson gson = new Gson();

    public EpicHandlerTest() throws IOException {
    }


    @BeforeEach
    public void setUp() {
        manager.deleteAllTask();
        manager.deleteAllSubTask();
        manager.deleteAllEpic();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }


    @Test
    public void testAddEpic() throws IOException, InterruptedException {

        Epic epic = new Epic("epic", "testing");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", epic.getName());
        jsonObject.addProperty("description", epic.getDescription());
        String epicJson = gson.toJson(jsonObject);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> epicsFromManager = manager.getEpics();
        assertNotNull(epicsFromManager, "Задачи не возвращаются");
        assertEquals(epic.getName(), epicsFromManager.get(0).getName(), "Некорректное имя задачи");
    }


    @Test
    public void taskDelete() throws IOException, InterruptedException {
        Epic epic = new Epic("epic", "testing");
        int id = epic.getId();
        manager.addEpic(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> epics = new ArrayList<>();
        assertEquals(epics, manager.getEpics());

    }

    @Test
    public void getEpicById() throws IOException, InterruptedException {
        Epic epic = new Epic("epic", "testing");
        int id = epic.getId();
        manager.addEpic(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(epic.toString(), (response.body().toString()));
    }

    @Test
    public void getAllEpics() throws IOException, InterruptedException {
        Epic epic = new Epic("epic", "testing");
        Epic epic2 = new Epic("epic2", "testing2");

        manager.addEpic(epic);
        manager.addEpic(epic2);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(manager.getEpics().toString(), response.body().toString());
    }

    @Test
    public void getEpicsSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("epic", "testing");
        SubTask subTask = new SubTask("subtask", "test", Duration.ofMinutes(11), LocalDateTime.now().plusHours(1), epic.getId());
        manager.addEpic(epic);
        manager.addSubTask(subTask);

        int id = epic.getId();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/" + id + "/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body().substring(1, response.body().length() - 1);

        assertEquals(subTask.toString(), body);
    }

}
