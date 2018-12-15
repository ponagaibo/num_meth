package lab8;

import java.util.Map;

import static lab8.Parabolic2DMethods.valueN1;
import static lab8.Parabolic2DMethods.valueN2;

public class Lab8 {
    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    interface Function3<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    public static void printMap(Map<Double, Double[][]> m, double hx, double hy, boolean grid) {
        for(Map.Entry<Double, Double[][]> entry: m.entrySet()) {
            Double key = entry.getKey();
            Double[][] value = entry.getValue();
            if (grid) {
                for (int i = 0; i < valueN1 + 1; i++) {
                    for (int j = 0; j < valueN2 + 1; j++) {
                        System.out.println("(" + (i * hx) + " : " + (j * hy) + ") " + value[i][j]);
                    }
                }
            } else {
                for (int i = 0; i < valueN1 + 1; i++) {
                    for (int j = 0; j < valueN2 + 1; j++) {
                        System.out.print(value[i][j] + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public static void printMatrix(Double[][] m) {
//        System.out.println("Str: " + m.length + ", col: " + m[0].length);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printMatrix(double[][] m) {
//        System.out.println("Str: " + m.length + ", col: " + m[0].length);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.print("\n");
        }
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

    public static void printStat(Map<Double, Double[][]> m, Function3<Double, Double, Double, Double> real,
                                 double tau, double hx, double hy) {
        for(Map.Entry<Double, Double[][]> entry: m.entrySet()) {
            Double key = entry.getKey();
            Double[][] value = entry.getValue();
            System.out.println("\n" + key + " time = " + key * tau);
            System.out.println(" (x:y) : u(x,t) | real(x,t) | error(x,t)");
            double maxErr = 0;
            for (int i = 0; i < value.length; i++) {
                for (int j = 0; j < value[0].length; j++) {
                    double r = real.apply(hx * i, hy * j, key * tau);
                    double error = Math.abs(value[i][j] - r);
//                    System.out.println("i=" + i + ", j=" + j + ", my = " + value[i][j] + ", real = " + r
//                            + ", err = " + error);
                    if (error > maxErr) {
                        maxErr = error;
                    }
                    System.out.println("(" + (hx * i) + " : " + (hy * j) + "): " + value[i][j]
                            + " | " + r + " | " + error);
                }
            }
            System.out.println("Max error on time " + key + " is " + maxErr);
        }
    }
}
