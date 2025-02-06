import org.junit.jupiter.api.Test;
import taskmanager.handlers.HttpTaskServer;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.model.Task;
import taskmanager.model.TaskUneversal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrioritizedHandlerTest {

    HttpTaskServer taskServer = new HttpTaskServer();
    InMemoryTaskManager manager = taskServer.getManager();

    public PrioritizedHandlerTest() throws IOException {
    }

    @Test
    public void getPrioritizedTasks() throws IOException, InterruptedException {

        manager.deleteAllTask();
        manager.deleteAllSubTask();
        manager.deleteAllEpic();
        taskServer.start();

        LocalDateTime now = LocalDateTime.now();
        Task task = new TaskUneversal("Test", "Testing task",
                Duration.ofMinutes(5), now);

        Task task2 = new TaskUneversal("Test 2", "Testing task 2",
                Duration.ofMinutes(50), now.plusHours(1));
        manager.addTask(task);
        manager.addTask(task2);

        URI uri = URI.create("http://localhost:8080/prioritized");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(manager.getPrioritizedTasks().toString(), response.body().toString());
        taskServer.stop();

    }
}
