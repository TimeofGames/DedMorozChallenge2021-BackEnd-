package org.example.deadCold.structure;

public class DistanceFounder implements Expression {
    private static final double EARTH_RADIUS = 6378137;

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @Override
    public double getDistance(double[] settings) {
        double radLat1 = rad(settings[1]);
        double radLat2 = rad(settings[3]);
        double deltaLat = radLat1 - radLat2;
        double deltaLon = rad(settings[0]) - rad(settings[2]);
        double range = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(deltaLon / 2), 2)));
        range = range * EARTH_RADIUS / 1000.0;
        return range;
    }

}
