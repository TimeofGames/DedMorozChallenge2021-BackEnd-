package org.example.deadCold.structure;

import java.util.List;
import java.util.concurrent.Callable;

public class PutPheromoneThread implements Callable<Object> {
    private int[] antWay;
    private double wayDistance;
    private double shortestWay;
    private Graph graph;

    public PutPheromoneThread(int[] antWay, double wayDistance, Graph graph, double shortestWay) {
        this.antWay = antWay;
        this.wayDistance = wayDistance;
        this.graph = graph;
        this.shortestWay = shortestWay;
    }

    @Override
    public Object call() {
        double pheromone = RouteConstants.PHEROMONE_FACTOR / wayDistance ;
        for (int j = 1; j < antWay.length; j++) {
            synchronized (graph.getMatrix().get(antWay[j - 1]).get(antWay[j])) {
                if (graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone < RouteConstants.PHEROMONE) {
                    graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone += pheromone;
                } else {
                    graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone = RouteConstants.PHEROMONE;
                }
            }
            synchronized (graph.getMatrix().get(antWay[j]).get(antWay[j - 1])) {
                if (graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone < RouteConstants.PHEROMONE) {
                    graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone += pheromone;
                } else {
                    graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone = RouteConstants.PHEROMONE;
                }
            }
        }
        return null;
    }
}

