package com.task.delivery.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeocodeDtoTest {

    private static final GeocodeDto GEOCODE = new GeocodeDto(1, 51.742503, 19.432956, 0);
    private static final int NEW_DISTANCE = 226;

    @Test
    public void calculateDistance_distanceToOther() {
        GeocodeDto toGeocodeDto = new GeocodeDto(2, 49.96220016, 20.60029984, 0);
        int distance = GEOCODE.distanceToOther(toGeocodeDto);
        assertEquals(NEW_DISTANCE, distance);
    }

    @Test
    public void updateDistance_recalculateDistance() {
        GeocodeDto toGeocodeDto = new GeocodeDto(2, 49.96220016, 20.60029984, 0);
        GEOCODE.recalculateDistance(toGeocodeDto);
        assertEquals(NEW_DISTANCE, GEOCODE.getDistance());
    }

    @Test
    public void calculateDistance_distanceToLocationInKm() {
        int distance = GEOCODE.distanceToLocationInKm(20.60029984, 49.96220016);
        assertEquals(NEW_DISTANCE, distance);
    }
}