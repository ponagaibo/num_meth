package lab4;

import java.util.ArrayList;
import java.util.function.Function;

public class RungeKuttaMethod {
    private double h;
    private double a;
    private double b;
    private double x0;
    private double y0;
    private double z0;
    private Main.Function3<Double, Double, Double, Double> my_func1;
    private Main.Function3<Double, Double, Double, Double> my_func2;
    private Main.Function2<Double, Double, Double> my_func;
    private Function<Double, Double> real_f;

    RungeKuttaMethod(double h, double a, double b, double x0, double y0, double z0,
                     Main.Function3<Double, Double, Double, Double> f1,
                     Main.Function3<Double, Double, Double, Double> f2,
                     Function<Double, Double> real_f) {
        this.h = h;
        this.a = a;
        this.b = b;
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.my_func1 = f1;
        this.my_func2 = f2;
        this.real_f = real_f;
    }

    RungeKuttaMethod(double h, double a, double b, double x0, double y0,
                     Main.Function2<Double, Double, Double> f,
                     Function<Double, Double> real_f) {
        this.h = h;
        this.a = a;
        this.b = b;
        this.x0 = x0;
        this.y0 = y0;
        this.my_func = f;
        this.real_f = real_f;
    }

    public ArrayList<Double> rungeKuttaMethod(double h) {
        double[] k = new double[4];
        double[] l = new double[4];
        ArrayList<Double> roots = new ArrayList<>();
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            roots.add(prev_y);
            double r = real_f.apply(prev_x);
//            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
//                    + ", eps: " + Math.abs(r - prev_y));
            k[0] = h * my_func1.apply(prev_x, prev_y, prev_z);
            l[0] = h * my_func2.apply(prev_x, prev_y, prev_z);
            k[1] = h * my_func1.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            l[1] = h * my_func2.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            k[2] = h * my_func1.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            l[2] = h * my_func2.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            k[3] = h * my_func1.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            l[3] = h * my_func2.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            double dy = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;
            double dz = (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6;
            double y = prev_y + dy;
            double z = prev_z + dz;
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
//        System.out.println("Roots size: " + roots.size());
        return roots;
    }

    public double rungeKuttaMethod(double h, double x0, double y0, double z0) {
        double[] k = new double[4];
        double[] l = new double[4];
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            double r = real_f.apply(prev_x);
//            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
//                    + ", eps: " + Math.abs(r - prev_y));
            k[0] = h * my_func1.apply(prev_x, prev_y, prev_z);
            l[0] = h * my_func2.apply(prev_x, prev_y, prev_z);
            k[1] = h * my_func1.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            l[1] = h * my_func2.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            k[2] = h * my_func1.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            l[2] = h * my_func2.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            k[3] = h * my_func1.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            l[3] = h * my_func2.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            double dy = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;
            double dz = (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6;
            double y = prev_y + dy;
            double z = prev_z + dz;
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
        return prev_y;
    }

    public double modifiedRungeKuttaMethod(double h, double x0, double y0, double z0) {
        double[] k = new double[4];
        double[] l = new double[4];
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            double shift = prev_x + h / 8;
            if (((int) (prev_x * 100)) / 100. == -0.5) {
                prev_x += shift;
            }
            double r = real_f.apply(prev_x);
//            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
//                    + ", eps: " + Math.abs(r - prev_y));
            k[0] = h * my_func1.apply(prev_x, prev_y, prev_z);
            l[0] = h * my_func2.apply(prev_x, prev_y, prev_z);
            k[1] = h * my_func1.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            l[1] = h * my_func2.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            k[2] = h * my_func1.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            l[2] = h * my_func2.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            k[3] = h * my_func1.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            l[3] = h * my_func2.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            double dy = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;
            double dz = (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6;
            double y = prev_y + dy;
            double z = prev_z + dz;
            if (prev_x == -0.5 + shift) {
                prev_x -= shift;
            }
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
        return prev_y;
    }

    public void rungeKuttaMethodWithPrint(double h, double x0, double y0, double z0) {
        double[] k = new double[4];
        double[] l = new double[4];
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
                    + ", eps: " + Math.abs(r - prev_y));
            k[0] = h * my_func1.apply(prev_x, prev_y, prev_z);
            l[0] = h * my_func2.apply(prev_x, prev_y, prev_z);
            k[1] = h * my_func1.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            l[1] = h * my_func2.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            k[2] = h * my_func1.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            l[2] = h * my_func2.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            k[3] = h * my_func1.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            l[3] = h * my_func2.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            double dy = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;
            double dz = (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6;
            double y = prev_y + dy;
            double z = prev_z + dz;
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
    }

    public void rungeKuttaMethod(int steps, double[] yy, double[] zz, double h) {
        double[] k = new double[4];
        double[] l = new double[4];
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        int times = steps > 0 ? steps : Integer.MAX_VALUE;
        while (((int) (prev_x * 100)) / 100. <= b && cnt < times) {
            k[0] = h * my_func1.apply(prev_x, prev_y, prev_z);
            l[0] = h * my_func2.apply(prev_x, prev_y, prev_z);
            k[1] = h * my_func1.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            l[1] = h * my_func2.apply(prev_x + h / 2, prev_y + k[0] / 2, prev_z + l[0] / 2);
            k[2] = h * my_func1.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            l[2] = h * my_func2.apply(prev_x + h / 2, prev_y + k[1] / 2, prev_z + l[1] / 2);
            k[3] = h * my_func1.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            l[3] = h * my_func2.apply(prev_x + h, prev_y + k[2], prev_z + l[2]);
            double dy = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;
            double dz = (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6;
            double y = prev_y + dy;
            double z = prev_z + dz;
            yy[cnt] = prev_y;
            zz[cnt] = prev_z;
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
    }

    public void check(int steps, double[] yy) {
        double[] k = new double[4];
        double[] roots = new double[(int) Math.ceil((b - a) / h)];
        double prev_x = x0;
        double prev_y = y0;
        int cnt = 0;
        int times = steps > 0 ? steps : Integer.MAX_VALUE;
        while (prev_x <= b && cnt < times) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
                    + ", eps: " + Math.abs(r - prev_y));
            roots[cnt] = prev_y;
            k[0] = h * my_func.apply(prev_x, prev_y);
            k[1] = h * my_func.apply(prev_x + h / 2, prev_y + k[0] / 2);
            k[2] = h * my_func.apply(prev_x + h / 2, prev_y + k[1] / 2);
            k[3] = h * my_func.apply(prev_x + h, prev_y + k[2]);
            double dy = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;
            double y = prev_y + dy;
            yy[cnt] = prev_y;
            prev_x = prev_x + h;
            prev_y = y;
            cnt++;
        }
    }
}
