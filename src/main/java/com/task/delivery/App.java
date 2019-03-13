package com.task.delivery;

import com.task.delivery.models.Geocode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    private static String USER_DIR = System.getProperty("user.dir") + "\\src\\main\\java\\com\\task\\delivery";
    private static String COMMA_SEPERATOR = ",";
    private static int TOTAL_DISTANCE = 2000;

    public static void main(String[] args) {
        int remainingDistance = TOTAL_DISTANCE;
        long start = System.currentTimeMillis();
        double lat = 51.742503;
        double lon = 19.432956;
        if (args.length == 2) {
            lat = Double.parseDouble(args[0]);
            lon = Double.parseDouble(args[1]);
        } else if (args.length != 0) {
            System.out.println("Wrong amount of arguments specified");
            return;
        }

        List<Geocode> geocodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DIR + "\\data\\geocodes.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_SEPERATOR);
                Geocode geocode = Geocode.buildFromCsv(values, lat, lon);
                if (geocode.getDistance() <= TOTAL_DISTANCE / 2) {
                    geocodes.add(geocode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        geocodes.sort(Comparator.comparingInt(Geocode::getDistance));
        List<Geocode> results = new ArrayList<>();

        for (Geocode geocode : geocodes) {
            if (geocode.getDistance() <= remainingDistance / 2) {
                results.add(geocode);
                remainingDistance -= geocode.getDistance();
            }
        }

        for (Geocode geocode : results) {
            System.out.println(geocode.toString());
        }
        System.out.println("Total distance: " + (TOTAL_DISTANCE - remainingDistance));
        System.out.println("Unused distance: " + remainingDistance);
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Program took: " + elapsed + " ms");
    }

}
