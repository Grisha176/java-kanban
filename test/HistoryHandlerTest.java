
import org.junit.jupiter.api.Test;

import taskmanager.handlers.HttpTaskServer;
import taskmanager.manager.HistoryManager;

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

public class HistoryHandlerTest {

    HttpTaskServer taskServer = new HttpTaskServer();
    InMemoryTaskManager manager = taskServer.getManager();
    HistoryManager manager2 = manager.getHistoryManager();

    public HistoryHandlerTest() throws IOException {
    }

    @Test
    public void getHistory() throws IOException, InterruptedException {

        manager.deleteAllTask();
        manager.deleteAllSubTask();
        manager.deleteAllEpic();
        taskServer.start();

        LocalDateTime now = LocalDateTime.now();
        Task task = new TaskUneversal("Test", "Testing task",
                Duration.ofMinutes(5), now);

        Task task2 = new TaskUneversal("Test 2", "Testing task 2",
                Duration.ofMinutes(50), now.plusHours(1));
        manager.getTask(task.getId());
        manager.getTask(task2.getId());

        URI uri = URI.create("http://localhost:8080/history");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(manager.getTasks().toString(), manager2.getHistory().toString());
        taskServer.stop();

    }
}
