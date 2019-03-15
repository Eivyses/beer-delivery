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

    private static final double LAT_DEFAULT4 = 51.981708;
    private static final double LON_DEFAULT4 = 22.637236;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double lat = LAT_DEFAULT;
        double lon = LON_DEFAULT;
        boolean optimize = false;

        GeocodeDto startGeo = new GeocodeDto(0, lon, lat, 0);
        List<GeocodeDto> geocodeDtos = DatabaseUtils.getGeocodes(startGeo, TOTAL_DISTANCE);

        Optimizer optimizer = optimize ? new AdvancedOptimization() : new SimpleOptimizer();
        List<GeocodeDto> results = optimizer.optimize(geocodeDtos, startGeo);
        PrintUtils.printResult(results);

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Program took: " + elapsed + " ms");
    }
}
