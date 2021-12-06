package org.example.deadCold;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.deadCold.structure.FlatDistanceFounder;
import org.example.deadCold.structure.Graph;
import org.example.deadCold.structure.Hive;
import org.example.deadCold.structure.Node;


import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\data\\test.csv"));
        Graph graph;
        String[] nextLine;
        Node[] arrayNodes = new Node[20];
        for (int i = 0; i < 20; i++) {
            nextLine = reader.readNext();
            arrayNodes[i] = new Node(nextLine, i);
        }

        graph = new Graph(arrayNodes, new FlatDistanceFounder());
        graph.printGraph();
        Hive hive = new Hive(graph.getMatrix(), arrayNodes);
        for (int i = 0; i < 1000; i++) {
            hive.fellowBrothers();
        }
        graph.printGraph();
    }
}
