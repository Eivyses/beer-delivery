package com.task.delivery.optimization.models;

public class BestElementContainer {

    private String bestString;
    private int bestZone;
    private double bestDouble;

    public BestElementContainer() {

    }

    public String getBestString() {
        return bestString;
    }

    public void setBestString(String bestString) {
        this.bestString = bestString;
    }

    public int getBestZone() {
        return bestZone;
    }

    public void setBestZone(int bestZone) {
        this.bestZone = bestZone;
    }

    public double getBestDouble() {
        return bestDouble;
    }

    public void setBestDouble(double bestDouble) {
        this.bestDouble = bestDouble;
    }
}
