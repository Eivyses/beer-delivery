package com.task.delivery.utils;

import com.task.delivery.dto.BeerDto;
import com.task.delivery.dto.GeocodeDto;

import java.util.ArrayList;
import java.util.List;

public class PrintUtils {

    public static void printResult(List<GeocodeDto> results) {
        List<BeerDto> beerDtos = new ArrayList<>();
        System.out.println(String.format("Found %d beer factories:", results.size() - 2));

        int distance = 0;
        for (GeocodeDto geocodeDto : results) {
            distance += geocodeDto.getDistance();
            beerDtos.addAll(geocodeDto.getBeerDtos());
            System.out.println(geocodeDto.toString());
        }

        System.out.println("Total distance traveled: " + distance + "\n\n");

        System.out.println(String.format("Collected %d beer types:", beerDtos.size()));
        for (BeerDto beerDto : beerDtos) {
            System.out.println(beerDto.toString());
        }
    }
}
