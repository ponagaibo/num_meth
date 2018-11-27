package lab2;

import java.util.function.Function;

public class IterationMethod {
    private static double presicion;

    private static final Function<Double, Double> linear_func = (x) ->
            (Math.sqrt((Math.log(x + 1) + 1) / 2));

    public static void main(String[] args) {
        presicion = NewtonAlgorithm.precision;    }
}
