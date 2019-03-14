package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.task.delivery.App.TOTAL_DISTANCE;

public class SimpleOptimizer extends Optimizer {

    private static void recalculateDistances(List<GeocodeDto> geocodeDtos, GeocodeDto currentGeocodeDto) {
        geocodeDtos.forEach(geocode -> geocode.recalculateDistance(currentGeocodeDto));
    }

    @Override
    public List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<GeocodeDto> results = new ArrayList<>();

        int i = 0;

        while (i < geocodeDtos.size() && geocodeDtos.size() > 0) {
            geocodeDtos.sort(Comparator.comparingInt(GeocodeDto::getDistance));
            GeocodeDto geocodeDto = geocodeDtos.get(i);
            startGeo.setDistance(startGeo.distanceToOther(geocodeDto));
            if (geocodeDto.getDistance() <= remainingDistance - startGeo.getDistance()) {
                results.add(new GeocodeDto(geocodeDto));
                remainingDistance -= geocodeDto.getDistance();
                geocodeDtos.remove(i);
                recalculateDistances(geocodeDtos, geocodeDto);
                i = 0;
            } else {
                i++;
            }

        }

        GeocodeDto endGeo = new GeocodeDto(startGeo);
        GeocodeDto last = results.get(results.size() - 1);
        endGeo.setDistance(endGeo.distanceToOther(last));
        results.add(endGeo);
        return results;
    }
}
