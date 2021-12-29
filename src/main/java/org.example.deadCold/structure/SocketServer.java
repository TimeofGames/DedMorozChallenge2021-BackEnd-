package org.example.deadCold.structure;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private Socket clientSocket;
    private ServerSocket server;

    public SocketServer() throws IOException {
        start();
    }

    private void start() throws IOException {
        server = new ServerSocket(5050, 1);
        System.out.println("Сервер запущен");
    }

    public void sendData(JSONObject data) throws IOException {
        clientSocket = server.accept();
        System.out.println(clientSocket.getInetAddress());
        JSONObject objectToOut = data;
        OutputStreamWriter stream = new OutputStreamWriter(clientSocket.getOutputStream());
        stream.write(String.valueOf(objectToOut));
        stream.flush();
        stream.close();
    }

    public void finish() throws IOException {
        server.close();
        System.out.println("Сервер закрыт");
    }

}
