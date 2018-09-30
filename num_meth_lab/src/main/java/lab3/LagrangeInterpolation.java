package lab3;

import java.util.function.Function;

class LagrangeInterpolation {
    private double[] points;
    private Function<Double, Double> func;

    LagrangeInterpolation(double[] p, Function<Double, Double> f) {
        points = p;
        func = f;
    }

    double interpolation(double x) {
        double sum = 0;
        for (int i = 0; i < points.length; i++) {
            double tmp = mult(x, i) / mult(points[i], i);
            sum += tmp * func.apply(points[i]);
/*
            System.out.println("i: " + i + " xi: " + points[i] + " fi: " + func.apply(points[i]) +
                    " w(xi): " + mult(points[i], i) + " fi/w(xi): " + func.apply(points[i]) / mult(points[i], i) +
                    " X* - xi: " + (x_points - points[i]));
*/
        }
        return sum;
    }

    private  double mult(double x, int ind) {
        double res = 1;
        for (int i = 0; i < points.length; i++) {
            if (i != ind) {
                res *= (x - points[i]);
            }
        }
        return res;
    }
}
