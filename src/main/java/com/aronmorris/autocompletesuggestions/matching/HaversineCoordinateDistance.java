package com.aronmorris.autocompletesuggestions.matching;

public class HaversineCoordinateDistance {

    //This constant is future-proof barring something very bad happening
    public final static double RADIUS_OF_EARTH_KM = 6371;

    //300 km
    public final static double MAX_SEARCH_RADIUS_KM = 300;
    /**
     * @param qLat query Latitude
     * @param qLong query Longitude
     * @param tLat target Latitude
     * @param tLong target Longitude
     * @return scoring value based on distance
     */
    public static double getHaversineDistanceScore(
            double qLat,
            double qLong,
            double tLat,
            double tLong
    ) {
        double distance = calculateHaversineDistance(qLat, qLong, tLat, tLong);

        return distance > MAX_SEARCH_RADIUS_KM ? 0.0 : (1 - (distance / MAX_SEARCH_RADIUS_KM)); //normalize to a value within 1
    }

    // Implementation of Haversine distance algorithm, which measures distance between 2 sets of coordinates
    // details from https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
    private static double calculateHaversineDistance(double qLat, double qLong, double tLat, double tLong) {
        double latitudeDiffInRad = Math.toRadians(tLat - qLat);
        double longitudeDiffInRad = Math.toRadians(tLong - qLong);

        qLat = Math.toRadians(qLat);
        tLat = Math.toRadians(tLat);

        double a = Math.pow(Math.sin(latitudeDiffInRad / 2), 2) +
                    Math.pow(Math.sin(longitudeDiffInRad / 2), 2) *
                    Math.cos(qLat) *
                    Math.cos(tLat);
        double c = 2 * Math.asin(Math.sqrt(a));
        return c * RADIUS_OF_EARTH_KM;
    }
}
