package com.aronmorris.autocompletesuggestions.matching;

import com.aronmorris.autocompletesuggestions.math.MathHelper;

import java.util.Arrays;

/**
 * The Levenshtein Distance algorithm uses dynamic programming to determine the number of edits required
 * to change string x to string y, where we assign a cost of 1 to substitutions, deletions, and insertions
 *
 * This implementation is from https://www.baeldung.com/java-levenshtein-distance with minor modifications
 */
public class LevenshteinDistance {

    private final static int MAX_EDIT_DISTANCE = 3;

    /**
     * Returns an overall Confidence of match, where 1.0 is equal confidence, and fuzzier matches are lower.
     * @param query query string to attempt matching
     * @param target data string to be matched against
     * @return the normalized confidence score
     */
    public static double normalizedDistanceScore(String query, String target) {
        if (query == null || target == null) {
            return 0.0;
        }
        query = query.toLowerCase();
        target = target.toLowerCase();

        if (query.equals(target)) {
            return 1.0;
        }

        double levenshteinDistance = levenshteinDistance(query, target);

        //if the edit distance is too big the value can be discarded, or we get a ton of items that have only
        //a tiny commonality with the query string
        if (levenshteinDistance > MAX_EDIT_DISTANCE) {
            return 0.0;
        } else {
            return MathHelper.roundValue(
                    1 - (levenshteinDistance(query, target) / Math.max(query.length(), target.length()))
            );
        }
    }

    private static double levenshteinDistance(String query, String target) {
        int[][] dp = new int[query.length() + 1][target.length() + 1];

        for (int i = 0; i <= query.length(); i++) {
            for (int j = 0; j <= target.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
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
