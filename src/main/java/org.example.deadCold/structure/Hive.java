package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.Arrays;

public class Hive {
    private final ArrayList<ArrayList<double[]>> graph;
    private final ArrayList<Node> nodeArray;
    private int nodeToDuple;

    public Hive(ArrayList<ArrayList<double[]>> graph, ArrayList<Node> nodeArray, int nodeToDuple) {
        this.graph = graph;
        this.nodeArray = nodeArray;
        this.nodeToDuple = nodeToDuple;
    }

    public void fellowBrothers(double[] shortestWays) {
        int size = nodeArray.size();
        ArrayList<int[]> antsWays = new ArrayList<>();
        double[] waysDistance = new double[size];
        for (int i = 0; i < size; i++) {
            Ant ant = new Ant(graph, nodeArray);
            antsWays.add(ant.run(i));
            waysDistance[i] = getDistance(antsWays.get(i));
        }
        //System.out.println(Arrays.stream(waysDistance).min());
        System.out.println(waysDistance[minDistance(waysDistance)]);
        System.out.println(Arrays.toString(antsWays.get(minDistance(waysDistance))));
        evaporation();
        updatePheromone(antsWays, waysDistance);
        shortestWays(antsWays.get(minDistance(waysDistance)), waysDistance[minDistance(waysDistance)], shortestWays);
    }

    public double[] shortestWays(int[] way, double distance, double[] shortestWays) {
        double firstShortestWay;
        int k = 0, g = 0, buffer;

        for (int i = 0; i < way.length; i++) {
            if (way[i] == nodeToDuple) {
                k = i;
            } else if (way[i] == way.length-1) {
                g = i;
            }
        }
        if (k > g) {
            buffer = k;
            k = g;
            g = buffer;
        }
        shortestWays[0] = firstShortestWay = getShortDistance(way, k, g);
        shortestWays[1] = distance - firstShortestWay;
       return shortestWays;
    }

    public double getShortDistance(int[] way, int fistNode, int secondNode) {
        double sumWays = 0;
        for (int i = fistNode+1; i <= secondNode; i++) {
            sumWays += graph.get(way[i - 1]).get(way[i])[0];
        }
        return sumWays;
    }

    public double getDistance(int[] way) {
        double sumWays = 0;
        for (int i = 1; i < way.length; i++) {
            sumWays += graph.get(way[i - 1]).get(way[i])[0];
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
        final double PHEROMONE_FACTOR = 100;
        double newPheromone;
        double pheromone;

        for (int i = 0; i < graph.size(); i++) {
            for (int j = 1; j < graph.size(); j++) {
                pheromone = PHEROMONE_FACTOR / distance[i];
                newPheromone = graph.get(antsWays.get(i)[j - 1]).get(antsWays.get(i)[j])[1] + pheromone;
                graph.get(antsWays.get(i)[j - 1]).get(antsWays.get(i)[j])[1] = newPheromone;
            }
        }
    }

    public int minDistance(double[] waysDistance) {
        int k = 0;
        double minValue = Double.MAX_VALUE;
        for (int i = 0; i < waysDistance.length; i++) {
            if (waysDistance[i] <= minValue) {
                minValue = waysDistance[i];
                k = i;
            }
        }
        return k;
    }

}
