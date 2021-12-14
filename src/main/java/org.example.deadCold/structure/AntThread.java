package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class AntThread implements Callable<int[]> {

    ArrayList<ArrayList<double[]>> graph;
    ArrayList<Node> nodeArray;
    int[] antWay;
    int arg;

    public AntThread(ArrayList<ArrayList<double[]>> graph, ArrayList<Node> nodeArray, int arg) {
        this.graph = graph;
        this.nodeArray = nodeArray;
        this.arg = arg;
    }

    @Override
    public int[] call(){
        Ant ant = new Ant(graph, nodeArray);
        antWay = ant.run(arg);
        return antWay;
    }
}
