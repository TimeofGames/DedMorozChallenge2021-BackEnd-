package org.example.deadCold.structure;

import java.util.Arrays;
import java.util.ArrayList;

public class Ant {
    private final double Q = 0;
    private int[] visits;
    private ArrayList<double[][][]> graph;
    private int startPoint;
    private final Node[] nodeArray;

    public Ant(ArrayList<double[][][]> graph, int startPoint, Node[] nodeArray) {
        this.graph = graph;
        this.startPoint = startPoint;
        this.visits = new int[graph.size()];
        this.nodeArray = nodeArray;
    }

    public void run(int[] way) {
        ArrayList<double[][]> line;
        int k = startPoint;
        for (int i = 0; i < this.visits.length; i++) {
            line = new ArrayList<>(Arrays.asList(graph.get(k)));
            visits[k] = 1;
            k = Probability(line.get(i), nodeArray, visits);
            way[i] = k;
        }
    }

    private int Probability(double[][] line, Node[] localNodesArray, int[] visits) {
        int n = line.length;
        int powFerro = 1;
        int powDist = 1;
        double[] desires = new double[n];
        double[] P = new double[n];
        double desire;
        double sumDesires = 0;

        for (int i = 0; i < n; i++) {
            if (visits[i] == 0) {
                desire = Math.pow(line[i][1], powFerro) * Math.pow(this.Q / line[i][0], powDist);
                sumDesires += desire;
                desires[i] = desire;
            }
        }

        for (int i = 0; i < n; i++) {
            if (line[i][0] != 0) {
                P[i] = desires[i] / sumDesires;
            }
        }

        double casino = Math.random();
        double counterOfP = 0;
        for (int i = 0; i < n; i++) {
            if (counterOfP + P[i] < casino) {
                counterOfP += P[i];
            } else return localNodesArray[i].getGrahpIndex();
        }
        return -1;
    }


}
