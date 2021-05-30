package util;

import java.util.Random;

public class Randomizer {
    private final static Random RANDOM = new Random();

    public static int getSparseIntWithinRange(int min, int max, int sparseUnit) {
        if (max < min) {
            throw new IllegalArgumentException("min cannot be greater than max!");
        }

        int number;
        do {
            number = RANDOM.nextInt((max - min) + 1) + min;
        } while (sparseUnit != 0 && number % sparseUnit != 0);
        return number;
    }
}
