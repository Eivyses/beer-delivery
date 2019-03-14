package com.task.delivery.entity;

import com.task.delivery.dto.BeerDto;
import com.task.delivery.dto.GeocodeDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "geocodes")
public class Geocode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id", foreignKey = @ForeignKey(name = "geocode_brewery"))
    private Brewery brewery;

    private double latitude;
    private double longitude;

    @Column(columnDefinition = "TEXT")
    private String accuracy;

    public int getId() {
        return id;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public GeocodeDto toDto(GeocodeDto startGeocode) {
        List<BeerDto> beerDtos = brewery.getBeers().stream().map(Beer::toDto).collect(Collectors.toList());
        GeocodeDto geocodeDto = new GeocodeDto(brewery.getId(), longitude, latitude, 0, brewery.getName(), beerDtos);
        geocodeDto.recalculateDistance(startGeocode);
        return geocodeDto;
    }
}
