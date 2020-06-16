package converter;

import java.util.Scanner;

public class Main {
    public static int radix;
    public static String number;
    public static int newRadix;

    public static void main(String[] args) {
        if (!userInput()) {
            System.out.println("error");
            return;
        }
        System.out.println(converter(radix, number, newRadix));
    }

    public static String converter(int radix, String number, int newRadix) {
        if (validateRadix(newRadix) && validateRadix(radix)) {
            if (number.contains(".")) {
                return convertInteger(radix, number.split("\\.")[0], newRadix) + "." +
                        convertFractional(radix, number.split("\\.")[1], newRadix);
            } else {
                return convertInteger(radix, number, newRadix);
            }
        } else {
            return "error";
        }
    }

    private static String convertInteger(int sourceRadix, String number, int targetRadix) {
        int decimal;
        if (sourceRadix == 1) {
            decimal = number.length();
        } else {
            decimal = Integer.parseInt(number, sourceRadix);
        }
        if (targetRadix == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            while (decimal > 0) {
                stringBuilder.append("1");
                decimal--;
            }
            return stringBuilder.toString();
        } else {
            return Integer.toString(decimal, targetRadix);
        }
    }

    private static String convertFractional(int sourceRadix, String number, int targetRadix) {
        double fractional = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            double a = Integer.parseInt(String.valueOf(number.charAt(i)), sourceRadix);
            a /= Math.pow(sourceRadix, i + 1);
            fractional += a;
        }

        for (int i = 0; i < 5; i++) {
            double a = fractional * targetRadix;
            int b = (int) a;
            fractional = a - b;
            stringBuilder.append((char) (b >= 10 ? 'a' + b - 10 : '0' + b));
        }
        return stringBuilder.toString();
    }

    private static boolean validateRadix(int radix) {
        return radix > 0 && radix <= 36;
    }

    public static boolean userInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (!input.matches("[\\d]+") && !sc.hasNext()) {
            return false;
        }
        radix = Integer.parseInt(input);
        input = sc.nextLine();
        if (!input.matches("[\\w.]+") || !sc.hasNext()) {
            return false;
        }
        number = input;
        input = sc.nextLine();
        if (!input.matches("[\\d]+")) {
            return false;
        }
        newRadix = Integer.parseInt(input);
        return true;
    }
}
