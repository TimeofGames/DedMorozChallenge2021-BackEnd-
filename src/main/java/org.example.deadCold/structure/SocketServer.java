package org.example.deadCold.structure;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static Socket clientSocket;
    private ServerSocket server;

    public SocketServer() throws IOException {
        start();
    }

    private void start() throws IOException {
        server = new ServerSocket(5050, 1);
        System.out.println("Сервер запущен");
        clientSocket = server.accept();
        System.out.println("Подключён: "+ clientSocket.getInetAddress());
    }

    public void sendData(String data) throws IOException {
        BufferedWriter stringOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        stringOut.write(data);
        stringOut.flush();
        stringOut.close();
        System.out.println("Выведено: " + data);
    }

    public void finish() throws IOException {
        server.close();
        System.out.println("Сервер закрыт");
    }

}
