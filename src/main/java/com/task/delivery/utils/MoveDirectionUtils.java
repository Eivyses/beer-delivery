package com.task.delivery.utils;

import com.task.delivery.dto.GeocodeDto;
import com.task.delivery.optimization.models.BestElementContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveDirectionUtils {

    public static final String LEFT = "l";
    public static final String RIGHT = "r";
    public static final String TOP = "t";
    public static final String BOTTOM = "b";

    public static BestElementContainer findBestDirection(List<GeocodeDto> geocodeDtos, int remainingDistance, int singleZoneRadius, GeocodeDto startGeo) {
        BestElementContainer bestElement = new BestElementContainer();
        /*
         * groups models into regions and 4 zones
         * region is simple x, y axis graph, where current location is the center of it
         * zone is a remaining distance radius split into 4 smaller radius.
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
            double coef = calculateEfficiencyCoefficient(newBeerCount, currentZone);
            if (coef > bestElement.getBestCoef()) {
                bestElement.setBestCoef(coef);
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

    public static boolean geocodeMathcesBestQuarter(String betQuarterString, GeocodeDto startGeo, GeocodeDto currentGeo) {
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

    private static double calculateEfficiencyCoefficient(int beerCount, int zone) {
        return (0.5 * beerCount) / Math.pow(zone + 1, 4);
    }
}
