package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.model.SubTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.*;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class SubTaskHandler extends BaseHttpHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case "GET_BY_ID":
                handleGetSubTaskById(exchange);
                break;
            case "GET_ALL_SUBTASK":
                handleGetAllSubTasks(exchange);
                break;
            case "DELETE":
                handleDeleteSubTask(exchange);
                break;
            case "POST":
                handlePOST_SubTask(exchange);
                break;
            default:

                sendHasException(exchange);
        }
    }


    private void handleGetSubTaskById(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] mass = path.split("/");
        try {
            int id = Integer.parseInt(mass[2]);
            SubTask subTask = manager.getSubTask(id);
            if (subTask != null) {
                sendText(exchange, subTask.toString());
                return;
            }
            sendNotFound(exchange);
        } catch (NumberFormatException e) {
            sendHasException(exchange);
        }
    }

    private void handleGetAllSubTasks(HttpExchange exchange) throws IOException {

        try {
            List<SubTask> subTasks = manager.getSubtasks();
            if (!subTasks.isEmpty()) {
                String subTasksString = subTasks.toString();
                sendText(exchange, subTasksString);
                return;
            }
            sendNotFound(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            sendHasException(exchange);
        }
    }

    private void handleDeleteSubTask(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] mass = path.split("/");
        try {
            int id = Integer.parseInt(mass[2]);
            manager.deleteSubTask(id);
            sendText(exchange, "Удаление прошло успешно!");
        } catch (NumberFormatException e) {
            sendHasException(exchange);

        }
    }

    private void handlePOST_SubTask(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();

            String startTime = jsonObject.get("startTime").getAsString();
            String startDate = jsonObject.get("startDate").getAsString();

            LocalTime localTime = LocalTime.parse(startTime);
            LocalDate localDate = LocalDate.parse(startDate);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            String formatDateTime = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            LocalDateTime parse = LocalDateTime.parse(formatDateTime);

            int durationInMinutes = jsonObject.get("duration").getAsInt();
            Duration duration = Duration.ofMinutes(durationInMinutes);
            int epicID = jsonObject.get("epicId").getAsInt();
            manager.addSubTask(new SubTask(name, description, duration, parse, epicID));
            sendText(exchange, "Добавление прошло успешно");
            System.out.println(manager.getSubtasks());
        } catch (IllegalArgumentException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(406, 0);
            OutputStream os = exchange.getResponseBody();
            String response = "Сначало следует добавть эпик,а потом subtask";
            os.write(response.getBytes());
            os.close();
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
            getMethod = "GET_ALL_SUBTASK";
        } else if (mass.length == 2 && method.equals("POST")) {
            getMethod = "POST";
        }
        return getMethod;
    }

}
