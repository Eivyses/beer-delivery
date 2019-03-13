package com.task.delivery.models;

import com.task.delivery.utils.DistanceUtils;

import java.util.Comparator;

public class Geocode {
    private long breweryId;
    private double lon;
    private double lat;
    private int distance;

    public Geocode(long breweryId, double lon, double lat, int distance) {
        this.breweryId = breweryId;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
    }

    public static Geocode buildFromCsv(String[] args, double latStart, double lonStart) {
        long breweryId = Long.parseLong(args[1]);
        double lat = Double.parseDouble(args[2]);
        double lon = Double.parseDouble(args[3]);
        int distance = DistanceUtils.distance(latStart, lat, lonStart, lon);
        return new Geocode(breweryId, lon, lat, distance);
    }

    public long getBreweryId() {
        return breweryId;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("breweryId: %d, distance: %d", this.breweryId, this.distance);
    }
}
