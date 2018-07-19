package com.github.upcraftlp.glasspane.api.util;

import java.util.TreeMap;

public class MathUtils {

    public static final int ZERO = 0;

    public static final int ONE        =              1;
    public static final int TEN        =             10;
    public static final int HUNDRED    =            100;
    public static final long THOUSAND   =           1000L;
    public static final long MILLION    =        1000000L;
    public static final long BILLION    =     1000000000L;
    public static final long TRILLION   =  1000000000000L;

    public static final double SQRT_2 = Math.sqrt(2.0D);
    public static final double TWO_PI = Math.PI * 2.0D;

    private static final TreeMap<Integer, String> ROMAN_NUMERALS = new TreeMap<>();
    static {
        ROMAN_NUMERALS.put(1000, "M");
        ROMAN_NUMERALS.put(900, "CM");
        ROMAN_NUMERALS.put(500, "D");
        ROMAN_NUMERALS.put(400, "DM");
        ROMAN_NUMERALS.put(100, "C");
        ROMAN_NUMERALS.put(90, "XC");
        ROMAN_NUMERALS.put(50, "L");
        ROMAN_NUMERALS.put(40, "XL");
        ROMAN_NUMERALS.put(10, "X");
        ROMAN_NUMERALS.put(9, "IX");
        ROMAN_NUMERALS.put(5, "V");
        ROMAN_NUMERALS.put(4, "IV");
        ROMAN_NUMERALS.put(1, "I");
    }

    /**
     * convert any given integer to roman numerals, accounting for negative integer,
     * {@code 0} will return an empty String.
     */
    public static String toRomanNumerals(int input) {
        StringBuilder builder = new StringBuilder();
        if(input == 0) return "N";
        if(input < 0) {
            input = -input;
            builder.append("-");
        }
        while(input > 0) {
            int l = ROMAN_NUMERALS.floorKey(input);
            if(input == l) {
                builder.append(ROMAN_NUMERALS.get(input));
                break;
            }
            else {
                builder.append(ROMAN_NUMERALS.get(l));
                input--;
            }
        }
        return builder.toString();

    }
}
