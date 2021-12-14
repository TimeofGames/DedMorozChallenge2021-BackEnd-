package org.example.deadCold.structure;

import com.rainerhahnekamp.sneakythrow.Sneaky;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Hive {
    private final ArrayList<ArrayList<double[]>> graph;
    private final ArrayList<Node> nodeArray;
    private int nodeToDuple;

    public Hive(ArrayList<ArrayList<double[]>> graph, ArrayList<Node> nodeArray, int nodeToDuple) {
        this.graph = graph;
        this.nodeArray = nodeArray;
        this.nodeToDuple = nodeToDuple;
    }

    public void fellowBrothers(double[] shortestWays) throws Exception {
        int size = nodeArray.size();
        List<Future<int[]>> antsWaysFuture = new ArrayList<>();
        double[] waysDistance = new double[size];
        ArrayList<AntThread> ants = new ArrayList<AntThread>();
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < size; i++) {
            ants.add(new AntThread(graph,nodeArray,i));
        }
        antsWaysFuture = service.invokeAll(ants);
        service.shutdown();

        List<int[]> antsWays = antsWaysFuture.stream()
                .map(f -> Sneaky.sneak(f::get))
                .toList();

        for (int i = 0; i < size; i++) {
            waysDistance[i] = getDistance(graph, antsWays.get(i));
        }
        System.out.println("Shortest distance " + waysDistance[minDistance(waysDistance)] + "km");
        evaporation();
        updatePheromone(antsWays, waysDistance);
    }

    public double[] shortestWays(int[] way, double distance, double[] shortestWays) {
        double firstShortestWay;
        int k = 0, g = 0, buffer;

        for (int i = 0; i < way.length; i++) {
            if (way[i] == nodeToDuple) {
                k = i;
            } else if (way[i] == way.length - 1) {
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
        for (int i = fistNode + 1; i <= secondNode; i++) {
            sumWays += graph.get(way[i - 1]).get(way[i])[0];
        }
        return sumWays;
    }

    public static double getDistance(ArrayList<ArrayList<double[]>> graph, int[] way) {
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

    public void updatePheromone(List<int[]> antsWays, double[] distance) {
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
