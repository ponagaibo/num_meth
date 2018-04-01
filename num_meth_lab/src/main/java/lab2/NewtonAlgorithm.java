package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class NewtonAlgorithm {
    public static double precision;
    private static double root;

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    private static void findRoot() {
        double x0 = 0.8;
        root = x0 + 2 * precision;
        while (Math.abs(root - x0) > precision) {
            x0 = root;
            root = x0 - fun.apply(x0) / dfun.apply(x0);
        }
    }

    public static void lab2_n8_2_1() {
        System.out.println("\n~~~ Newton's method ~~~");
        findRoot();
        System.out.println("\nRoot: " + root);
    }

    public static double func(double x) {
        return (Math.log(x + 1) - 2 * x * x + 1);
    }

    public static double ddfunc(double x) {
        return (-1/((x + 1) * (x + 1)) - 4);
    }

    private static final Function<Double, Double> ddfun = (x) ->
            (-1/((x + 1) * (x + 1)) - 4);

    private static final DoubleFunction<Double> dfun = (x) ->
            (1 / (x + 1) - 4 * x);

    private static final Function<Double, Double> fun = (x) ->
            (Math.log(x + 1) - 2 * x * x + 1);

    public static void main(String[] args) {

    }
}
