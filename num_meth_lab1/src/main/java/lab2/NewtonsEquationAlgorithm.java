package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class NewtonsEquationAlgorithm {
    public static double precision;
    static double root;

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    static int findRoot() {
        int cnt = 0;
        double x0 = 0.8;
        root = (0.8 + 1) / 2;
        while (Math.abs(root - x0) > precision) {
            x0 = root;
            root = x0 - fun.apply(x0) / dfun.apply(x0);
            cnt++;
        }
        return cnt;
    }

    private static final DoubleFunction<Double> dfun = (x) ->
            (1 / (x + 1) - 4 * x);

    private static final Function<Double, Double> fun = (x) ->
            (Math.log(x + 1) - 2 * x * x + 1);
}
