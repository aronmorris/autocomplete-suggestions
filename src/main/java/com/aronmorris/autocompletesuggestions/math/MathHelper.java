package com.aronmorris.autocompletesuggestions.math;

public class MathHelper {
    //round to 3 decimal places
    public static double roundValue(double value) {
        value = Math.round(value * 1000);
        value = value / 1000;
        return value;
    }
}
