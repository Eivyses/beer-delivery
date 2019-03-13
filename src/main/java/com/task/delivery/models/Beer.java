package com.task.delivery.models;

public class Beer {
    private long breweryId;
    private String name;

    public Beer(long breweryId, String name) {
        this.breweryId = breweryId;
        this.name = name;
    }

    public static Beer buildBeer(String[] args) {
        long breweryId = Long.parseLong(args[1]);
        String name = args[2];
        return new Beer(breweryId, name);
    }

    public long getBreweryId() {
        return breweryId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("     -> %s", this.name);
    }
}
