package com.task.delivery.utils;

import com.task.delivery.models.Beer;
import com.task.delivery.models.Geocode;

import java.util.ArrayList;
import java.util.List;

public class PrintUtils {

    public static void printResult(List<Geocode> results) {
        List<Beer> beers = new ArrayList<>();
        System.out.println(String.format("Found %d beer factories:", results.size() - 2));

        int distance = 0;
        for (Geocode geocode : results) {
            distance += geocode.getDistance();
            beers.addAll(geocode.getBeers());
            System.out.println(geocode.toString());
        }

        System.out.println("Total distance traveled: " + distance + "\n\n");

        System.out.println(String.format("Collected %d beer types:", beers.size()));
        for (Beer beer : beers) {
            System.out.println(beer.toString());
        }
    }
}
