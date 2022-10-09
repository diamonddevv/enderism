package net.diamonddev.enderism.util;

public class EnderismMath {
    /**
     * Checks if the number after the decimal point is only zeroes, i.e. has a decimal.
     * @param number Any number.
     * @return True if the number does not have only zeroes after the decimal point.
     */
    public static boolean hasDecimal(double number) {
        return number % 1 == 0;
    }
}
