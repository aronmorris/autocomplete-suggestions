package com.aronmorris.autocompletesuggestions.math;

/**
 * The Haversine Formula is used to calculate the distance between two coordinate points on a sphere.
 * Conveniently, the Earth is more or less a sphere.
 */
public class HaversineCoordinateDistance {

    //This constant is future-proof barring something very bad happening
    public final static double RADIUS_OF_EARTH_KM = 6371;

    /**
     * @param qLat  query Latitude
     * @param qLong query Longitude
     * @param tLat  target Latitude
     * @param tLong target Longitude
     * @return scoring value based on distance
     * Implementation of Haversine distance algorithm, which measures distance between 2 sets of coordinates
     * details from https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
     */
    public static double calculateHaversineDistance(double qLat, double qLong, double tLat, double tLong) {
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
