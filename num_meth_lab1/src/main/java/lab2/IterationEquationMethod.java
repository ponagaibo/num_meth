package lab2;

import java.util.function.Function;

public class IterationEquationMethod {
    static double root;

    private static final Function<Double, Double> phi = (x) ->
            (Math.sqrt((Math.log(x + 1) + 1) / 2));

    static int findRoot() {
        int cnt = 0;
        double precision = NewtonsEquationAlgorithm.precision;
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
