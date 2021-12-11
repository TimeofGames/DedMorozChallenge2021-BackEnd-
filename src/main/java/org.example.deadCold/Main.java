package org.example.deadCold;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.deadCold.structure.FlatDistanceFounder;
import org.example.deadCold.structure.Graph;
import org.example.deadCold.structure.Hive;
import org.example.deadCold.structure.Node;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\data\\test.csv"));
        Graph graph;
        String[] nextLine;
        ArrayList<Node> arrayNodes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            nextLine = reader.readNext();
            arrayNodes.add(i, new Node(nextLine, i));
        }

        int nodeToDuple = 5;
        double[] shortestWays = new double[2];
        graph = new Graph(arrayNodes, new FlatDistanceFounder(), nodeToDuple);
        System.out.println(graph);
        Hive hive = new Hive(graph.getMatrix(), arrayNodes, nodeToDuple);
        for (int i = 0; i < 100; i++) {
           hive.fellowBrothers(shortestWays);
        }
        graph.setFirstShortestWay(shortestWays[0]);
        graph.setSecondShortestWay(shortestWays[1]);

        System.out.println(graph);
    }
}
