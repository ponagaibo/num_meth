package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class EulerMethod {
    private double h;
    private double a;
    private double b;
    private double x0;
    private double y0;
    private double z0;

    private Main.Function3<Double, Double, Double, Double> my_func1;
    private Main.Function3<Double, Double, Double, Double> my_func2;
    private Function<Double, Double> real_f;

    EulerMethod(double h, double a, double b, double x0, double y0, double z0,
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


    public List<Double> eulerMethod(double h0) {
        List<Double> roots = new ArrayList<>();
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            roots.add(prev_y);
            double r = real_f.apply(prev_x);
//            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
//                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y + h0 * my_func1.apply(prev_x, prev_y, prev_z);
            double z = prev_z + h0 * my_func2.apply(prev_x, prev_y, prev_z);
            prev_x = prev_x + h0;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
//        System.out.println("Roots size: " + roots.size());
        return roots;
    }

    public double eulerMethod(double h0, double x_last, double y_last, double z_last) {
        double prev_x = x_last;
        double prev_y = y_last;
        double prev_z = z_last;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            double r = real_f.apply(prev_x);
//            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
//                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y + h0 * my_func1.apply(prev_x, prev_y, prev_z);
            double z = prev_z + h0 * my_func2.apply(prev_x, prev_y, prev_z);
            prev_x = prev_x + h0;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
//        System.out.println("Roots size: " + roots.size());
        return prev_y;
    }

    public double reversedEulerMethod(double h0, double x_last, double y_last, double z_last) {
        List<Double> roots = new ArrayList<>();
        double prev_x = x_last;
        double prev_y = y_last;
        double prev_z = z_last;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. >= a) {
            roots.add(prev_y);
            double r = real_f.apply(prev_x);
//            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
//                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y - h0 * my_func1.apply(prev_x, prev_y, prev_z);
            double z = prev_z - h0 * my_func2.apply(prev_x, prev_y, prev_z);
            prev_x = prev_x - h0;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
        double check = prev_z + 2 * prev_y;
        return prev_y;
    }

    public void reversedEulerMethod(double h0, double x_last, double y_last, double z_last, boolean compare) {
        double prev_x = x_last;
        double prev_y = y_last;
        double prev_z = z_last;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. >= a) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y - h0 * my_func1.apply(prev_x, prev_y, prev_z);
            double z = prev_z - h0 * my_func2.apply(prev_x, prev_y, prev_z);
            prev_x = prev_x - h0;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
    }

    public void eulerMethodWithPrint(double h0, double user_y, double user_z) {
        double prev_x = x0;
        double prev_y = user_y;
        double prev_z = user_z;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y + h0 * my_func1.apply(prev_x, prev_y, prev_z);
            double z = prev_z + h0 * my_func2.apply(prev_x, prev_y, prev_z);
            prev_x = prev_x + h0;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
    }

    public static void check() {
        double h = 0.1;
        double a = 0;
        double b = 0.5;
        Main.Function2<Double, Double, Double> my_func = (x, y) -> (x + y) * (x + y);
        double prev_x = 0;
        double prev_y = 0;
        Function<Double, Double> real_f = (x) -> Math.tan(x) - x;
        int cnt = 0;
        while (((int) (prev_x * 100)) / 100. <= b) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y + h * my_func.apply(prev_x, prev_y);
            prev_x = prev_x + h;
            prev_y = y;
            cnt++;
        }
    }
}
