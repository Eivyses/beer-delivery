package com.task.delivery.utils;

public class DistanceUtils {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. Uses Haversine method as its base.
     *
     * @param lat1 start point latitude
     * @param lat2 end point latitude
     * @param lon1 start point longitude
     * @param lon2 end point longitude
     * @return Distance in kilometers
     */
    public static int distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return (int) Math.sqrt(distance) / 1000;
    }
}
