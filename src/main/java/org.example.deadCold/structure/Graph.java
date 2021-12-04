package org.example.deadCold.structure;

import java.util.Arrays;
import java.util.function.UnaryOperator;


public class Graph {
    private double[][][] matrix;
    private final Node[] nodes;
    private double shortestWay;

    public Graph(Node[] nodes, Expression distanceFounder) {
        this.nodes = nodes;
        generateMatrix(distanceFounder);
    }

    private void generateMatrix(Expression distanceFounder) {
        double pheromone = 1;
        int len = this.nodes.length;
        this.matrix = new double[len][len][2];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.matrix[i][j] = new double[]{distanceFounder.getDistance(new double[]{nodes[i].getLongitude(),
                        nodes[i].getLatitude(), nodes[j].getLongitude(), nodes[j].getLatitude()}), pheromone};
            }
        }
    }

    public void printGraph() {
        int len = this.nodes.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.printf("%2.2f ", this.matrix[i][j][1]);
            }
            System.out.println();
        }
        System.out.printf("Кратчайший путь имеет длинну %f\n", this.shortestWay);
    }

    public Node[] getNodes() {
        return nodes;
    }

    public double[][][] getMatrix() {
        return matrix;
    }

    public double getShortestWay() {
        return shortestWay;
    }

    public void setShortestWay(double shortestWay) {
        this.shortestWay = shortestWay;
    }
}
