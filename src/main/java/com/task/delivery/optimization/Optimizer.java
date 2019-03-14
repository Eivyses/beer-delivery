package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;

import java.util.List;

public abstract class Optimizer {

    public List<GeocodeDto> optimize(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        List<GeocodeDto> results = calculateOptimizationResults(geocodeDtos, startGeo);
        addHomeAddressToResult(results, startGeo);
        return results;
    }

    protected abstract List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo);

    void recalculateDistances(List<GeocodeDto> geocodeDtos, GeocodeDto currentGeocodeDto) {
        geocodeDtos.forEach(geocode -> geocode.recalculateDistance(currentGeocodeDto));
    }

    private void addHomeAddressToResult(List<GeocodeDto> results, GeocodeDto startGeo) {
        GeocodeDto starting = new GeocodeDto(startGeo.getBreweryId(), startGeo.getLon(), startGeo.getLat(), 0);
        results.add(0, starting);
        GeocodeDto endGeo = new GeocodeDto(startGeo);
        GeocodeDto last = results.get(results.size() - 1);
        endGeo.recalculateDistance(last);
        results.add(endGeo);
    }
}
