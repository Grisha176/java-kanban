package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;

import taskmanager.manager.HistoryManager;
import taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler {
    HistoryManager manager;

    public HistoryHandler(HistoryManager manager) {
        this.manager = manager;
    }


    @Override
    public void processGetTasks(HttpExchange exchange) throws IOException {
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
            e.printStackTrace();
            sendHasException(exchange);
        }
    }
}
