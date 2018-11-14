package lab6;

import java.util.Map;

public class Lab6 {
    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    public static void printArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("" + array[i] + " ");
        }
        System.out.println();
    }

    public static void printArray(Double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("" + array[i] + " ");
        }
        System.out.println();
    }

    public static double arrayNorm(double[] a, double[] b) {
        double max = 0.0;
        for (int i = 0; i < a.length; i++) {
            double tmp = Math.abs(b[i] - a[i]);
            if (tmp > max) {
                max = tmp;
            }
        }
        return max;
    }

    public static double arrayNorm(Double[] a, Double[] b) {
        double max = 0.0;
        for (int i = 0; i < a.length; i++) {
            double tmp = Math.abs(b[i] - a[i]);
            if (tmp > max) {
                max = tmp;
            }
        }
        return max;
    }

    public static void printMap(Map<Double, Double[][]> m, double tau) {
        for(Map.Entry<Double, Double[][]> entry: m.entrySet()) {
            Double key = entry.getKey();
            Double[][] value = entry.getValue();
            System.out.println("\ntime = " + key * tau + "\n");
            for (int i = 0; i < value[0].length; i++) {
                System.out.println("" + value[0][i] + " : " + value[1][i]);
            }
        }
    }

    public static void printStat(Map<Double, Double[][]> m, Function2<Double, Double, Double> real,
                                 double tau, double h) {
        for(Map.Entry<Double, Double[][]> entry: m.entrySet()) {
            Double key = entry.getKey();
            Double[][] value = entry.getValue();
            System.out.println("\ntime = " + key * tau + "\n");
            System.out.println(" x : u(x,t) : real(x,t) : error(x,t)");
            for (int i = 0; i < value[0].length; i++) {
                double r = real.apply(value[0][i], key * tau);
                double error = Math.abs(value[1][i] - r);
                System.out.println("" + value[0][i] + " : " + value[1][i] + " : " + r + " : " + error);
            }
        }
    }
}
