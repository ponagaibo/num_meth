package lab3;

import java.util.function.Function;

class NumericalIntegration {
    private static Function<Double, Double> func;
    private static double begin, end;

    NumericalIntegration(Function<Double, Double> f, double b, double e) {
        func = f;
        begin = b;
        end = e;
    }

    static double rectangle_method(double step) {
        double sum = 0;
        double cur = begin + step;
        while (cur <= end) {
            sum += step * func.apply((2 * cur - step) / 2);
            cur += step;
        }
        return sum;
    }

    static double trapeze_method(double step) {
        double sum = 0;
        double cur = begin + step;
        while (cur <= end) {
            sum += step / 2 * (func.apply(cur - step) + func.apply(cur));
            cur += step;
        }
        return sum;
    }

    static double simpsons_rule(double step) {
        double sum = 0;
        double cur = begin + 2 * step;
        while (cur <= end) {
            sum += step / 3 * (func.apply(cur - 2 * step) + 4 * func.apply((2 * cur - 2 * step) / 2)
            + func.apply(cur));
            cur += 2 * step;
        }
        return sum;
    }

    static double runge_method(double h1, double h2, double f1, double f2, double p) {
        double k = h2 / h1;
        return f1 + (f1 - f2) / (Math.pow(k, p) - 1);
    }
}
