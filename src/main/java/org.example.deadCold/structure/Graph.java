package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.List;


public class Graph {
    private List<List<MatrixItem>> matrix;
    private final ArrayList<Node> nodes;
    private double firstShortestWay;
    private double secondShortestWay;

    public Graph(ArrayList<Node> nodes, Expression distanceFounder, int nodeToDuple) {
        this.nodes = nodes;
        generateMatrix(distanceFounder, nodeToDuple);
    }

    private void generateMatrix(Expression distanceFounder, int nodeToDuple) {
        double pheromone = 1;
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


    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                matrix.append(this.matrix.get(i).get(j).pheromone).append(' ');
            }
            matrix.append("\n");
        }
        return "matrix = " + matrix + "FirstShortestWay = " + firstShortestWay + ' ' + "SecondShortestWay = " + secondShortestWay;
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
}
