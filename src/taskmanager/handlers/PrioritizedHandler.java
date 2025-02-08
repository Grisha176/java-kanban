package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;

import taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

public class PrioritizedHandler extends BaseHttpHandler {
    @Override
    public void processGetTasks(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                List<Task> tasks = manager.getPrioritizedTasks();
                if (!tasks.isEmpty()) {
                    sendText(exchange, tasks.toString());
                    return;
                }
                sendNotFound(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendHasException(exchange);
        }
    }
}
