package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Graph {
    private List<List<MatrixItem>> matrix;
    private List<List<Double>> multiDistanceDesire = new ArrayList<>();
    private final ArrayList<Node> nodes;
    private double firstShortestWay;
    private double secondShortestWay;
    private int[] shortestWay;
    private final int nodeToDuple;

    public Graph(ArrayList<Node> nodes, Expression distanceFounder, int nodeToDuple) {
        this.nodes = nodes;
        this.nodeToDuple = nodeToDuple;
        generateMatrix(distanceFounder);
        cookDistanceDesire();
    }

    private void generateMatrix(Expression distanceFounder) {
        double pheromone = 1000;
        this.matrix = new ArrayList<>();
        for (Node value : this.nodes) {
            ArrayList<MatrixItem> localList = new ArrayList<>();
            for (Node node : this.nodes) {
                localList.add(new MatrixItem(distanceFounder.getDistance(new double[]{value.getLongitude(),
                        value.getLatitude(), node.getLongitude(), node.getLatitude()}), pheromone));
            }
            this.matrix.add(localList);
        }
        this.matrix.add(matrix.get(nodeToDuple));
        for (int i = 0; i < nodes.size(); i++) {
            this.matrix.get(i).add(this.matrix.get(nodeToDuple).get(i));
        }
        this.matrix.get(this.nodes.size()).add(new MatrixItem(0.0, pheromone));

        this.nodes.add(new Node(this.nodes.get(nodeToDuple).toString().split(" "), this.nodes.size()));
    }

    private void cookDistanceDesire() {
        final double POW_DISTANCE = 5;
        final int DISTANCE_FACTOR = 1;
        for (int i = 0; i < matrix.size(); i++) {
            List<Double> localList = new ArrayList<>();
            for (int j = 0; j < matrix.size(); j++) {
                localList.add(Math.pow(DISTANCE_FACTOR / matrix.get(i).get(j).distance, POW_DISTANCE));
            }
            this.multiDistanceDesire.add(localList);
        }
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                matrix.append(this.matrix.get(i).get(j).pheromone).append(' ');
            }
            matrix.append("\n");
        }
        return "matrix = " + matrix + "FirstShortestWay = " + firstShortestWay + ' ' + "SecondShortestWay = " + secondShortestWay + "ShortestWay = " + Arrays.toString(shortestWay);
    }


    public int length(){
        return nodes.size();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public List<List<MatrixItem>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<MatrixItem>> matrix) {
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

    public List<List<Double>> getMultiDistanceDesire() {
        return multiDistanceDesire;
    }

    public void setMultiDistanceDesire(List<List<Double>> multiDistanceDesire) {
        this.multiDistanceDesire = multiDistanceDesire;
    }

    public void setShortestWay(int[] shortestWay) {
        this.shortestWay = shortestWay;
    }

    public int[] getShortestWay() {
        return shortestWay;
    }

    public int getNodeToDuple() {
        return nodeToDuple;
    }
}
