package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class AntThread implements Callable<int[]> {
    List<List<Double>> multiDistanceDesire;
    List<List<MatrixItem>> graph;
    ArrayList<Node> nodeArray;
    int[] antWay;
    int arg;

    public AntThread(List<List<MatrixItem>> graph, ArrayList<Node> nodeArray, int arg, List<List<Double>> multiDistanceDesire) {
        this.graph = graph;
        this.nodeArray = nodeArray;
        this.arg = arg;
        this.multiDistanceDesire = multiDistanceDesire;
    }

    @Override
    public int[] call() {
        Ant ant = new Ant(graph, nodeArray, multiDistanceDesire);
        antWay = ant.run(arg);
        return antWay;
    }
}
