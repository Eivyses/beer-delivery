package com.task.delivery.utils;

import com.task.delivery.dto.BeerDto;
import com.task.delivery.dto.GeocodeDto;

import java.util.ArrayList;
import java.util.List;

public class PrintUtils {

    public static void printResult(List<GeocodeDto> results) {
        List<BeerDto> beerDtos = new ArrayList<>();
        int factoriesCount = results.size() > 0 ? results.size() - 2 : 0;
        System.out.println(String.format("Found %d beer factories:", factoriesCount));

        int distance = 0;
        for (GeocodeDto geocodeDto : results) {
            distance += geocodeDto.getDistance();
            beerDtos.addAll(geocodeDto.getBeerDtos());
            System.out.println(geocodeDto.toString());
        }

        System.out.println("Total distance traveled: " + distance);

        System.out.println(String.format("Collected %d beer types:", beerDtos.size()));
        for (BeerDto beerDto : beerDtos) {
            System.out.println(beerDto.toString());
        }
    }
}
