package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.Arrays;

public class Hive {
    private final ArrayList<ArrayList<double[]>> graph;
    private final Node[] nodeArray;

    public Hive(ArrayList<ArrayList<double[]>> graph, Node[] nodeArray) {
        this.graph = graph;
        this.nodeArray = nodeArray;
    }

    public void fellowBrothers() {
        ArrayList<int[]> antsWays = new ArrayList<>();
        double[] waysDistance = new double[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            Ant ant = new Ant(graph, nodeArray);
            antsWays.add(ant.run(i));
            waysDistance[i] = getDistance(antsWays.get(i));
        }

        evaporation();
        updatePheromone(antsWays, waysDistance);
    }

    public double getDistance(int[] way) {
        double sumWays = 0;
        for (int i = 1; i < way.length; i++) {
            sumWays += graph.get(way[i-1]).get(way[i])[0];
        }
        return sumWays;
    }

    public void evaporation() {
        final double AFTER_EVAPORATION = 0.64;
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
                graph.get(i).get(j)[1] = AFTER_EVAPORATION * graph.get(i).get(j)[1];
            }
        }
    }

    public void updatePheromone(ArrayList<int[]> antsWays, double[] distance) {
        final double PHEROMONE_FACTOR = 5;
        double newPheromone;
        double pheromone;


        for (int i = 0; i < graph.size(); i++) {
            for (int j = 1; j < graph.size(); j++) {
                pheromone = PHEROMONE_FACTOR / distance[i];
                newPheromone = graph.get(antsWays.get(i)[j-1]).get(antsWays.get(i)[j])[1] + pheromone;
                graph.get(antsWays.get(i)[j-1]).get(antsWays.get(i)[j])[1] = newPheromone;
                graph.get(antsWays.get(j-1)[i]).get(antsWays.get(j)[i])[1] = newPheromone;
            }
        }
    }

}
