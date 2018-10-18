package lab5;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.function.Function;

public abstract class ParabolicMethods {
    protected static double a = 1.0;
    protected static double right = Math.PI / 2.0;
    protected static Function<Double, Double> phi0 = (t) -> Math.sin(t);
    protected static Function<Double, Double> phiN = (t) -> -Math.sin(t);
    protected static Function<Double, Double> psi = (x) -> 0.0;
    protected Lab5.Function2<Double, Double, Double> f = (x, t) -> Math.cos(x) * (Math.cos(t) + Math.sin(t));
    Lab5.Function2<Double, Double, Double> analyticSolution = (x, t) -> Math.sin(t) * Math.cos(x);
    Map<Double, Double[][]> fullSolution;

    protected static int valueN;
    protected static double valueT;

    ParabolicMethods(int n, double t) {
        this.valueN = n;
        this.valueT = t;
    }

    abstract double[] solve(int approx) throws FileNotFoundException;

}
