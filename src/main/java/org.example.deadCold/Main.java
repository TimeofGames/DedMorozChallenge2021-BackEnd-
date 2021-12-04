package org.example.deadCold;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.deadCold.structure.DistanceFounder;
import org.example.deadCold.structure.FlatDistanceFounder;
import org.example.deadCold.structure.Graph;
import org.example.deadCold.structure.Node;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\data\\test.csv"));
        Graph graph;
        String[] nextLine;
        Node[] arrayNodes = new Node[20];
        for (int i = 0; i < 20; i++) {
            nextLine = reader.readNext();
            arrayNodes[i] = new Node(nextLine);
        }
        graph = new Graph(arrayNodes, new DistanceFounder());
        graph.printGraph();

        graph = new Graph(arrayNodes, new FlatDistanceFounder());
    }
}
