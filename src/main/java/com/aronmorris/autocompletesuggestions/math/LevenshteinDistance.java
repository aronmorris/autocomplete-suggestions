package com.aronmorris.autocompletesuggestions.math;

import com.aronmorris.autocompletesuggestions.math.MathHelper;

import java.util.Arrays;

/**
 * The Levenshtein Distance algorithm uses dynamic programming to determine the number of edits required
 * to change string x to string y, where we assign a cost of 1 to substitutions, deletions, and insertions
 * <p>
 * This implementation is from https://www.baeldung.com/java-levenshtein-distance with minor modifications
 */
public class LevenshteinDistance {

    public static int levenshteinDistance(String query, String target) {
        int[][] dp = new int[query.length() + 1][target.length() + 1];

        for (int i = 0; i <= query.length(); i++) {
            for (int j = 0; j <= target.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(query.charAt(i - 1), target.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[query.length()][target.length()];
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
}
