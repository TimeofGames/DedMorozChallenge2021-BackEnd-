package org.example.deadCold.structure;

import java.util.ArrayList;


public class Graph {
    private ArrayList<ArrayList<double[]>> matrix;
    private final Node[] nodes;
    private double firstShortestWay;


    private double secondShortestWay;

    public Graph(Node[] nodes, Expression distanceFounder, int nodeToDuple) {
        this.nodes = nodes;
        generateMatrix(distanceFounder, nodeToDuple);
    }

    private void generateMatrix(Expression distanceFounder, int nodeToDuple) {
        double pheromone = 1;
        this.matrix = new ArrayList<>();
        for (Node value : this.nodes) {
            ArrayList<double[]> localList = new ArrayList<>();
            for (Node node : this.nodes) {
                localList.add(new double[]{distanceFounder.getDistance(new double[]{value.getLongitude(),
                        value.getLatitude(), node.getLongitude(), node.getLatitude()}), pheromone});
            }
            this.matrix.add(localList);
        }
        this.matrix.add(matrix.get(nodeToDuple));
        for (int i = 0; i < nodes.length; i++) {
            this.matrix.get(i).add(this.matrix.get(nodeToDuple).get(i));
        }
        this.matrix.get(this.nodes.length).add(new double[]{0.0, pheromone});
    }


    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                matrix.append(this.matrix.get(i).get(j)[0]).append(' ');
            }
            matrix.append("\n");
        }
        return "matrix = " + matrix + "FirstShortestWay = " + firstShortestWay + ' ' + "SecondShortestWay = " + secondShortestWay;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public ArrayList<ArrayList<double[]>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<double[]>> matrix) {
        this.matrix = matrix;
    }

    public void setFirstShortestWay(double firstShortestWay) {
        this.firstShortestWay = firstShortestWay;
    }

    public void setSecondShortestWay(double secondShortestWay) {
        this.secondShortestWay = secondShortestWay;
    }

    public double getFirstShortestWay() {
        return firstShortestWay;
    }

    public double getSecondShortestWay() {
        return secondShortestWay;
    }
}
