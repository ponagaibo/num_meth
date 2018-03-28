package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Function;

public class NewtonsSystemAlgorithm {
    static double x, y;
    static double precision;

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    public static double norm (double x0, double y0, double x1, double y1) {
        double max = Math.abs(x1 - x0);
        if (Math.abs(y1 - y0) > max) {
            return Math.abs(y1 - y0);
        } else {
            return max;
        }
    }

    private static double f1 (double x1, double x2) {
        return x1 * x1 + x2 * x2 - 9;
    }

    private static double f2 (double x1, double x2) {
        return x1 - Math.exp(x2) + 3;
    }

    private static double df1dx1 (double x1, double x2) {
        return 2 * x1;
    }

    private static double df1dx2 (double x1, double x2) {
        return 2 * x2;
    }

    private static double df2dx1 (double x1, double x2) {
        return 1;
    }

    private static double df2dx2 (double x1, double x2) {
        return -Math.exp(x2);
    }

    private static double detA1(double x1, double x2) {
        return f1(x1, x2) * df2dx2(x1, x2) - f2(x1, x2) * df1dx2(x1, x2);
    }

    private static double detA2(double x1, double x2) {
        return f2(x1, x2) * df1dx1(x1, x2) - f1(x1, x2) * df2dx1(x1, x2);
    }

    private static double detJ(double x1, double x2) {
        return df1dx1(x1, x2) * df2dx2(x1, x2) - df1dx2(x1, x2) * df2dx1(x1, x2);
    }

    static int findRoots() {
        double x0 = 2;
        double y0 = 1;
        x = (2 + 3) / 2;
        y = (1 + 2) / 2;
        int cnt = 0;
        double eps = norm(x0, y0, x, y); // left bounds
        while (eps > precision) {
            x0 = x;
            y0 = y;
            x = x0 - detA1(x0, y0) / detJ(x0, y0);
            y = y0 - detA2(x0, y0) / detJ(x0, y0);
            cnt++;
        }
        return cnt;
    }
}
