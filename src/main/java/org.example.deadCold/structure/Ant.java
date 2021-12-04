package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.stream.DoubleStream;

public class Ant {
    private final int[] visits;
    private final int[] way;
    private final ArrayList<ArrayList<double[]>> graph;
    private final int startPoint;
    private final Node[] nodeArray;

    public Ant(ArrayList<ArrayList<double[]>> graph, int startPoint, Node[] nodeArray) {
        this.graph = graph;
        this.startPoint = startPoint;
        this.visits = new int[graph.size()];
        this.way = new int[graph.size()];
        this.nodeArray = nodeArray;
    }

    public int[] run() {
        ArrayList<double[]> line;
        int k = startPoint;
        for (int i = 0; i < this.visits.length; i++) {
            line = graph.get(k);
            visits[k] = 1;
            way[i] = k;
            k = nextNode(line);
        }
        return way;
    }

    private double[] getDesire(ArrayList<double[]> line) {
        int POW_PHEROMONE = 1;
        int POW_DISTANCE = 1;
        int DISTANCE_FACTOR = 1;
        int size = line.size();
        double[] desires = new double[size];
        for (int i = 0; i < size; i++) {
            if (visits[i] == 0) {
                desires[i] = Math.pow(line.get(i)[1], POW_PHEROMONE) * Math.pow(DISTANCE_FACTOR / line.get(i)[0], POW_DISTANCE);
            }
        }
        return desires;
    }

    private double[] getProbabilities(ArrayList<double[]> line, double[] desires, double sumDesires) {
        double[] probability = new double[line.size()];
        for (int i = 0; i < line.size(); i++) {
            if (line.get(i)[0] != 0) {
                probability[i] = desires[i] / sumDesires;
            }
        }
        return probability;
    }

    private int choiceNode(ArrayList<double[]> line, double[]probability){
        double casino = Math.random();
        double counterOfP = 0;
        for (int i = 0; i < line.size(); i++) {
            if (counterOfP + probability[i] < casino) {
                counterOfP += probability[i];
            } else return nodeArray[i].getGraphIndex();
        }
        return -1;
    }

    private int nextNode(ArrayList<double[]> line) {
        double[] desires;
        double[] probability;
        double sumDesires;

        desires = getDesire(line);
        sumDesires = DoubleStream.of(desires).sum();
        probability = getProbabilities(line, desires, sumDesires);
        return choiceNode(line,probability);
    }
}
