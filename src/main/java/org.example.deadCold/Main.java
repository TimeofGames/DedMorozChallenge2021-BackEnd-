package org.example.deadCold;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


import org.example.deadCold.structure.*;


import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;


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


        int nodeToDuple = 5;
        double[] shortestWays = new double[2];
        graph = new Graph(arrayNodes, new DistanceFounder(), nodeToDuple);
        Hive hive = new Hive(graph.getMatrix(), arrayNodes, nodeToDuple, graph.getMultiDistanceDesire());
        System.out.println(Runtime.getRuntime().availableProcessors() * 2);
        for (int i = 0; i < 200; i++) {
            long clock = System.currentTimeMillis();
            hive.fellowBrothers(shortestWays);
            System.out.println("Time on operation " + (System.currentTimeMillis() - clock) + "ìs");
        }
        graph.setFirstShortestWay(shortestWays[0]);
        graph.setSecondShortestWay(shortestWays[1]);

    }
}
