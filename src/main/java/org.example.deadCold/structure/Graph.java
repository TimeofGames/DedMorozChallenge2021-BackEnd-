package org.example.deadCold.structure;

import java.util.ArrayList;


public class Graph {
    private ArrayList<ArrayList<double[]>> matrix;
    private final Node[] nodes;
    private double shortestWay;

    public Graph(Node[] nodes, Expression distanceFounder) {
        this.nodes = nodes;
        generateMatrix(distanceFounder);
    }

    private void generateMatrix(Expression distanceFounder) {
        double pheromone = 1;
        int len = this.nodes.length;
        this.matrix = new ArrayList<>();
        for (Node value : this.nodes) {
            ArrayList<double[]> localList = new ArrayList<>();
            for (Node node : this.nodes) {
                localList.add(new double[]{distanceFounder.getDistance(new double[]{value.getLongitude(),
                        value.getLatitude(), node.getLongitude(), node.getLatitude()}), pheromone});
            }
            this.matrix.add(localList);
        }
    }

    public void printGraph() {
        int len = this.nodes.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.printf("%2.2f ", this.matrix.get(i).get(j)[0]);
            }
            System.out.println();
        }
        System.out.printf("Кратчайший путь имеет длинну %f\n", this.shortestWay);
    }

    public Node[] getNodes() {
        return nodes;
    }

    public ArrayList<ArrayList<double[]>> getMatrix() {
        return matrix;
    }

    public double getShortestWay() {
        return shortestWay;
    }

    public void setShortestWay(double shortestWay) {
        this.shortestWay = shortestWay;
    }
}
