package org.example.deadCold;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


import org.example.deadCold.structure.*;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Main {
    public static void main(String[] args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("src\\data\\Cities.csv"));
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
        Hive hive = new Hive(graph);
        for (int i = 0; i < 1000; i++) {
            long clock = System.currentTimeMillis();
            hive.fellowBrothers();
            System.out.println("Shortest distance: " + (graph.getFirstShortestWay()+graph.getSecondShortestWay()) + "km");
            System.out.println("First way: " + graph.getFirstShortestWay() + "km");
            System.out.println("Second way: " + graph.getSecondShortestWay() + "km");
            System.out.println("Time on operation " + (System.currentTimeMillis() - clock) + "ms\n\n");
            FileWriter fileWriter = new FileWriter("data" + i + ".json", false);
            fileWriter.write(PreparationOfInformation.cookData(graph).toJSONString());
            fileWriter.flush();
            fileWriter.close();
        }

    }
    private static int getNodeByName(String name, ArrayList<Node> nodes){
        return nodes.stream()
                .filter(item -> item.getName().equals(name))
                .toList().get(0).getGraphIndex();
    };
}

