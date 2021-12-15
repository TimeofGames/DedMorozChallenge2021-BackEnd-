package org.example.deadCold.structure;

public class Node {
    private final String name;
    private final String index;
    private final double latitude;
    private final double longitude;
    private final String population;
    private final int graphIndex;

    public Node(String[] line, int index) {
        this.index = line[0];
        this.name = line[1];
        this.latitude = Double.parseDouble(line[2]);
        this.longitude = Double.parseDouble(line[3]);
        this.population = line[4];
        this.graphIndex = index;
    }

    @Override
    public String toString() {
        return name + ' ' + index + ' ' + latitude + ' ' + longitude + ' ' + population;
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPopulation() {
        return population;
    }

    public int getGraphIndex() {
        return graphIndex;
    }
}
