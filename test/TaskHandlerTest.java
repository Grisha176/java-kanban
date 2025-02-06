import com.google.gson.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import taskmanager.handlers.HttpTaskServer;

import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Task;
import taskmanager.model.TaskUneversal;

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

public class TaskHandlerTest {

    HttpTaskServer taskServer = new HttpTaskServer();
    InMemoryTaskManager manager = taskServer.getManager();
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();


    public TaskHandlerTest() throws IOException {
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
    public void testAddTask() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Task task = new TaskUneversal("Test 2", "Testing task 2",
                Duration.ofMinutes(5), now);

        String startDate = now.toLocalDate().toString();
        String startTime = now.toLocalTime().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", task.getName());
        jsonObject.addProperty("description", task.getDescription());
        jsonObject.addProperty("duration", task.getDuration().toMinutes());
        jsonObject.addProperty("startDate", startDate);
        jsonObject.addProperty("startTime", startTime);
        String taskJson = gson.toJson(jsonObject);
        System.out.println(taskJson);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
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
    public void taskDelete() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Task task = new TaskUneversal("Test 2", "Testing task 2",
                Duration.ofMinutes(5), now);
        int id = task.getId();
        manager.addTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasks = new ArrayList<>();
        assertEquals(tasks, manager.getTasks());

    }

    @Test
    public void getTaskById() throws IOException, InterruptedException {
        Task task = new TaskUneversal("Test 2", "Testing task 2",
                Duration.ofMinutes(5), LocalDateTime.now());
        int id = task.getId();
        manager.addTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(task.toString(), (response.body().toString()));
    }

    @Test
    public void getAllTasks() throws IOException, InterruptedException {
        Task task = new TaskUneversal("Test 2", "Testing task 2",
                Duration.ofMinutes(5), LocalDateTime.now());
        Task task2 = new TaskUneversal("Test", "Testing", Duration.ofMinutes(20), LocalDateTime.now().plusHours(2));
        manager.addTask(task);
        manager.addTask(task2);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(manager.getTasks().toString(), response.body().toString());
    }
}
