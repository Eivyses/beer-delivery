package com.task.delivery;

import com.task.delivery.models.Beer;
import com.task.delivery.models.Geocode;
import com.task.delivery.optimisation.Optimizer;
import com.task.delivery.optimisation.SimpleOptimizer;
import com.task.delivery.reader.DataReader;
import com.task.delivery.utils.PrintUtils;

import java.util.List;
import java.util.Map;

public class App {
    public static final String USER_DIR = System.getProperty("user.dir") + "\\src\\main\\java\\com\\task\\delivery";
    public static final String COMMA_SEPARATOR = ",";
    public static final int TOTAL_DISTANCE = 2000;
    private static final double LAT_DEFAULT = 51.742503;
    private static final double LON_DEFAULT = 19.432956;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double lat = LAT_DEFAULT;
        double lon = LON_DEFAULT;

        if (args.length == 2) {
            lat = Double.parseDouble(args[0]);
            lon = Double.parseDouble(args[1]);
        } else if (args.length != 0) {
            System.out.println("Wrong amount of arguments specified");
            return;
        }
        Geocode startGeo = new Geocode(0, lon, lat, 0);

        List<Geocode> geocodes = DataReader.getGeocodesFromCsv(startGeo);
        List<Beer> beers = DataReader.getBeerDataFromCsv();
        Map<Long, String> factoriesNames = DataReader.getFactoryNamesFromCsv();

        Optimizer optimizer = new SimpleOptimizer(startGeo);
        optimizer.collectData(geocodes, beers, factoriesNames);
        List<Geocode> results = optimizer.optimize();
        PrintUtils.printResult(results);

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Program took: " + elapsed + " ms");
    }
}
