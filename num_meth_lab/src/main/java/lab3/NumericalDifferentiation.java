package lab3;

public class NumericalDifferentiation {
    private static double[] x_points;
    private static double[] y_points;

    NumericalDifferentiation(double[] newX, double[] newY) {
        x_points = new double[newX.length];
        System.arraycopy(newX, 0, x_points, 0, newX.length);
        y_points = new double[newY.length];
        System.arraycopy(newY, 0, y_points, 0, newY.length);
    }

    public static double func(double x) {
        int cnt = 0;
        while (cnt < x_points.length && x > x_points[cnt++]);
        if (cnt == x_points.length || cnt == 0) {
            System.out.println("Point out of interval");
            return -1;
        }
        return y_points[cnt - 1] + (y_points[cnt] - y_points[cnt - 1]) /
                (x_points[cnt] - x_points[cnt - 1]) * (x - x_points[cnt - 1]);

    }

    public static double right_dfunc(double x) {
        int cnt = 0;
        while (cnt < x_points.length && x > x_points[cnt++]);
        if (cnt == x_points.length || cnt == 0) {
            System.out.println("Point out of interval");
            return -1;
        }
        System.out.println("cnt: " + cnt + ", " + x_points[cnt - 1] + " < 0.2 < " + x_points[cnt]);
        return (y_points[cnt] - y_points[cnt - 1]) /
                (x_points[cnt] - x_points[cnt - 1]);
    }

    public static double left_dfunc(double x) {
        int cnt = 0;
        while (cnt < x_points.length && x_points[cnt++] >= x);
        if (cnt == x_points.length || cnt == 0) {
            System.out.println("Point out of interval");
            return -1;
        }
        System.out.println("cnt: " + cnt + ", " + x_points[cnt - 1] + " < 0.2 < " + x_points[cnt]);
        return (y_points[cnt] - y_points[cnt - 1]) /
                (x_points[cnt] - x_points[cnt - 1]);
    }
}
