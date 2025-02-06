package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
            sendHasException(exchange);
        }
    }
}
