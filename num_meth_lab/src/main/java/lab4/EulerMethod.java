package lab4;

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

    public void eulerMethod() {
        double prev_x = x0;
        double prev_y = y0;
        double prev_z = z0;
        int cnt = 0;
        while (prev_x <= b) {
            double r = real_f.apply(prev_x);
            System.out.println("k: " + cnt + ", x: " + prev_x + ", y: " + prev_y + ", real y: " + r
                    + ", eps: " + Math.abs(r - prev_y));
            double y = prev_y + h * my_func1.apply(prev_x, prev_y, prev_z);
            double z = prev_z + h * my_func2.apply(prev_x, prev_y, prev_z);
            prev_x = prev_x + h;
            prev_y = y;
            prev_z = z;
            cnt++;
        }
    }

    public static void check(){
        double h = 0.1;
        double a = 0;
        double b = 0.5;
        Main.Function2<Double, Double, Double> my_func = (x, y) -> (x + y) * (x + y);
        double prev_x = 0;
        double prev_y = 0;
        Function<Double, Double> real_f = (x) -> Math.tan(x) - x;
        int cnt = 0;
        while (prev_x <= b) {
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
