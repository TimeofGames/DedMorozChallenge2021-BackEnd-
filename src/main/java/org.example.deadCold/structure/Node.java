package org.example.deadCold.structure;

public class Node {
    private final String name;
    private final String index;
    private final double latitude;
    private final double longitude;
    private final String population;

    public Node(String[] line) {
        this.index = line[0];
        this.name = line[1];
        this.latitude = Double.parseDouble(line[2]);
        this.longitude = Double.parseDouble(line[3]);
        this.population = line[4];
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
}
