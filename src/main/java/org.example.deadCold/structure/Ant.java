package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class Ant {
    private final int[] visits;
    private final int[] way;
    private final List<List<MatrixItem>> graph;
    private final ArrayList<Node> nodeArray;

    public Ant(List<List<MatrixItem>> graph, ArrayList<Node> nodeArray) {
        this.graph = graph;
        this.visits = new int[nodeArray.size()];
        this.way = new int[graph.size()];
        this.nodeArray = nodeArray;
    }

    public int[] run(int startPoint) {
        List<MatrixItem> line;
        int k = startPoint;
        for (int i = 0; i < this.visits.length; i++) {
            line = graph.get(k);
            visits[k] = 1;
            way[i] = k;
            k = nextNode(line);
        }
        return way;
    }

    private double[] getDesire(List<MatrixItem> line) {
        final int POW_PHEROMONE = 2;
        final int POW_DISTANCE = 3;
        final int DISTANCE_FACTOR = 10;
        int size = nodeArray.size();
        double[] desires = new double[size];
        for (int i = 0; i < size; i++) {
            if (visits[i] == 0 && line.get(i).distance != 0) {
                desires[i] = Math.pow(line.get(i).pheromone, POW_PHEROMONE) * Math.pow(DISTANCE_FACTOR / line.get(i).distance, POW_DISTANCE);
            }
        }
        return desires;
    }

    private double[] getProbabilities(List<MatrixItem> line, double[] desires, double sumDesires) {
        double[] probability = new double[line.size()];
        for (int i = 0; i < line.size(); i++) {
            if (line.get(i).distance != 0) {
                probability[i] = desires[i] / sumDesires;
            }
        }
        return probability;
    }

    private int choiceNode(List<MatrixItem> line, double[]probability){
        double casino = Math.random();
        double counterOfProbability = 0;
        for (int i = 0; i < line.size(); i++) {
            if (counterOfProbability + probability[i] < casino) {
                counterOfProbability += probability[i];
            } else return nodeArray.get(i).getGraphIndex();
        }
        return -1;
    }

    private int nextNode(List<MatrixItem> line) {
        double[] desires;
        double[] probability;
        double sumDesires;

        desires = getDesire(line);
        sumDesires = DoubleStream.of(desires).sum();
        probability = getProbabilities(line, desires, sumDesires);
        return choiceNode(line,probability);
    }
}
