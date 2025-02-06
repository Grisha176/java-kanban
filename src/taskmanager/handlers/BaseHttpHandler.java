package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;
import taskmanager.manager.FileBackedTaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    static final FileBackedTaskManager manager = new FileBackedTaskManager();

    protected void sendText(HttpExchange h, String str) throws IOException {
        byte[] resp = str.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h) throws IOException {
        h.sendResponseHeaders(404, 0);
        OutputStream os = h.getResponseBody();
        String response = "По вашему запросу ничего не найдено";
        os.write(response.getBytes());
        os.close();
    }

    protected void sendHasException(HttpExchange h) throws IOException {
        h.sendResponseHeaders(406, 0);
        OutputStream os = h.getResponseBody();
        os.write("Некорректные входные данные.Проверьте запрос и повторите попытку".getBytes());
        os.close();
    }

    protected void sendHasInteractions(HttpExchange h) throws IOException {
        h.sendResponseHeaders(406, 0);
        OutputStream os = h.getResponseBody();
        os.write("Задача пересекается с другой по времени".getBytes());
        os.close();
    }

    public FileBackedTaskManager getManager() {
        return manager;
    }
}