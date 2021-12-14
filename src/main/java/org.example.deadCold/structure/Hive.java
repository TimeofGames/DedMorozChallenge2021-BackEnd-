package org.example.deadCold.structure;

import com.rainerhahnekamp.sneakythrow.Sneaky;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Hive {
    public List<List<MatrixItem>> graph;
    private final ArrayList<Node> nodeArray;
    private int nodeToDuple;

    public Hive(List<List<MatrixItem>> graph, ArrayList<Node> nodeArray, int nodeToDuple) {
        this.graph = graph;
        this.nodeArray = nodeArray;
        this.nodeToDuple = nodeToDuple;
    }

    public void fellowBrothers(double[] shortestWays) throws Exception {
        List<Double> waysDistance;
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<int[]> antsWays = getAntsWays(service);

        waysDistance = antsWays.stream()
                .parallel()
                .map(row -> getDistance(graph, row))
                .toList();

        System.out.println("Shortest distance " + waysDistance.get(minDistance(waysDistance)) + "km");

        evaporation();
        updatePheromone(service, antsWays, waysDistance);
        updatePheromoneElite(antsWays.get(minDistance(waysDistance)),waysDistance.get(minDistance(waysDistance)));
        service.shutdown();
    }

    private void updatePheromoneElite(int[] antWay, double wayDistance){
        double PHEROMONE_FACTOR = 10000;
        double pheromone = PHEROMONE_FACTOR / wayDistance;
        for (int j = 1; j<antWay.length; j++){
            synchronized (graph.get(antWay[j-1]).get(antWay[j])){
                graph.get(antWay[j-1]).get(antWay[j]).pheromone += pheromone;}
        }
    }

    private void updatePheromone(ExecutorService service, List<int[]> antsWays, List<Double> waysDistance) throws InterruptedException {
        List<UpdatePheromoneThread> updatePheromoneTasks = new ArrayList<UpdatePheromoneThread>();
        for (int i = 0; i < antsWays.size(); i++) {
            updatePheromoneTasks.add(new UpdatePheromoneThread(antsWays.get(i), waysDistance.get(i), graph));
        }
        service.invokeAll(updatePheromoneTasks);
    }

    private List<int[]> getAntsWays(ExecutorService service) throws InterruptedException {
        ArrayList<AntThread> ants = new ArrayList<AntThread>();
        int size = nodeArray.size();
        List<Future<int[]>> antsWaysFuture;
        for (int i = 0; i < size; i++) {
            ants.add(new AntThread(graph, nodeArray, i));
        }
        antsWaysFuture = service.invokeAll(ants);

        return antsWaysFuture.stream()
                .map(f -> Sneaky.sneak(f::get))
                .toList();
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
            sumWays += graph.get(way[i - 1]).get(way[i]).distance;
        }
        return sumWays;
    }

    public static double getDistance(List<List<MatrixItem>> graph, int[] way) {
        double sumWays = 0;
        for (int i = 1; i < way.length; i++) {
            sumWays += graph.get(way[i - 1]).get(way[i]).distance;
        }
        return sumWays;
    }

    public void evaporation() {
        final double AFTER_EVAPORATION = 0.64;
        graph = graph.stream()
                .parallel()
                .map(row -> row.stream().peek(item -> item.pheromone = item.pheromone * AFTER_EVAPORATION).toList())
                .toList();
    }

    public int minDistance(List<Double> waysDistance) {
        int k = 0;
        for (int i = 0; i < waysDistance.size(); i++) {
            if (waysDistance.get(i) <= waysDistance.get(k)) {
                k = i;
            }
        }
        return k;
    }

}
