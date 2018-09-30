package lab3;

import java.util.function.Function;

class NewtonsInterpolation {
    private double[] points;
    private Function<Double, Double> func;
    private double[][] fd;

    NewtonsInterpolation(double[] p, Function<Double, Double> f) {
        points = p;
        func = f;
        fd = new double[p.length][p.length];
    }

    private double boundedMult(int ind, double x) {
        if (ind == -1) return 1;
        double res = 1;
        for (int i = 0; i <= ind; i++) {
            res *= (x - points[i]);
        }
        return res;
    }

    private void finiteDifference() {
        for (int i = 0; i < points.length; i++) {
            fd[i][i] = func.apply(points[i]);
        }
        for (int k = 1; k < points.length; k++) {
            for (int i = 0; i < points.length - k; i++) {
                for (int j = i + k; j < points.length; j++) {
                    fd[i][j] = (fd[i][j - 1] - fd[i + 1][j]) / (points[i] - points[j]);
                }
            }
        }
    }

    double interpolation(double x) {
        finiteDifference();
        double res = 0;
        for (int i = 0; i < points.length; i++) {
            res += boundedMult(i - 1, x) * fd[0][i];
        }
        return res;
    }

}
