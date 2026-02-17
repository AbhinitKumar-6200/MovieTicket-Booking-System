package util;

public class InputValidator {

    public static boolean isValidMenuOption(int opt, int min, int max) {
        return opt >= min && opt <= max;
    }

    public static boolean isPositiveInt(int n) {
        return n > 0;
    }
}
