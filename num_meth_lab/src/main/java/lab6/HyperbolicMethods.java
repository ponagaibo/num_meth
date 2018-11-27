package lab6;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public abstract class HyperbolicMethods {
    protected static double a = 1.0; // задавать, дефолтно 1
    protected static double right = Math.PI;
    protected static Function<Double, Double> phi0 = (t) -> 0.0;
    protected static Function<Double, Double> phiN = (t) -> 0.0;
    protected static Function<Double, Double> psi1 = (x) -> Math.sin(x) + Math.cos(x);
    protected static Function<Double, Double> psi2 = (x) -> -a * (Math.sin(x) + Math.cos(x));
    protected static Function<Double, Double> ddpsi1 = (x) -> -Math.sin(x) - Math.cos(x);

    protected Lab6.Function2<Double, Double, Double> f = (x, t) -> 0.0;
    Lab6.Function2<Double, Double, Double> analyticSolution = (x, t) -> Math.sin(x - a * t) + Math.cos(x + a * t);
    Map<Double, Double[][]> fullSolution;

    protected static int valueN;
    protected static double valueT = 3.0 * Math.PI;

    HyperbolicMethods(int n, double aa) {
        this.valueN = n;
        this.a = aa;
    }

    abstract double[] solve(int approx_x, int approx_t) throws IOException, PythonExecutionException;
}