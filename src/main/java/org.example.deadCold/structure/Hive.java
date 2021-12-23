package org.example.deadCold.structure;

import com.rainerhahnekamp.sneakythrow.Sneaky;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Hive {
    public Graph graph;
    private int[] shortestWay;

    public Hive(Graph graph) {
        this.graph = graph;
        shortestWay = new int[graph.getNodes().size()];
        for (int i = 0; i < graph.getNodes().size(); i++) {
            shortestWay[i] = i;
        }
    }

    public void fellowBrothers() throws Exception {
        List<Double> waysDistance;
        //ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService service = Executors.newFixedThreadPool(5);
        List<int[]> antsWays = getAntsWays(service);

        waysDistance = antsWays.stream()
                .parallel()
                .map(row -> getDistance(graph, row))
                .toList();

        minDistance(antsWays.get(minDistanceThread(waysDistance)));
        updatePheromone(service, antsWays, waysDistance);
        service.shutdown();
        graph.setShortestWay(shortestWay);
        double[] lengthsOfWays = getLengthsOfWays();
        graph.setFirstShortestWay(lengthsOfWays[0]);
        graph.setSecondShortestWay(lengthsOfWays[1]);
    }

    private void putPheromoneElite(int[] antWay, double wayDistance) {
        double PHEROMONE_FACTOR = 10000;
        double pheromone = PHEROMONE_FACTOR / wayDistance;
        for (int i = 1; i < antWay.length; i++) {
            if (graph.getMatrix().get(antWay[i - 1]).get(antWay[i]).pheromone < 1000) {
                graph.getMatrix().get(antWay[i - 1]).get(antWay[i]).pheromone += pheromone;
            } else {
                graph.getMatrix().get(antWay[i - 1]).get(antWay[i]).pheromone = 1000;
            }
            if (graph.getMatrix().get(antWay[i]).get(antWay[i - 1]).pheromone < 1000) {
                graph.getMatrix().get(antWay[i]).get(antWay[i - 1]).pheromone += pheromone;
            } else {
                graph.getMatrix().get(antWay[i]).get(antWay[i - 1]).pheromone = 1000;
            }
        }
    }

    private void putPheromone(ExecutorService service, List<int[]> antsWays, List<Double> waysDistance) throws
            InterruptedException {
        List<PutPheromoneThread> updatePheromoneTasks = new ArrayList<PutPheromoneThread>();
        double shortestWay = getDistance(graph, this.shortestWay);
        for (int i = 0; i < antsWays.size(); i++) {
            updatePheromoneTasks.add(new PutPheromoneThread(antsWays.get(i), waysDistance.get(i), graph, shortestWay));
        }
        service.invokeAll(updatePheromoneTasks);
    }

    private void updatePheromone(ExecutorService service, List<int[]> antsWays, List<Double> waysDistance) throws
            InterruptedException {
        evaporation();
        putPheromone(service, antsWays, waysDistance);
        putPheromoneElite(shortestWay, getDistance(graph, shortestWay));
    }

    private List<int[]> getAntsWays(ExecutorService service) throws InterruptedException {
        ArrayList<AntThread> ants = new ArrayList<AntThread>();
        int size = graph.getNodes().size();
        List<Future<int[]>> antsWaysFuture;
        for (int i = 0; i < size; i++) {
            ants.add(new AntThread(graph, i));
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
        while (shortestWay[i] != graph.getNodeToDuple() && shortestWay[i] != graph.getNodes().size()) {
            i++;
        }
        i++;
        for (; i < shortestWay.length; i++) {
            if (shortestWay[i] != graph.getNodeToDuple() && shortestWay[i] != graph.getNodes().size()) {
                length += graph.getMatrix().get(shortestWay[i - 1]).get(shortestWay[i]).distance;
            } else {
                break;
            }
        }
        lengthsOfWays[0] = length;
        lengthsOfWays[1] = getDistance(graph, shortestWay) - length;
        return lengthsOfWays;
    }

    private static double getDistance(Graph graph, int[] way) {
        double sumWays = 0;
        for (int i = 1; i < way.length; i++) {
            sumWays += graph.getMatrix().get(way[i - 1]).get(way[i]).distance;
        }
        return sumWays;
    }

    private void evaporation() {
        final double AFTER_EVAPORATION = 0.70;
        graph.getMatrix().stream()
                .parallel()
                .forEach(row -> row.forEach(item -> {
                    if (item.pheromone > 20) {
                        item.pheromone = item.pheromone * AFTER_EVAPORATION;
                    } else {
                        item.pheromone = 20;
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
