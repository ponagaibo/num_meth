package lab5;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.function.Function;

public class Lab5 {
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

    public static void printMap(Map<Double, Double[][]> m) {
        for(Map.Entry<Double, Double[][]> entry: m.entrySet()) {
            Double key = entry.getKey();
            Double[][] value = entry.getValue();
            System.out.println("\ntime = " + key + "\n");
            for (int i = 0; i < value[0].length; i++) {
                System.out.println("" + value[0][i] + " : " + value[1][i]);
            }
        }
    }
/*
    Map<Double, Double[][]> lab5_efdm(int n, double t, int apr) {
        ExplicitFiniteDifferenceMethod efdm = new ExplicitFiniteDifferenceMethod(n, t);
        efdm.solve(apr);
//        efdm.solve(2);
//        efdm.solve(3);
        return efdm.getFullSolution();
    }

    Map<Double, Double[][]> lab5_ifdm(int n, double t, int apr) throws FileNotFoundException {
        ImplicitFiniteDifferenceMethod ifdm = new ImplicitFiniteDifferenceMethod(n, t);
        ifdm.solve(apr);
//        ifdm.solve(2);
//        ifdm.solve(3);
        return ifdm.getFullSolution();
    }

    Map<Double, Double[][]> lab5_cnm(int n, double t, int apr) throws FileNotFoundException {
        CrankNicolsonMethod cnm = new CrankNicolsonMethod(n, t);
        cnm.solve(apr);
//        cnm.solve(2);
//        cnm.solve(3);
        return cnm.getFullSolution();
    }

    static class Solution {
        double[] xPoints;
        double[] yPoints;
        Solution(double[] x, double[] y) {
            xPoints = x;
            yPoints = y;
            System.arraycopy(x, 0, xPoints, 0, x.length);
            System.arraycopy(y, 0, yPoints, 0, y.length);

        }
    }*/
}
