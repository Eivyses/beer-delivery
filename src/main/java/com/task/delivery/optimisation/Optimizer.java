package com.task.delivery.optimisation;

import com.task.delivery.models.Beer;
import com.task.delivery.models.Geocode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Optimizer {

    private List<Geocode> geocodes;
    private Geocode startGeo;
    private List<Beer> beers;
    private Map<Long, String> breweriesNames;

    Optimizer(Geocode startGeo) {
        geocodes = new ArrayList<>();
        this.startGeo = startGeo;
        beers = new ArrayList<>();
        breweriesNames = new HashMap<>();
    }

    public List<Geocode> optimize() {
        geocodes.add(startGeo);
        return calculateOptimizationResults(geocodes, startGeo);
    }

    public void collectData(List<Geocode> geocodes, List<Beer> beers, Map<Long, String> breweriesNames) {
        this.geocodes = geocodes;
        this.beers = beers;
        this.breweriesNames = breweriesNames;
        fillBeersForGeocodes(this.geocodes, this.beers);
        fillBreweriesNamesForGeocodes(this.geocodes, this.breweriesNames);
    }

    protected abstract List<Geocode> calculateOptimizationResults(List<Geocode> geocodes, Geocode startGeo);

    private void fillBreweriesNamesForGeocodes(List<Geocode> geocodes, Map<Long, String> names) {
        for (Geocode geocode : geocodes) {
            String name = names.get(geocode.getBreweryId());
            geocode.setName(name == null ? "HOME" : name);
        }
    }

    private void fillBeersForGeocodes(List<Geocode> geocodes, List<Beer> beers) {
        for (Geocode geocode : geocodes) {
            for (Beer beer : beers) {
                if (geocode.getBreweryId() == beer.getBreweryId() && !geocode.getBeers().contains(beer)) {
                    geocode.getBeers().add(beer);
                }
            }
        }
    }
}
