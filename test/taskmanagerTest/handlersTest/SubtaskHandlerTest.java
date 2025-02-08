package taskmanagerTest.handlersTest;

import com.google.gson.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import taskmanager.handlers.HttpTaskServer;

import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Epic;
import taskmanager.model.SubTask;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubtaskHandlerTest {

    HttpTaskServer taskServer = new HttpTaskServer();
    InMemoryTaskManager manager = taskServer.getManager();
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    public SubtaskHandlerTest() throws IOException {
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
    public void testAddSubtask() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Epic epic = new Epic("epic", "description");
        manager.addEpic(epic);
        SubTask subtask = new SubTask("Test 2", "Testing task 2",
                Duration.ofMinutes(5), now, epic.getId());

        String startDate = now.toLocalDate().toString();
        String startTime = now.toLocalTime().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", subtask.getName());
        jsonObject.addProperty("description", subtask.getDescription());
        jsonObject.addProperty("duration", subtask.getDuration().toMinutes());
        jsonObject.addProperty("startDate", startDate);
        jsonObject.addProperty("startTime", startTime);
        jsonObject.addProperty("epicId", epic.getId());
        String subtaskJson = gson.toJson(jsonObject);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> subTasksFromManager = manager.getSubtasks();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(subtask.getName(), subTasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    class DurationSerializer implements JsonSerializer<Duration> {
        @Override
        public JsonElement serialize(Duration duration, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(duration.getSeconds());
        }
    }

    class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    @Test
    public void subtaskDelete() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Epic epic = new Epic("epic", "description");
        manager.addEpic(epic);
        SubTask subtask = new SubTask("Test 2", "Testing task 2",
                Duration.ofMinutes(5), now, epic.getId());
        int id = subtask.getId();
        manager.addSubTask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> subTasks = new ArrayList<>();
        assertEquals(subTasks, manager.getTasks());

    }

    @Test
    public void getSubTaskById() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Epic epic = new Epic("epic", "description");
        manager.addEpic(epic);
        SubTask subtask = new SubTask("Test 2", "Testing task 2",
                Duration.ofMinutes(5), now, epic.getId());
        manager.addSubTask(subtask);
        int id = subtask.getId();


        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(subtask.toString(), (response.body().toString()));
    }

    @Test
    public void getAllTasks() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Epic epic = new Epic("epic", "description");
        manager.addEpic(epic);

        SubTask subtask = new SubTask("Test 1", "Testing task 1",
                Duration.ofMinutes(5), now, epic.getId());
        SubTask subtask2 = new SubTask("Test 2", "Testing task 2",
                Duration.ofMinutes(1), now.plusHours(1), epic.getId());

        manager.addSubTask(subtask2);
        manager.addSubTask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(manager.getSubtasks().toString(), response.body().toString());
    }
}
