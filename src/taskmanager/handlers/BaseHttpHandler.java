package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.manager.FileBackedTaskManager;
import taskmanager.manager.TaskManager1;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {
    static final TaskManager1 manager = new FileBackedTaskManager();
    FileBackedTaskManager fileManager = (FileBackedTaskManager) manager;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case "GET_BY_ID":
                processGet(exchange);
                break;
            case "POST":
                processPost(exchange);
                break;
            case "DELETE":
                processDelete(exchange);
                break;
            case "GET_ALL_TASKS":
                processGetTasks(exchange);
            default:
                writeToUser(exchange, "Данный метод не предусмотрен");
        }
    }


    public String getEndpoint(String requestPath, String method) {
        String[] mass = requestPath.split("/");
        String getMethod = "incorrect info";

        if (mass.length == 3 && method.equals("GET")) {
            getMethod = "GET_BY_ID";
        } else if (mass.length == 3 && method.equals("DELETE")) {
            getMethod = "DELETE";
        } else if (mass.length == 2 && method.equals("GET")) {
            getMethod = "GET_ALL_TASKS";
        } else if (mass.length == 2 && method.equals("POST")) {
            getMethod = "POST";
        }
        return getMethod;
    }


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
        return fileManager;
    }


    private void writeToUser(HttpExchange exchange, String str) throws IOException {
        exchange.sendResponseHeaders(406, 0);
        OutputStream os = exchange.getResponseBody();
        os.write(str.getBytes());
        os.close();
    }

    protected void processGet(HttpExchange exchange) throws IOException {
        sendHasException(exchange);
    }

    protected void processGetTasks(HttpExchange exchange) throws IOException {
        sendHasException(exchange);

    }

    protected void processDelete(HttpExchange exchange) throws IOException {
        sendHasException(exchange);
    }

    protected void processPost(HttpExchange exchange) throws IOException {
        sendHasException(exchange);
    }
}