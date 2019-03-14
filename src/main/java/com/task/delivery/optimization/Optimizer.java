package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;

import java.util.List;

public abstract class Optimizer {

    public List<GeocodeDto> optimize(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        return calculateOptimizationResults(geocodeDtos, startGeo);
    }

    protected abstract List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo);

    void recalculateDistances(List<GeocodeDto> geocodeDtos, GeocodeDto currentGeocodeDto) {
        geocodeDtos.forEach(geocode -> geocode.recalculateDistance(currentGeocodeDto));
    }
}
