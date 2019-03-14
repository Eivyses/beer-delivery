package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;


public class OptimizerTest {

    private List<GeocodeDto> geocodeDtos;
    private GeocodeDto startGeocode;

    @Before
    public void initialize() {
        startGeocode = new GeocodeDto(0, 51.742503, 19.432956, 0);

        geocodeDtos = new ArrayList<>();
        geocodeDtos.add(new GeocodeDto(5, 49.45675, 126.12549, 0));
        geocodeDtos.add(new GeocodeDto(10, 32.1359, 19.15915, 0));
        geocodeDtos.add(new GeocodeDto(20, -25.1234, 50.01564, 0));
    }

    @Test
    public void recalculatesDistances_recalculateDistances() {
        Optimizer optimizer = new SimpleOptimizer();
        optimizer.recalculateDistances(geocodeDtos, startGeocode);
        geocodeDtos.forEach(g -> assertNotEquals(0, g.getDistance()));
    }
}