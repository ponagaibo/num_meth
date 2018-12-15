package lab8;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.function.Function;

public abstract class Parabolic2DMethods {
    protected static double a = 1.0;
    protected static double right_x = Math.PI / 2.0;
    protected static double right_y = Math.log(2.0);

    protected static Lab8.Function2<Double, Double, Double> phi1 = (x, t) ->
            Math.cos(2.0 * x) * Math.exp(-3.0 * a * t);
    protected static Lab8.Function2<Double, Double, Double> phi2 = (x, t) ->
            3.0 / 4.0 * Math.cos(2.0 * x) * Math.exp(-3.0 * a * t);
    protected static Lab8.Function2<Double, Double, Double> phi3 = (y, t) ->
            Math.sinh(y) * Math.exp(-3.0 * a * t);
    protected static Lab8.Function2<Double, Double, Double> phi4 = (y, t) ->
            -Math.sinh(y) * Math.exp(-3.0 * a * t);
    protected static Lab8.Function2<Double, Double, Double> psi = (x, y) ->
            Math.cos(2.0 * x) * Math.sinh(y);

    protected Lab8.Function3<Double, Double, Double, Double> f = (x, y, t) -> 0.0;
    Lab8.Function3<Double, Double, Double, Double> analyticSolution = (x, y, t) ->
            Math.cos(2.0 * x) * Math.sinh(y) * Math.exp(-3.0 * a * t);
    Map<Double, Double[][]> fullSolution;

    protected static int valueN1;
    protected static int valueN2;
    protected static double valueT = Math.PI; // change in preprocessor

    Parabolic2DMethods(int n1, int n2) {
        this.valueN1 = n1;
        this.valueN2 = n2;
    }

    abstract double[] solve(int k);
}
