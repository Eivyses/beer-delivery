package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;

import java.util.*;

import static com.task.delivery.App.TOTAL_DISTANCE;

public class AdvancedOptimization extends Optimizer {

    @Override
    protected List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<GeocodeDto> results = new ArrayList<>();

        while (startGeo.getDistance() <= remainingDistance) {
            Map<String, List<Integer>> fullMap = new HashMap<>();

            int zoneSize = (remainingDistance / 2) / 4;
            String bestString = "";
            int bestInt = 0;

            for (GeocodeDto geocodeDto : geocodeDtos) {
                startGeo.recalculateDistance(geocodeDto);
                if (geocodeDto.getDistance() > remainingDistance - startGeo.getDistance()) {
                    continue;
                }
                int zone = geocodeDto.getDistance() / zoneSize;
                if (zone >= 4) {
                    zone = 3;
                }

                String x = startGeo.getLon() > geocodeDto.getLon() ? "l" : "r";
                String y = startGeo.getLat() > geocodeDto.getLat() ? "b" : "t";

                List<Integer> zoneInfo = fullMap.get(x + y + zone);
                if (zoneInfo == null) {
                    zoneInfo = new ArrayList<>();
                    zoneInfo.add(0);
                    zoneInfo.add(0);
                }
                int value1 = zoneInfo.get(0) + geocodeDto.beerCount();
                if (value1 > bestInt) {
                    bestInt = value1;
                    bestString = x + y + zone;
                }
                zoneInfo.set(0, zoneInfo.get(0) + geocodeDto.beerCount());
                zoneInfo.set(1, zoneInfo.get(0) + 1);
                fullMap.put(x + y + zone, zoneInfo);
            }

//            System.out.println("best direction: " + bestString);
            geocodeDtos.sort(Comparator.comparingInt(GeocodeDto::getDistance));
            for (GeocodeDto geocodeDto : geocodeDtos) {
                startGeo.recalculateDistance(geocodeDto);
                if (geocodeDto.getDistance() > remainingDistance - startGeo.getDistance()) {
                    continue;
                }
                boolean match = false;
                if (geocodeDto.beerCount() <= 0) {
                    continue;
                }
                if (bestString.contains("rb")) {
                    if (startGeo.getLat() > geocodeDto.getLat() && startGeo.getLon() < geocodeDto.getLon()) {
                        match = true;
                    }
                } else if (bestString.contains("rt")) {
                    if (startGeo.getLat() < geocodeDto.getLat() && startGeo.getLon() < geocodeDto.getLon()) {
                        match = true;
                    }
                } else if (bestString.contains("lb")) {
                    if (startGeo.getLat() > geocodeDto.getLat() && startGeo.getLon() > geocodeDto.getLon()) {
                        match = true;
                    }
                } else if (bestString.contains("lt")) {
                    if (startGeo.getLat() < geocodeDto.getLat() && startGeo.getLon() > geocodeDto.getLon()) {
                        match = true;
                    }
                }
                if (match) {
                    startGeo.recalculateDistance(geocodeDto);
                    results.add(new GeocodeDto(geocodeDto));
                    remainingDistance -= geocodeDto.getDistance();
                    geocodeDtos.remove(geocodeDto);
                    recalculateDistances(geocodeDtos, geocodeDto);
                    break;
                }
            }

//            fullMap.forEach((k, v) -> System.out.println(String.format("Quarter zone %s has: %d beers in %d factories", k, v.get(0), v.get(1))));
//            System.out.println();
        }
        GeocodeDto starting = new GeocodeDto(startGeo.getBreweryId(), startGeo.getLon(), startGeo.getLat(), 0);
        results.add(0, starting);
        GeocodeDto endGeo = new GeocodeDto(startGeo);
        GeocodeDto last = results.get(results.size() - 1);
        endGeo.recalculateDistance(last);
        results.add(endGeo);

        return results;
    }

}
