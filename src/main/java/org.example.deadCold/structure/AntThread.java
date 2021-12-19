package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class AntThread implements Callable<int[]> {
    private final Graph graph;
    private final int arg;

    public AntThread(Graph graph, int arg ) {
        this.graph = graph;
        this.arg = arg;
    }

    @Override
    public int[] call() {
        Ant ant = new Ant(graph.getMatrix(), graph.getNodes(), graph.getMultiDistanceDesire());
        return ant.run(arg);
    }
}
