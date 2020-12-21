package net.lacit;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(Server.class.getResource("/message.json").getPath());

    }

    public  void startServer(String jsonFilePath){
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket clientSocket = serverSocket.accept()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Данные которые пришли
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //данные которые отправляем
            out.println(loadJSONFile(jsonFilePath));
        } catch
        (IOException e) {
            e.printStackTrace();
        }
    }
    //загрузка Json схемы/сообщения
    public JSONObject loadJSONFile(String schemaPath) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new JSONTokener((new FileInputStream(new File(schemaPath)))));
        } catch (FileNotFoundException e) {
            System.out.println("Selected file not found");
        }
        return jsonObject;
    }
}

