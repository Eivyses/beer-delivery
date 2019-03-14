package com.task.delivery.entity;

import com.task.delivery.dto.GeocodeDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeocodeTest {

    private static final GeocodeDto GEOCODE = new GeocodeDto(1, 51.742503, 19.432956, 0);
    private static final double LATITUDE = 5.45684;
    private static final double LONGITUDE = 13.1549;
    private static final String BREWERY_NAME = "TEST_BREWERY";
    private static final String BEER_NAME1 = "FIRST_BEER";
    private static final String BEER_NAME2 = "SECOND_BEER";

    @Test
    public void createsDto_toDto() {
        Geocode geocode = new Geocode();
        geocode.setLatitude(LATITUDE);
        geocode.setLongitude(LONGITUDE);

        Brewery brewery = new Brewery();
        brewery.setName(BREWERY_NAME);

        Beer beer1 = new Beer();
        beer1.setName(BEER_NAME1);
        Beer beer2 = new Beer();
        beer2.setName(BEER_NAME2);
        brewery.getBeers().add(beer1);
        brewery.getBeers().add(beer2);
        geocode.setBrewery(brewery);

        GeocodeDto geocodeDto = geocode.toDto(GEOCODE);

        assertEquals(LATITUDE, geocodeDto.getLat(), 0);
        assertEquals(LONGITUDE, geocodeDto.getLon(), 0);
        assertEquals(BREWERY_NAME, geocodeDto.getName());

        assertEquals(geocode.getBrewery().getBeers().size(), geocodeDto.getBeerDtos().size());
        assertEquals(BEER_NAME1, geocodeDto.getBeerDtos().get(0).getName());
        assertEquals(BEER_NAME2, geocodeDto.getBeerDtos().get(1).getName());
    }
}