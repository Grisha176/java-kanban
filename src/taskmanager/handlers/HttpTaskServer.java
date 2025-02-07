package taskmanager.handlers;

import com.sun.net.httpserver.HttpServer;


import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer extends BaseHttpHandler {

    static final int PORT = 8080;
    private static HttpServer httpServer;


    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/epics", new EpicHandler());// связываем путь и обработчик
        httpServer.createContext("/subtasks", new SubTaskHandler());
        httpServer.createContext("/history", new HistoryHandler(manager.getHistoryManager()));
        httpServer.createContext("/prioritized", new PrioritizedHandler());
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        File file = new File("tasks.csv");
        manager.loadFile(file);
        httpTaskServer.start();// запускаем сервер
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }
}