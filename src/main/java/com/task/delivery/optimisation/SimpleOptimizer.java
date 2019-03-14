package com.task.delivery.optimisation;

import com.task.delivery.models.Geocode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.task.delivery.App.TOTAL_DISTANCE;

public class SimpleOptimizer extends Optimizer {

    public SimpleOptimizer(Geocode startGeo) {
        super(startGeo);
    }

    private static void recalculateDistances(List<Geocode> geocodes, Geocode currentGeocode) {
        geocodes.forEach(geocode -> geocode.recalculateDistance(currentGeocode));
    }

    @Override
    public List<Geocode> calculateOptimizationResults(List<Geocode> geocodes, Geocode startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<Geocode> results = new ArrayList<>();

        int i = 0;

        while (i < geocodes.size() && geocodes.size() > 0) {
            geocodes.sort(Comparator.comparingInt(Geocode::getDistance));
            Geocode geocode = geocodes.get(i);
            startGeo.setDistance(startGeo.distanceToOther(geocode));
            if (geocode.getDistance() <= remainingDistance - startGeo.getDistance()) {
                results.add(new Geocode(geocode));
                remainingDistance -= geocode.getDistance();
                geocodes.remove(i);
                recalculateDistances(geocodes, geocode);
                i = 0;
            } else {
                i++;
            }

        }

        Geocode endGeo = new Geocode(startGeo);
        Geocode last = results.get(results.size() - 1);
        endGeo.setDistance(endGeo.distanceToOther(last));
        results.add(endGeo);
        return results;
    }
}
