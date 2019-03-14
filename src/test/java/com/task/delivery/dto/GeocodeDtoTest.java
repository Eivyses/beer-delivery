package com.task.delivery.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeocodeDtoTest {

    private static final GeocodeDto GEOCODE = new GeocodeDto(1, 51.742503, 19.432956, 0);

    @Test
    public void calculateDistance_distanceToOther(){
        GeocodeDto toGeocodeDto = new GeocodeDto(2, 49.96220016, 20.60029984, 0);
        int distance = GEOCODE.distanceToOther(toGeocodeDto);
        assertEquals(226, distance);
    }
}