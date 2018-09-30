package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IterationSystemMethod {
    private static double precision;
    static double x, y;

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    private static double norm(double x0, double y0, double x1, double y1) {
        double max = Math.abs(x1 - x0);
        if (Math.abs(y1 - y0) > max) {
            return Math.abs(y1 - y0);
        } else {
            return max;
        }
    }

    private static double phi1(double x1, double x2) {
        return Math.sqrt(9 - x2 * x2);
        //return 0.3 - 0.1 * x1 * x1 - 0.2 * x2* x2;
    }

    private static double phi2(double x1, double x2) {
        return Math.log(x1 + 3);
        //return 0.7 - 0.2 * x1 * x1 + 0.1 * x1 * x2;
    }

    static int findRoots() {
        int cnt = 0;
        double q = 0.9;
        double x0 = (2 + 3) / 2.;
        double y0 = (1 + 2) / 2;
        x = phi1(x0, y0);
        y = phi2(x0, y0);
        /*
        double q = 0.5;
        double x0 = 0.25;
        double y0 = 0.75;
        x = phi1(x0, y0);
        y = phi2(x0, y0);
        */
        double eps = (q * norm(x0, y0, x, y)) / (1 - q);
        while (eps > precision) {
            x0 = x;
            y0 = y;
            x = phi1(x0, y0);
            y = phi2(x0, y0);
            eps = (q * norm(x0, y0, x, y)) / (1 - q);
            cnt++;
        }
        return cnt;
    }
}
