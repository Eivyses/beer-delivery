package com.task.delivery.dto;

import java.util.ArrayList;
import java.util.List;

public class GeocodeDto {
    private static final int EARTH_RADIUS = 6371;

    private long breweryId;
    private double lon;
    private double lat;
    private int distance;
    private String name;
    private List<BeerDto> beerDtos;

    public GeocodeDto(long breweryId, double lon, double lat, int distance) {
        this(breweryId, lon, lat, distance, "HOME", new ArrayList<>());
    }

    public GeocodeDto(long breweryId, double lon, double lat, int distance, String name, List<BeerDto> beerDtos) {
        this.breweryId = breweryId;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
        this.beerDtos = beerDtos;
        this.name = name;
    }

    public GeocodeDto(GeocodeDto geocodeDto) {
        this(geocodeDto.getBreweryId(), geocodeDto.getLon(), geocodeDto.getLat(), geocodeDto.getDistance(), geocodeDto.getName(), geocodeDto.getBeerDtos());
    }

    public static GeocodeDto of(long breweryId, double lon, double lat, GeocodeDto from) {
        int distance = from.distanceToLocationInKm(lat, lon);
        return new GeocodeDto(breweryId, lon, lat, distance);
    }

    public int beerCount() {
        return beerDtos.size();
    }

    public List<BeerDto> getBeerDtos() {
        return beerDtos;
    }

    public void setBeerDtos(List<BeerDto> beerDtos) {
        this.beerDtos = beerDtos;
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

    /**
     * X
     */
    public double getLon() {
        return lon;
    }

    /**
     * Y
     */
    public double getLat() {
        return lat;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void recalculateDistance(GeocodeDto toGeocodeDto) {
        int newDistance = distanceToLocationInKm(toGeocodeDto.getLat(), toGeocodeDto.getLon());
        this.setDistance(newDistance);
    }

    public int distanceToOther(GeocodeDto toGeocodeDto) {
        return distanceToLocationInKm(toGeocodeDto.getLat(), toGeocodeDto.getLon());
    }

    public int distanceToLocationInKm(double endLat, double endLon) {
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
