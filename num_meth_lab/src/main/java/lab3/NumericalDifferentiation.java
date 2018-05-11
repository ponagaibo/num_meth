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

    public static double func1(double x) {
        if (x < x_points[0] || x > x_points[x_points.length - 1]) {
            System.out.println("Point is out of interval");
            return -1;
        }
        int cnt = x_points.length - 2;
        while (cnt >= 0) {
            if (x_points[cnt] <= x) {
                break;
            }
            cnt--;
        }
        return y_points[cnt] + (y_points[cnt + 1] - y_points[cnt]) /
                (x_points[cnt + 1] - x_points[cnt]) * (x - x_points[cnt]);

    }

    static double func2(double x) {
        if (x < x_points[0] || x > x_points[x_points.length - 2]) {
            System.out.println("Point is out of interval");
            return -1;
        }
        int cnt = x_points.length - 3;
        while (cnt >= 0) {
            if (x_points[cnt] <= x) {
                break;
            }
            cnt--;
        }
        double term1 = (y_points[cnt + 1] - y_points[cnt]) / (x_points[cnt + 1] - x_points[cnt])
                * (x - x_points[cnt]);
        double term2 = (y_points[cnt + 2] - y_points[cnt + 1]) /(x_points[cnt + 2] - x_points[cnt + 1])
                - (y_points[cnt + 1] - y_points[cnt]) / (x_points[cnt + 1] - x_points[cnt]);
        term2 *= (x - x_points[cnt]) * (x - x_points[cnt + 1]);
        term2 /= (x_points[cnt + 2] - x_points[cnt]);
        return y_points[cnt] + term1 + term2;
    }

    static double left_dfunc1(double x) {
        if (x < x_points[0] || x > x_points[x_points.length - 1]) {
            System.out.println("Point is out of interval");
            return -1;
        }
        int cnt = 1;
        while (cnt < x_points.length - 1) {
            if (x <= x_points[cnt]) {
                break;
            }
            cnt++;
        }
        return (y_points[cnt] - y_points[cnt - 1]) /
                (x_points[cnt] - x_points[cnt - 1]);
    }
    static double right_dfunc1(double x) {
        if (x < x_points[0] || x > x_points[x_points.length - 1]) {
            System.out.println("Point is out of interval");
            return -1;
        }
        int cnt = x_points.length - 2;
        while (cnt >= 0) {
            if (x_points[cnt] <= x) {
                break;
            }
            cnt--;
        }
        return (y_points[cnt + 1] - y_points[cnt]) /
                (x_points[cnt + 1] - x_points[cnt]);
    }

    static double dfun2(double x) {
        if (x < x_points[0] || x > x_points[x_points.length - 2]) {
            System.out.println("Point is out of interval");
            return -1;
        }
        int cnt = x_points.length - 2;
        while (cnt >= 0) {
            cnt--;
            if (x_points[cnt] < x) {
                break;
            }
        }
        //System.out.println("cnt: " + cnt + ", " + x_points[cnt] + " < 0.2 < " + x_points[cnt + 1]);
        double term1 = (y_points[cnt + 1] - y_points[cnt]) / (x_points[cnt + 1] - x_points[cnt]);
        double term2 = (y_points[cnt + 2] - y_points[cnt + 1]) /(x_points[cnt + 2] - x_points[cnt + 1])
                - (y_points[cnt + 1] - y_points[cnt]) / (x_points[cnt + 1] - x_points[cnt]);
        term2 *= (2 * x - x_points[cnt] - x_points[cnt + 1]);
        term2 /= (x_points[cnt + 2] - x_points[cnt]);
        return term1 + term2;
    }

    static double ddfun2(double x) {
        if (x < x_points[0] || x > x_points[x_points.length - 2]) {
            System.out.println("Point is out of interval");
            return -1;
        }
        int cnt = x_points.length - 2;
        while (cnt >= 0) {
            cnt--;
            if (x_points[cnt] < x) {
                break;
            }
        }
        //System.out.println("cnt: " + cnt + ", " + x_points[cnt] + " < 0.2 < " + x_points[cnt + 1]);

        double term2 = 2 * ((y_points[cnt + 2] - y_points[cnt + 1]) / (x_points[cnt + 2] - x_points[cnt + 1])
                - (y_points[cnt + 1] - y_points[cnt]) / (x_points[cnt + 1] - x_points[cnt])) / (x_points[cnt + 2] - x_points[cnt]);

        /*System.out.println("y[" + (cnt+2) + "]: " + y_points[cnt+2] + ", y[" + (cnt+1) + "]: " + y_points[cnt+1]
        + ", y[" + cnt + "]: " + y_points[cnt]);
        System.out.println("x[" + (cnt+2) + "]: " + x_points[cnt+2] + ", x[" + (cnt+1) + "]: " + x_points[cnt+1]
                + ", x[" + cnt + "]: " + x_points[cnt]);
        */
        return term2;
    }

}