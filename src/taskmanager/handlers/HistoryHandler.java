package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.manager.HistoryManager;
import taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    HistoryManager manager;

    HistoryHandler(HistoryManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                List<Task> tasks = manager.getHistory();
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
