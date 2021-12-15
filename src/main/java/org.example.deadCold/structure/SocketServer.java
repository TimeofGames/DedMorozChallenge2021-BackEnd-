package org.example.deadCold.structure;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
    private Socket clientSocket;
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

    public void sendData(Graph graph) throws IOException {
        JSONObject objectToOut = new JSONObject();
        ArrayList<ArrayList<double[]>> matrix = graph.getMatrix();
        ArrayList<ArrayList<Double>> matrixToOut = new ArrayList<>();

        for (int i = 0;i<matrix.size();i++){
            ArrayList<Double> localArray = new ArrayList<>();
            for (int j = 0;j<matrix.size();j++){
                localArray.add(matrix.get(i).get(j)[1]);
            }
            matrixToOut.add(localArray);
        }
        objectToOut.put("Matrix", matrixToOut);
        objectToOut.put("FirstShortestWay",graph.getFirstShortestWay());
        objectToOut.put("SecondShortestWay",graph.getSecondShortestWay());
        PrintStream stream = new PrintStream(clientSocket.getOutputStream());
        System.out.println(objectToOut);
        stream.println(objectToOut);
        stream.flush();
        stream.close();
    }

    public void finish() throws IOException {
        server.close();
        System.out.println("Сервер закрыт");
    }

}
