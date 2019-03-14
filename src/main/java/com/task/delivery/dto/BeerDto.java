package com.task.delivery.dto;

public class BeerDto {
    private String name;

    public BeerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("     -> %s", this.name);
    }
}
