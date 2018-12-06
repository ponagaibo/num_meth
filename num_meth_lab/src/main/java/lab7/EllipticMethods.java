package lab7;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lab6.Lab6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

public abstract class EllipticMethods {
    protected static double right_x = 1.0;
    protected static double right_y = Math.PI / 2.0;
//    protected static Function<Double, Double> phi1 = (y) -> 0.0;
//    protected static Function<Double, Double> phi2 = (y) -> 1.0 - y * y;
//    protected static Function<Double, Double> phi3 = (x) -> 0.0;
//    protected static Function<Double, Double> phi4 = (x) -> x * x - 1.0;
    protected static Function<Double, Double> phi1 = (y) -> Math.cos(y);
    protected static Function<Double, Double> phi2 = (y) -> 0.0;
    protected static Function<Double, Double> phi3 = (x) -> x;
    protected static Function<Double, Double> phi4 = (x) -> 0.0;

    protected Lab7.Function2<Double, Double, Double> f = (x, y) -> 0.0;
//    Lab7.Function2<Double, Double, Double> analyticSolution = (x, y) -> x * x - y * y;
    Lab7.Function2<Double, Double, Double> analyticSolution = (x, y) -> x * Math.cos(y);

    ArrayList<ArrayList<Double>> fullSolution;

    protected static int valueN1;
    protected static int valueN2;

    EllipticMethods(int n1, int n2) {
        this.valueN1 = n1;
        this.valueN2 = n2;
    }

    abstract double[] solve(int method, double relaxation) throws IOException, PythonExecutionException;
}