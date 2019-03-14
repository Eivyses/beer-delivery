package com.task.delivery.utils;

import com.task.delivery.dto.GeocodeDto;
import com.task.delivery.entity.Geocode;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtils {

    public static List<GeocodeDto> getGeocodes(GeocodeDto startGeo, int searchDiameter) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Geocode> dbGeo = session.createQuery("from Geocode", Geocode.class).list();

            List<GeocodeDto> geocodeDtos = dbGeo.stream()
                    .filter(f -> startGeo.distanceToLocationInKm(f.getLatitude(), f.getLongitude()) <= searchDiameter / 2)
                    .map(g -> g.toDto(startGeo))
                    .collect(Collectors.toList());
            return geocodeDtos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
