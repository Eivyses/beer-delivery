package com.task.delivery;

import com.task.delivery.dto.GeocodeDto;
import com.task.delivery.optimization.AdvancedOptimization;
import com.task.delivery.optimization.Optimizer;
import com.task.delivery.optimization.SimpleOptimizer;
import com.task.delivery.utils.DatabaseUtils;
import com.task.delivery.utils.PrintUtils;

import java.util.List;

public class App {
    public static final int TOTAL_DISTANCE = 2000;
    private static final double LAT_DEFAULT = 51.742503;
    private static final double LON_DEFAULT = 19.432956;

    private static final double LAT_DEFAULT2 = 50.929115;
    private static final double LON_DEFAULT2 = 18.438955;

    private static final double LAT_DEFAULT3 = 52.559864;
    private static final double LON_DEFAULT3 = 16.553510;
    private static final String OPTIMIZE = "optimize";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double lat = LAT_DEFAULT3;
        double lon = LON_DEFAULT3;
        boolean optimize = false;


        if (args.length == 2) {
            lat = Double.parseDouble(args[0]);
            lon = Double.parseDouble(args[1]);
        } else if (args.length == 1) {
            System.out.println("Wrong amount of arguments specified");
            return;
        }
        if (args.length == 3 && args[2].equals(OPTIMIZE)) {
            optimize = true;
        }

        GeocodeDto startGeo = new GeocodeDto(0, lon, lat, 0);
        List<GeocodeDto> geocodeDtos = DatabaseUtils.getGeocodes(startGeo, TOTAL_DISTANCE);

        System.out.println("SIMPLE");
        Optimizer optimizer = new SimpleOptimizer();
        List<GeocodeDto> results = optimizer.optimize(geocodeDtos, startGeo);
        PrintUtils.printResult(results);

        System.out.println("\n\nADVANCED");
        startGeo = new GeocodeDto(0, lon, lat, 0);
        geocodeDtos = DatabaseUtils.getGeocodes(startGeo, TOTAL_DISTANCE);
        optimizer = new AdvancedOptimization();
        results = optimizer.optimize(geocodeDtos, startGeo);
        PrintUtils.printResult(results);

//        if (geocodeDtos != null) {
//            Optimizer optimizer = optimize ? new AdvancedOptimization() : new SimpleOptimizer();
//            List<GeocodeDto> results = optimizer.optimize(geocodeDtos, startGeo);
//            PrintUtils.printResult(results);
//        } else {
//            System.out.println("Failed to read data from database");
//        }

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Program took: " + elapsed + " ms");
    }
}
