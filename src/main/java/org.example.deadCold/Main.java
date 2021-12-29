package org.example.deadCold;

import com.opencsv.CSVReader;


import org.example.deadCold.structure.*;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("src\\data\\Cities_v.csv"));
        reader.readNext();
        Graph graph;
        String[] nextLine;
        ArrayList<Node> arrayNodes = new ArrayList<>();
        for (int i = 0; i < 1112; i++) {
            nextLine = reader.readNext();
            arrayNodes.add(i, new Node(nextLine, i));
        }

        int nodeToDuple = getNodeByName("Великий Устюг", arrayNodes);
        graph = new Graph(arrayNodes, new DistanceFounder(), nodeToDuple);
        Hive hive = new Hive(graph, graph.getShortestWay());
        //SocketServer server = new SocketServer();
        for (int i = 0; i < 1000; i++) {
            long clock = System.currentTimeMillis();
            hive.run();
            System.out.println("Shortest distance: " + (graph.getFirstShortestWay()+graph.getSecondShortestWay()) + "km");
            System.out.println("First way: " + graph.getFirstShortestWay() + "km");
            System.out.println("Second way: " + graph.getSecondShortestWay() + "km");
            System.out.println("Time on operation " + (System.currentTimeMillis() - clock) + "ms\n\n");
            FileWriter fileWriter = new FileWriter("data" + i + ".json", false);
            fileWriter.write(PreparationOfInformation.preparationDataToFile(graph).toJSONString());
            fileWriter.flush();
            fileWriter.close();
            //server.sendData(PreparationOfInformation.preparationData(graph));
        }

    }
    private static int getNodeByName(String name, ArrayList<Node> nodes){
        return nodes.stream()
                .filter(item -> item.getName().equals(name))
                .toList().get(0).getGraphIndex();
    };
}

