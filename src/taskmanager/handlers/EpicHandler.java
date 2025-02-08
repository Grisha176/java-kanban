package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;


import taskmanager.model.Epic;
import taskmanager.model.SubTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.*;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.List;


public class EpicHandler extends BaseHttpHandler {

    Gson gson = new GsonBuilder()
                .setPrettyPrinting()  // Включаем красивый вывод
                .create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case "GET_BY_ID":
                processGet(exchange);
                break;
            case "GET_ALL_TASKS":
                processGetTasks(exchange);
                break;
            case "DELETE":
                processDelete(exchange);
                break;
            case "POST":
                processPost(exchange);
                break;
            case "GET_SUBTASK":
                processGetEpicSubtasks(exchange);
            default:
                sendHasException(exchange);
        }
    }

    @Override
    protected void processGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] mass = path.split("/");
        try {
            int id = Integer.parseInt(mass[2]);
            Epic epic = manager.getEpic(id);
            if (epic != null) {
                String epicStr = epic.toString();
                sendText(exchange, epicStr);
                return;
            }
            sendNotFound(exchange);
        } catch (NumberFormatException e) {
            sendHasException(exchange);
        }
    }

    @Override
    protected void processGetTasks(HttpExchange exchange) throws IOException {

        try {
            List<Epic> epics = manager.getEpics();
            if (!epics.isEmpty()) {
                String epicStr = epics.toString();
                String epGson = gson.toJson(epicStr);
                sendText(exchange, epGson);
            }
            sendNotFound(exchange);
        } catch (Exception e) {
            sendHasException(exchange);
        }
    }

    @Override
    protected void processDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] mass = path.split("/");
        try {
            int id = Integer.parseInt(mass[2]);
            manager.deleteEpics(id);
            sendText(exchange, "Удаление прошло успешно!");
        } catch (NumberFormatException e) {
            sendHasException(exchange);
        }
    }

    @Override
    protected void processPost(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();

            Epic epic = new Epic(name, description);
            manager.addEpic(epic);
            String response = "Добвление прошло успешно id эпика: " + epic.getId();
            sendText(exchange, response);
            System.out.println(manager.getEpics());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendHasInteractions(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            sendHasException(exchange);
        }
    }

    private void processGetEpicSubtasks(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] mass = path.split("/");
        try {
            int id = Integer.parseInt(mass[2]);
            List<SubTask> subTask = manager.getEpicsSubtask(id);
            if (!subTask.isEmpty()) {
                sendText(exchange, subTask.toString());
                return;
            }
            sendNotFound(exchange);
        } catch (NumberFormatException e) {
            sendHasException(exchange);
        }
    }

    @Override
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
        } else if (mass.length == 4 && method.equals("GET")) {
            getMethod = "GET_SUBTASK";
        }
        return getMethod;
    }


}
