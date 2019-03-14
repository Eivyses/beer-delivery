package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;
import com.task.delivery.optimization.models.BestElementContainer;
import com.task.delivery.utils.MoveDirectionUtils;

import java.util.*;

import static com.task.delivery.App.TOTAL_DISTANCE;

public class AdvancedOptimization extends Optimizer {

    /*
     * Splits map into quarters and finds which quarter has the most types of beers
     * After that, goes to that particular quarter while visiting close factories that have a lot of beer types
     * After going to factory recalculates everything
     */
    @Override
    protected List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<GeocodeDto> results = new ArrayList<>();

        while (startGeo.getDistance() <= remainingDistance) {
            int singleZoneRadius = (remainingDistance / 2) / 4;
            BestElementContainer bestElement = MoveDirectionUtils.findBestDirection(geocodeDtos, remainingDistance, singleZoneRadius, startGeo);

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
                if (MoveDirectionUtils.geocodeMathcesBestQuarter(bestElement.getBestString(), startGeo, geocodeDto)) {
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
}
