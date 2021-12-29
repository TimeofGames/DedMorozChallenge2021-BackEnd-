package org.example.deadCold.structure;

public class RouteConstants {
    public static final double POW_DISTANCE = 3;
    public static final int DISTANCE_FACTOR = 1;
    public static final double PHEROMONE_FACTOR_ELITE = 10000000;
    public static final int PHEROMONE = 10000;
    public static final double PHEROMONE_FACTOR = 1000000;
    public static final double POW_PHEROMONE = 2;

    private RouteConstants() {
        throw new IllegalStateException("Constants class");
    }
}
