package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AdamsMethod {
    private double h;
    private double a;
    private double b;
    private double x0;
    private double y0;
    private double z0;

    private Main.Function3<Double, Double, Double, Double> my_func1;
    private Main.Function3<Double, Double, Double, Double> my_func2;
    private Function<Double, Double> real_f;

    AdamsMethod(double h, double a, double b, double x0, double y0, double z0,
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

    void adamsMethod() {
        RungeKuttaMethod tmp_rkm = new RungeKuttaMethod(h, a, b, x0, y0, z0, my_func1, my_func2, real_f);
        double[] four_y = new double[4];
        double[] four_z = new double[4];
        tmp_rkm.rungeKuttaMethod(4, four_y, four_z);
//        System.out.println("Roots len: " + four_y.length);
        double prev_x = x0;
        double prev_y = four_y[3];
        double prev_z = four_z[3];
        List<Double> y_roots = new ArrayList<>();
        List<Double> z_roots = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            y_roots.add(four_y[i]);
            z_roots.add(four_z[i]);
//            System.out.println("y: " + four_y[i] + ", z: " + four_z[i]);
        }
        int size = y_roots.size() - 1;
        while (prev_x <= b) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + (size - 3) + ", x: " + prev_x + ", y: " + y_roots.get(size - 3)
                    + ", real y: " + r + ", eps: " + Math.abs(r - y_roots.get(size - 3)));
            double y = prev_y + (55 * my_func1.apply(x0 + size * h, y_roots.get(size), z_roots.get(size))
                    - 59 * my_func1.apply(x0 + (size - 1) * h, y_roots.get(size - 1),z_roots.get(size - 1))
                    + 37 * my_func1.apply(x0 + (size - 2) * h, y_roots.get(size - 2),z_roots.get(size - 2))
                    - 9 * my_func1.apply(x0 + (size - 3) * h, y_roots.get(size - 3),z_roots.get(size - 3))) * h / 24;
            double z = prev_z + (55 * my_func2.apply(x0 + size * h, y_roots.get(size), z_roots.get(size))
                    - 59 * my_func2.apply(x0 + (size - 1) * h, y_roots.get(size - 1),z_roots.get(size - 1))
                    + 37 * my_func2.apply(x0 + (size - 2) * h, y_roots.get(size - 2),z_roots.get(size - 2))
                    - 9 * my_func2.apply(x0 + (size - 3) * h, y_roots.get(size - 3),z_roots.get(size - 3))) * h / 24;
            y_roots.add(y);
            z_roots.add(z);
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            size++;
        }
    }

    void check() {
        double h = 0.1;
        double a = 0;
        double b = 1;
        double x0 = 0;
        double y0 = 0;
        Main.Function2<Double, Double, Double> my_func = (x, y) -> (x + y) * (x + y);
        Function<Double, Double> real_f = (x) -> Math.tan(x) - x;
        RungeKuttaMethod tmp_rkm = new RungeKuttaMethod(h, a, b, x0, y0, my_func, real_f);
        double[] four_roots = new double[4];
        tmp_rkm.check(4, four_roots);
        System.out.println("Roots len: " + four_roots.length);
        double prev_x = x0;
        double prev_y = four_roots[3];
        List<Double> roots = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            roots.add(four_roots[i]);
        }
        int size = roots.size() - 1;
        while (prev_x <= b) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + (size - 3) + ", x: " + prev_x + ", y: " + roots.get(size - 3)
                    + ", f: " + my_func.apply(x0 + (size - 3) * h, roots.get(size - 3))
                    + ", real y: " + r + ", eps: " + Math.abs(r - roots.get(size - 3)));
            double y = prev_y + (55 * my_func.apply(x0 + size * h, roots.get(size))
                    - 59 * my_func.apply(x0 + (size - 1) * h, roots.get(size - 1))
                    + 37 * my_func.apply(x0 + (size - 2) * h, roots.get(size - 2))
                    - 9 * my_func.apply(x0 + (size - 3) * h, roots.get(size - 3))) * h / 24;
            roots.add(y);
            prev_x = prev_x + h;
            prev_y = y;
            size++;
        }
    }
}
