package com.task.delivery.models;

import java.util.ArrayList;
import java.util.List;

public class Geocode {
    private static final int EARTH_RADIUS = 6371;

    private long breweryId;
    private double lon;
    private double lat;
    private int distance;
    private String name;
    private List<Beer> beers;

    public Geocode(long breweryId, double lon, double lat, int distance) {
        this(breweryId, lon, lat, distance, "NONE", new ArrayList<>());
    }

    public Geocode(long breweryId, double lon, double lat, int distance, String name, List<Beer> beers) {
        this.breweryId = breweryId;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
        this.beers = beers;
        this.name = name;
    }

    public Geocode(Geocode geocode) {
        this(geocode.getBreweryId(), geocode.getLon(), geocode.getLat(), geocode.getDistance(), geocode.getName(), geocode.getBeers());
    }

    public static Geocode of(long breweryId, double lon, double lat, Geocode from) {
        int distance = from.distanceToLocationInKm(lat, lon);
        return new Geocode(breweryId, lon, lat, distance);
    }

    public List<Beer> getBeers() {
        return beers;
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
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

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void recalculateDistance(Geocode toGeocode) {
        int newDistance = distanceToLocationInKm(toGeocode.getLat(), toGeocode.getLon());
        this.setDistance(newDistance);
    }

    public int distanceToOther(Geocode toGeocode) {
        return distanceToLocationInKm(toGeocode.getLat(), toGeocode.getLon());
    }

    private int distanceToLocationInKm(double endLat, double endLon) {
        double latDistance = Math.toRadians(endLat - lat);
        double lonDistance = Math.toRadians(endLon - lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(endLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return (int) Math.sqrt(distance) / 1000;
    }

    @Override
    public String toString() {
        return String.format("     -> [%d] %s %f %f distance: %dkm", this.breweryId, this.name, this.lat, this.lon, this.distance);
    }
}
