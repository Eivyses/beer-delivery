package com.task.delivery;

import com.task.delivery.models.Beer;
import com.task.delivery.models.Geocode;
import com.task.delivery.utils.DistanceUtils;
import com.task.delivery.utils.FileUtils;
import com.task.delivery.utils.PrintUtils;

import java.util.*;

public class App {
    private static String USER_DIR = System.getProperty("user.dir") + "\\src\\main\\java\\com\\task\\delivery";
    private static String COMMA_SEPERATOR = ",";
    private static int TOTAL_DISTANCE = 2000;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double lat = 51.742503;
        double lon = 19.432956;
        Geocode startGeo = new Geocode(0, lon, lat, 0);
        List<Geocode> geocodes = new ArrayList<>();

        if (args.length == 2) {
            lat = Double.parseDouble(args[0]);
            lon = Double.parseDouble(args[1]);
        } else if (args.length != 0) {
            System.out.println("Wrong amount of arguments specified");
            return;
        }

        geocodes.add(startGeo);
        initGeocodesData(geocodes, lat, lon);
        List<Beer> beers = initBeersData();
        Map<Long, String> factoriesNames = initFactoriesNames();

        List<Geocode> results = calculateSimple(geocodes, startGeo);
        List<Beer> beerResult = new ArrayList<>();
        for (Geocode result : results) {
            for (Beer beer : beers) {
                if (result.getBreweryId() == beer.getBreweryId() && !beerResult.contains(beer)) {
                    beerResult.add(beer);
                }
            }
        }

        fillFactoryNames(results, factoriesNames);
        PrintUtils.printResult(results, beerResult);

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Program took: " + elapsed + " ms");
    }

    private static List<Geocode> calculateSimple(List<Geocode> geocodes, Geocode startGeo) {
        int remainingDistance = TOTAL_DISTANCE;
        List<Geocode> results = new ArrayList<>();

        geocodes.sort(Comparator.comparingInt(Geocode::getDistance));
        for (Geocode geocode : geocodes) {
            // if destination distance is less or equal to remaining distance that doesn't include going back to start
            if (geocode.getDistance() <= remainingDistance - startGeo.getDistance()) {
                results.add(new Geocode(geocode));
                remainingDistance -= geocode.getDistance();
                recalculateDistances(geocodes, geocode);
            }
        }

        Geocode endGeo = new Geocode(startGeo);
        endGeo.setDistance(DistanceUtils.distance(endGeo, results.get(results.size() - 1)));
        results.add(endGeo);
        return results;
    }

    private static void initGeocodesData(List<Geocode> geocodes, double lat, double lon) {
        List<String[]> fileData = FileUtils.readFile(USER_DIR + "\\data\\geocodes.csv", COMMA_SEPERATOR, true);
        for (String[] values : fileData) {
            Geocode geocode = Geocode.buildLocationFromCsv(values, lat, lon);
            if (geocode.getDistance() <= TOTAL_DISTANCE / 2) {
                geocodes.add(geocode);
            }
        }
    }

    private static List<Beer> initBeersData() {
        List<Beer> beers = new ArrayList<>();
        List<String[]> fileData = FileUtils.readFile(USER_DIR + "\\data\\beers.csv", COMMA_SEPERATOR, true);
        for (String[] values : fileData) {
            try {
                Integer.parseInt(values[0]);
                beers.add(Beer.buildBeer(values));
            } catch (NumberFormatException ignored) {

            }
        }
        return beers;
    }

    private static Map<Long, String> initFactoriesNames() {
        Map<Long, String> names = new HashMap<>();
        List<String[]> fileData = FileUtils.readFile(USER_DIR + "\\data\\breweries.csv", COMMA_SEPERATOR, true);
        for (String[] values : fileData) {
            try {
                Long id = Long.parseLong(values[0]);
                String name = values[1];
                names.put(id, name);
            }
            catch (NumberFormatException ignored){

            }
        }
        return names;
    }

    private static void fillFactoryNames(List<Geocode> geocodes, Map<Long, String> names) {
        for (Geocode geocode : geocodes) {
            String name = names.get(geocode.getBreweryId());
            geocode.setName(name == null ? "HOME" : name);
        }
    }

    private static void recalculateDistances(List<Geocode> geocodes, Geocode currentGeocode) {
        for (Geocode geocode : geocodes) {
            geocode.setDistance(DistanceUtils.distance(currentGeocode, geocode));
        }
    }

}
