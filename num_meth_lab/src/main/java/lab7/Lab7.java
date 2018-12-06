package lab7;

import java.util.ArrayList;
import java.util.Map;

public class Lab7 {
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

    public static double matrixNorm(double[][] a, double[][] b) {
        double sum = 0;
        double max = 0;
        for (int i = 0; i < a.length; i++) {
//            sum = 0.0;
            for (int j = 0; j < a[0].length; j++) {
                double tmp = a[i][j] - b[i][j];
                sum += tmp * tmp;
            }
//            if (sum > max) {
//                max = sum;
//            }
        }
        return Math.sqrt(sum);
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

    public static void printArrArr(ArrayList<ArrayList<Double>> a) {
        for (ArrayList<Double> al:a) {
            for (Double d:al) {
                System.out.print(d + " ");
            }
            System.out.println();
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

    public static void printStat(ArrayList<ArrayList<Double>> a, Function2<Double, Double, Double> real,
                                 double hx, double hy) {
        double j = 0;
        for(ArrayList<Double> entry : a) {
            System.out.println(" x : y | u(x,y) : real(x,y) : error(x,y)");
            for (int i = 0; i < entry.size(); i++) {
                double myRes = entry.get(i);
                double r = real.apply(hx * j, hy * i);
                double error = Math.abs(myRes - r);
                System.out.println(hx * i + " : " + hy * j + " | " + myRes + " : " + r + " : " + error);
            }
            j++;
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
}
