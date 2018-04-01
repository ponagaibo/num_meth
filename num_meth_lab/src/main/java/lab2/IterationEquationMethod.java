package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Function;

public class IterationEquationMethod {
    static double root;
    static double precision;

    private static final Function<Double, Double> phi = (x) ->
            (Math.sqrt((Math.log(x + 1) + 1) / 2));

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    static int findRoot() {
        int cnt = 0;
        double q = 0.16;
        double x0 = 0.8;
        root = (0.8 + 1) / 2;
        double eps = (q * Math.abs(root - x0)) / (1 - q);
        while (eps > precision) {
            x0 = root;
            root = phi.apply(x0);
            eps = (q * Math.abs(root - x0)) / (1 - q);
            cnt++;
        }
        return cnt;
    }
}
