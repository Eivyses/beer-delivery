package com.task.delivery.reader;

import com.task.delivery.models.Beer;
import com.task.delivery.models.Geocode;
import com.task.delivery.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.task.delivery.App.*;

public class DataReader {

    public static Map<Long, String> getFactoryNamesFromCsv() {
        Map<Long, String> names = new HashMap<>();
        List<String[]> fileData = FileUtils.readFile(USER_DIR + "\\data\\breweries.csv", COMMA_SEPARATOR, true);
        for (String[] values : fileData) {
            try {
                Long id = Long.parseLong(values[0]);
                String name = values[1];
                names.put(id, name);
            } catch (NumberFormatException ignored) {

            }
        }
        return names;
    }

    public static List<Geocode> getGeocodesFromCsv(Geocode startGeo) {
        List<Geocode> geocodes = new ArrayList<>();
        List<String[]> fileData = FileUtils.readFile(USER_DIR + "\\data\\geocodes.csv", COMMA_SEPARATOR, true);
        for (String[] values : fileData) {
            try {
                long breweryId = Long.parseLong(values[1]);
                double lat = Double.parseDouble(values[2]);
                double lon = Double.parseDouble(values[3]);
                Geocode geocode = Geocode.of(breweryId, lon, lat, startGeo);
                if (geocode.getDistance() <= TOTAL_DISTANCE / 2) {
                    geocodes.add(geocode);
                }
            } catch (NumberFormatException ignore) {

            }

        }
        return geocodes;
    }

    public static List<Beer> getBeerDataFromCsv() {
        List<Beer> beers = new ArrayList<>();
        List<String[]> fileData = FileUtils.readFile(USER_DIR + "\\data\\beers.csv", COMMA_SEPARATOR, true);
        for (String[] values : fileData) {
            try {
                Integer.parseInt(values[0]);
                beers.add(Beer.buildBeer(values));
            } catch (NumberFormatException ignored) {

            }
        }
        return beers;
    }
}
