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
        double PHEROMONE_FACTOR = 10000;
        double pheromone = PHEROMONE_FACTOR / wayDistance / Math.exp(Math.pow((shortestWay / wayDistance),2));
        for (int j = 1; j < antWay.length; j++) {
            synchronized (graph.getMatrix().get(antWay[j - 1]).get(antWay[j])) {
                if (graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone < 1000) {
                    graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone += pheromone;
                } else {
                    graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone = 1000;
                }
            }
            synchronized (graph.getMatrix().get(antWay[j]).get(antWay[j - 1])) {
                if (graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone < 1000) {
                    graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone += pheromone;
                } else {
                    graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone = 1000;
                }
            }
        }
        return null;
    }
}

