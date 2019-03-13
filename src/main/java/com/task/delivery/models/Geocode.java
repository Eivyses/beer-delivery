package com.task.delivery.models;

import com.task.delivery.utils.DistanceUtils;

public class Geocode {
    private long breweryId;
    private double lon;
    private double lat;
    private int distance;
    private String name;

    public Geocode(long breweryId, double lon, double lat, int distance) {
        this.breweryId = breweryId;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
    }

    public Geocode(Geocode geocode) {
        this(geocode.getBreweryId(), geocode.getLon(), geocode.getLat(), geocode.getDistance());
    }

    public static Geocode buildLocationFromCsv(String[] args, double latStart, double lonStart) {
        long breweryId = Long.parseLong(args[1]);
        double lat = Double.parseDouble(args[2]);
        double lon = Double.parseDouble(args[3]);
        int distance = DistanceUtils.distance(latStart, lat, lonStart, lon);
        return new Geocode(breweryId, lon, lat, distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(long breweryId) {
        this.breweryId = breweryId;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        if (this.breweryId > 0) {
            return String.format("     -> [%d] %s %f %f distance %dkm", this.breweryId, this.name, this.lat, this.lon, this.distance);
        } else {
            return String.format("     -> %s %f %f distance %dkm", this.name, this.lat, this.lon, this.distance);
        }
    }
}
