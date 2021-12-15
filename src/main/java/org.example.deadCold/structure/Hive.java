package org.example.deadCold.structure;

import com.rainerhahnekamp.sneakythrow.Sneaky;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Hive {
    public List<List<MatrixItem>> graph;
    public List<List<Double>> multiDistanceDesire;
    private int[] shortestWay;
    private final ArrayList<Node> nodeArray;
    private int nodeToDuple;

    public Hive(List<List<MatrixItem>> graph, ArrayList<Node> nodeArray, int nodeToDuple, List<List<Double>> multiDistanceDesire) {
        this.graph = graph;
        this.nodeArray = nodeArray;
        this.nodeToDuple = nodeToDuple;
        this.multiDistanceDesire = multiDistanceDesire;
        shortestWay = new int[nodeArray.size()];
        for (int i = 0; i < nodeArray.size(); i++) {
            shortestWay[i] = i;
        }
    }

    public void fellowBrothers(double[] shortestWays) throws Exception {
        List<Double> waysDistance;
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<int[]> antsWays = getAntsWays(service);

        waysDistance = antsWays.stream()
                .parallel()
                .map(row -> getDistance(graph, row))
                .toList();

        minDistance(antsWays.get(minDistanceThread(waysDistance)));
        updatePheromone(service, antsWays, waysDistance);
        service.shutdown();
        System.out.println("Shortest distance " + getDistance(graph, shortestWay) + "km");
        double[] lengthsOfWays = getLengthsOfWays();
        System.out.println("First way: " + lengthsOfWays[0] + "km\n" +"Second way: " + lengthsOfWays[1] + "km");
    }

    private void putPheromoneElite(int[] antWay, double wayDistance) {
        double PHEROMONE_FACTOR = 100000;
        double pheromone = PHEROMONE_FACTOR / wayDistance;
        for (int i = 1; i < antWay.length; i++) {
            graph.get(antWay[i - 1]).get(antWay[i]).pheromone += pheromone;
            }
        }

    private void putPheromone(ExecutorService service, List<int[]> antsWays, List<Double> waysDistance) throws InterruptedException {
        List<UpdatePheromoneThread> updatePheromoneTasks = new ArrayList<UpdatePheromoneThread>();
        double shortestWay = getDistance(graph,this.shortestWay);
        for (int i = 0; i < antsWays.size(); i++) {
            updatePheromoneTasks.add(new UpdatePheromoneThread(antsWays.get(i), waysDistance.get(i), graph, shortestWay));
        }
        service.invokeAll(updatePheromoneTasks);
    }

    private void updatePheromone(ExecutorService service, List<int[]> antsWays, List<Double> waysDistance) throws InterruptedException {
        evaporation();
        putPheromone(service, antsWays, waysDistance);
        putPheromoneElite(shortestWay, getDistance(graph, shortestWay));
    }

    private List<int[]> getAntsWays(ExecutorService service) throws InterruptedException {
        ArrayList<AntThread> ants = new ArrayList<AntThread>();
        int size = nodeArray.size();
        List<Future<int[]>> antsWaysFuture;
        for (int i = 0; i < size; i++) {
            ants.add(new AntThread(graph, nodeArray, i, multiDistanceDesire));
        }
        antsWaysFuture = service.invokeAll(ants);

        return antsWaysFuture.stream()
                .map(f -> Sneaky.sneak(f::get))
                .toList();
    }

    private double[] getLengthsOfWays() {
        double[] lengthsOfWays = new double[2];
        double length = 0;
        int i = 0;
        while (shortestWay[i] != 133 && shortestWay[i] != 1113) {
            i++;
        }
        i++;
        for (; i < shortestWay.length; i++) {
            if (shortestWay[i] != 133 && shortestWay[i] != 1113) {
                length += graph.get(shortestWay[i - 1]).get(shortestWay[i]).distance;
            } else {
                break;
            }
        }
        lengthsOfWays[0] = length;
        lengthsOfWays[1] = getDistance(graph,shortestWay)-length;
        return lengthsOfWays;
    }

    private static double getDistance(List<List<MatrixItem>> graph, int[] way) {
        double sumWays = 0;
        for (int i = 1; i < way.length; i++) {
            sumWays += graph.get(way[i - 1]).get(way[i]).distance;
        }
        return sumWays;
    }

    private void evaporation() {
        final double AFTER_EVAPORATION = 0.64;
        graph.stream()
                .parallel()
                .forEach(row -> row.forEach(item -> {
                    if (item.pheromone > 10) {
                        item.pheromone = item.pheromone * AFTER_EVAPORATION;
                    } else {
                        item.pheromone = 10;
                    }
                }));
    }

    private int minDistanceThread(List<Double> waysDistance) {
        int k = 0;
        for (int i = 0; i < waysDistance.size(); i++) {
            if (waysDistance.get(i) <= waysDistance.get(k)) {
                k = i;
            }
        }
        return k;
    }

    private void minDistance(int[] shortestWay) {
        if (getDistance(graph, shortestWay) < getDistance(graph, this.shortestWay)) {
            this.shortestWay = shortestWay;
        }
    }
}
