package com.task.delivery.utils;

import com.task.delivery.dto.BeerDto;
import com.task.delivery.dto.GeocodeDto;
import com.task.delivery.optimization.models.BestElementContainer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.task.delivery.utils.MoveDirectionUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveDirectionUtilsTest {

    private static final int REMAINING_DISTANCE = 2000;
    private static final int SINGLE_ZONE_RADIUS = 250;

    private GeocodeDto startGeo;

    @Before
    public void initialize() {
        startGeo = new GeocodeDto(0, 0, 0, 0);
    }

    @Test
    public void bestDirectionRightTop_findBestDirection() {
        List<GeocodeDto> geocodeDtos = new ArrayList<>();
        geocodeDtos.add(new GeocodeDto(0, 1, 1, 0, "test1", generateBeersList(3)));
        geocodeDtos.add(new GeocodeDto(0, 2, 2, 0, "test1", generateBeersList(10)));
        geocodeDtos.add(new GeocodeDto(0, -5, -1, 0, "test1", generateBeersList(13)));
        geocodeDtos.add(new GeocodeDto(0, -4, 10, 0, "test1", generateBeersList(3)));
        geocodeDtos.add(new GeocodeDto(0, 11, 8, 0, "test1", generateBeersList(3)));
        BestElementContainer container = MoveDirectionUtils.findBestDirection(geocodeDtos, REMAINING_DISTANCE, SINGLE_ZONE_RADIUS, startGeo);
        assertEquals(RIGHT + TOP, container.getBestString().substring(0, 2));
    }

    @Test
    public void bestDirectionLeftBot_findBestDirection() {
        List<GeocodeDto> geocodeDtos = new ArrayList<>();
        geocodeDtos.add(new GeocodeDto(0, 1, 1, 0, "test1", generateBeersList(3)));
        geocodeDtos.add(new GeocodeDto(0, 2, 2, 0, "test1", generateBeersList(1)));
        geocodeDtos.add(new GeocodeDto(0, -5, -1, 0, "test1", generateBeersList(13)));
        geocodeDtos.add(new GeocodeDto(0, -4, 10, 0, "test1", generateBeersList(3)));
        geocodeDtos.add(new GeocodeDto(0, 11, 8, 0, "test1", generateBeersList(2)));
        BestElementContainer container = MoveDirectionUtils.findBestDirection(geocodeDtos, REMAINING_DISTANCE, SINGLE_ZONE_RADIUS, startGeo);
        assertEquals(LEFT + BOTTOM, container.getBestString().substring(0, 2));
    }

    @Test
    public void rightTopQuarter_geocodeMathcesBestQuarter() {
        String betQuarterString = RIGHT + TOP;
        GeocodeDto targetGeo = new GeocodeDto(0, 20, 30, 0);
        assertTrue(MoveDirectionUtils.geocodeMathcesBestQuarter(betQuarterString, startGeo, targetGeo));
    }

    @Test
    public void rightBotQuarter_geocodeMathcesBestQuarter() {
        String betQuarterString = RIGHT + BOTTOM;
        GeocodeDto targetGeo = new GeocodeDto(0, 20, -20, 0);
        assertTrue(MoveDirectionUtils.geocodeMathcesBestQuarter(betQuarterString, startGeo, targetGeo));
    }

    @Test
    public void leftTopQuarter_geocodeMathcesBestQuarter() {
        String betQuarterString = LEFT + TOP;
        GeocodeDto targetGeo = new GeocodeDto(0, -1, 30, 0);
        assertTrue(MoveDirectionUtils.geocodeMathcesBestQuarter(betQuarterString, startGeo, targetGeo));
    }

    @Test
    public void leftBotQuarter_geocodeMathcesBestQuarter() {
        String betQuarterString = LEFT + BOTTOM;
        GeocodeDto startGeo = new GeocodeDto(0, 0, 0, 0);
        GeocodeDto targetGeo = new GeocodeDto(0, -10, -50, 0);
        assertTrue(MoveDirectionUtils.geocodeMathcesBestQuarter(betQuarterString, startGeo, targetGeo));
    }

    private List<BeerDto> generateBeersList(int size) {
        List<BeerDto> beerDtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            beerDtos.add(new BeerDto("Beer " + i));
        }
        return beerDtos;
    }
}