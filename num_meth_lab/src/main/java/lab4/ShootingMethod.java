package lab4;

import java.util.ArrayList;
import java.util.function.Function;

public class ShootingMethod {
    double h;
    double a;
    double b;
    double y_a;
    double y_b;

    Main.Function3<Double, Double, Double, Double> f1;
    Main.Function3<Double, Double, Double, Double> f2;
    Function<Double, Double> real_f;

    ShootingMethod(double h, double a, double b, double y_a, double y_b,
                   Main.Function3<Double, Double, Double, Double> f1,
                   Main.Function3<Double, Double, Double, Double> f2,
                   Function<Double, Double> real_f) {

        this.h = h;
        this.a = a;
        this.b = b;
        this.y_a = y_a;
        this.y_b = y_b;
        this.f1 = f1;
        this.f2 = f2;
        this.real_f = real_f;
    }

    void shootingMethod() {
        double old_slope = 1.;
        RungeKuttaMethod em = new RungeKuttaMethod(h, a, b, a, y_a, old_slope, f1, f2, real_f);
        double old_check = em.modifiedRungeKuttaMethod(h, a, y_a, old_slope);

        double slope = 1.2;
        double check = em.modifiedRungeKuttaMethod(h, a, y_a, slope);
        double eps = 0.0001;
        //System.out.println("s: " + slope + ", old_s: " + old_slope + ", c: " + check + ", old_c: " + old_check);
        int cnt = 0;
        while (Math.abs(check - y_a) >= eps && Math.abs(slope - old_slope) >= eps) {
            double new_slope = slope - (check - y_b) * (slope - old_slope) / (check - old_check);
            double new_check = em.modifiedRungeKuttaMethod(h, a, y_a, new_slope);
            old_slope = slope;
            old_check = check;
            slope = new_slope;
            check = new_check;
//            System.out.println("s: " + slope + ", old_s: " + old_slope + ", c: " + check + ", old_c: " + old_check);
            cnt++;
        }
//        System.out.println("slope: " + slope + ", check: " + old_check + ", cnt: " + cnt);
        em.rungeKuttaMethodWithPrint(h, a, y_a, slope);
    }
}
