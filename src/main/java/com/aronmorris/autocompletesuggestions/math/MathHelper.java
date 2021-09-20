package com.aronmorris.autocompletesuggestions.math;

/**
 * Sometimes we need misc. math functions, and I don't want to repeat myself when one entity can handle that
 */
public class MathHelper {
    //round to 3 decimal places
    public static double roundValue(double value) {
        value = Math.round(value * 1000);
        value = value / 1000;
        return value;
    }
}
