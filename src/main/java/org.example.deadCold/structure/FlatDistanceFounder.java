package org.example.deadCold.structure;

public class FlatDistanceFounder implements Expression {

    @Override
    public double getDistance(double[] settings) {
        return Math.sqrt(Math.pow(settings[2] - settings[0], 2) + Math.pow(settings[3] - settings[1], 2));
    }
}
