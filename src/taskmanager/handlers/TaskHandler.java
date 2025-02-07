package taskmanager.handlers;

import com.sun.net.httpserver.HttpExchange;
import taskmanager.model.Task;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.*;
import taskmanager.model.TaskUneversal;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class TaskHandler extends BaseHttpHandler {

    @Override
    protected void processGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] mass = path.split("/");
        try {
            int id = Integer.parseInt(mass[2]);
            Task task = manager.getTask(id);
            if (task != null) {
                sendText(exchange, task.toString());
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
            List<Task> tasks = manager.getTasks();
            if (!tasks.isEmpty()) {
                String taskstoStr = tasks.toString();
                sendText(exchange, taskstoStr);
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
            manager.deleteTask(id);
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

            String startTime = jsonObject.get("startTime").getAsString();
            String startDate = jsonObject.get("startDate").getAsString();

            LocalTime localTime = LocalTime.parse(startTime);
            LocalDate localDate = LocalDate.parse(startDate);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

            LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T" + startTime);
            int durationInMinutes = jsonObject.get("duration").getAsInt();
            Duration duration = Duration.ofMinutes(durationInMinutes);

            manager.addTask(new TaskUneversal(name, description, duration, startDateTime));
            sendText(exchange, "Добавление прошло успешно");
            System.out.println(manager.getTasks());
        } catch (IllegalArgumentException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            sendHasException(exchange);
        }
    }


}
