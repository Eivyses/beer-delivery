package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;
import com.task.delivery.optimization.models.BestElementContainer;

import java.util.*;

import static com.task.delivery.App.TOTAL_DISTANCE;

public class AdvancedOptimization extends Optimizer {

    private static final String LEFT = "l";
    private static final String RIGHT = "r";
    private static final String TOP = "t";
    private static final String BOTTOM = "b";

    private static double calculateEfficiencyCoefficient(int beerCount, int zone) {
        return (0.5 * beerCount) / Math.pow(zone + 1, 4);
    }

    /*
     * Splits map into quarters and finds which quarter has the most types of beers
     * After that, goes to that particular quarter while visiting closest biggest factory
     * After going to factory, again recalculates everything
     */
    @Override
    protected List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<GeocodeDto> results = new ArrayList<>();

        while (startGeo.getDistance() <= remainingDistance) {
            int singleZoneRadius = (remainingDistance / 2) / 4;
            BestElementContainer bestElement = findBestDirection(geocodeDtos, remainingDistance, singleZoneRadius, startGeo);

            geocodeDtos.sort(Comparator.comparingInt(GeocodeDto::getDistance));
            for (GeocodeDto geocodeDto : geocodeDtos) {
                startGeo.recalculateDistance(geocodeDto);
                if (geocodeDto.beerCount() <= 0 || geocodeDto.getDistance() > remainingDistance - startGeo.getDistance()) {
                    continue;
                }
                int zoneStart = singleZoneRadius * bestElement.getBestZone();
                int zoneTo = singleZoneRadius * (bestElement.getBestZone() + 1);
                if (geocodeDto.getDistance() > zoneTo || geocodeDto.getDistance() < zoneStart) {
                    continue;
                }
                if (geoMathcesBestQuarter(bestElement.getBestString(), startGeo, geocodeDto)) {
                    results.add(new GeocodeDto(geocodeDto));
                    remainingDistance -= geocodeDto.getDistance();
                    geocodeDtos.remove(geocodeDto);
                    recalculateDistances(geocodeDtos, geocodeDto);
                    break;
                }
            }
        }
        return results;
    }

    private BestElementContainer findBestDirection(List<GeocodeDto> geocodeDtos, int remainingDistance, int singleZoneRadius, GeocodeDto startGeo) {
        BestElementContainer bestElement = new BestElementContainer();
        /*
         * groups models into regions and 4 zones
         * regions is simple x, y axis graph, where current location is the center of it
         * zone is a remaining radius split into 4.
         */
        Map<String, Integer> groupedDataMap = new HashMap<>();

        for (GeocodeDto geocodeDto : geocodeDtos) {
            startGeo.recalculateDistance(geocodeDto);
            if (geocodeDto.getDistance() > remainingDistance - startGeo.getDistance()) {
                continue;
            }

            int currentZone = geocodeDto.getDistance() / singleZoneRadius;
            if (currentZone >= 4) {
                currentZone = 3;
            }
            String x = startGeo.getLon() > geocodeDto.getLon() ? LEFT : RIGHT;
            String y = startGeo.getLat() > geocodeDto.getLat() ? BOTTOM : TOP;
            Integer beerCount = groupedDataMap.get(x + y + currentZone);

            if (beerCount == null) {
                beerCount = 0;
            }
            int newBeerCount = beerCount + geocodeDto.beerCount();
            double koef = calculateEfficiencyCoefficient(newBeerCount, currentZone);
            if (koef > bestElement.getBestDouble()) {
                bestElement.setBestDouble(koef);
                bestElement.setBestString(x + y + currentZone);
                bestElement.setBestZone(currentZone);
            }
            groupedDataMap.put(x + y + currentZone, newBeerCount);
        }
        //            System.out.println("best direction: " + bestString);
        //            fullMap.forEach((k, v) -> System.out.println(String.format("Quarter zone %s has: %d beers", k, v)));
        //            System.out.println();
        return bestElement;
    }

    private boolean geoMathcesBestQuarter(String betQuarterString, GeocodeDto startGeo, GeocodeDto currentGeo) {
        if (betQuarterString.contains(RIGHT + BOTTOM)) {
            if (startGeo.getLat() > currentGeo.getLat() && startGeo.getLon() < currentGeo.getLon()) {
                return true;
            }
        } else if (betQuarterString.contains(RIGHT + TOP)) {
            if (startGeo.getLat() < currentGeo.getLat() && startGeo.getLon() < currentGeo.getLon()) {
                return true;
            }
        } else if (betQuarterString.contains(LEFT + BOTTOM)) {
            if (startGeo.getLat() > currentGeo.getLat() && startGeo.getLon() > currentGeo.getLon()) {
                return true;
            }
        } else if (betQuarterString.contains(LEFT + TOP)) {
            if (startGeo.getLat() < currentGeo.getLat() && startGeo.getLon() > currentGeo.getLon()) {
                return true;
            }
        }
        return false;
    }
}
