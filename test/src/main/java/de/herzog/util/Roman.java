package de.herzog.util;

public class Roman {

    private final static int[] arabic = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private final static String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private static String reduce(int n, int index, StringBuilder sb) {
        for (int i = index; i < arabic.length; i++) {
            if (arabic[i] <= n) {
                return reduce(n - arabic[i], i, sb.append(roman[i]));
            }
        }
        return sb.toString();
    }
    
    public static String toRoman(long n) {
    	return toRoman((int) n);
    }

    public static String toRoman(int n) {
        assert (Math.abs(n) < 4000);
        return (n == 0) ? "nulla" : reduce(n, 0, new StringBuilder(n > 0 ? "" : "-"));
    }
}