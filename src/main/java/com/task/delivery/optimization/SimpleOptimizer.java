package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.task.delivery.App.TOTAL_DISTANCE;

public class SimpleOptimizer extends Optimizer {

    /*
     * Goes to all the closest factories
     */
    @Override
    public List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<GeocodeDto> results = new ArrayList<>();

        int i = 0;
        geocodeDtos.sort(Comparator.comparingInt(GeocodeDto::getDistance));
        while (i < geocodeDtos.size() && geocodeDtos.size() > 0) {
            GeocodeDto geocodeDto = geocodeDtos.get(i);
            startGeo.setDistance(startGeo.distanceToOther(geocodeDto));
            if (geocodeDto.getDistance() <= remainingDistance - startGeo.getDistance()) {
                results.add(new GeocodeDto(geocodeDto));
                remainingDistance -= geocodeDto.getDistance();
                geocodeDtos.remove(i);
                recalculateDistances(geocodeDtos, geocodeDto);
                geocodeDtos.sort(Comparator.comparingInt(GeocodeDto::getDistance));
                i = 0;
            } else {
                i++;
            }

        }
        return results;
    }
}
