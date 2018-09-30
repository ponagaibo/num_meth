package lab4;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Lab4 {
    void lab4_1(double h, double a, double b, double x0, double y0, double z0,
                       Main.Function3<Double, Double, Double, Double> f1,
                       Main.Function3<Double, Double, Double, Double> f2,
                       Function<Double, Double> real_f) {
        ArrayList<Double> solution = new ArrayList<>();
        double last_x = x0;
        while (((int) (last_x * 100)) / 100. <= b) {
            double r = real_f.apply(last_x);
            solution.add(r);
            last_x += h;
        }

        System.out.println("\n~~~ Euler method ~~~");
        EulerMethod em = new EulerMethod(h, a, b, x0, y0, z0, f1, f2, real_f);
        List<Double> e_roots = em.eulerMethod(h);
        List<Double> e_roots_half = em.eulerMethod(h / 2);
        List<Double> e_improved = new ArrayList<>();
        double xx = x0;
        for (int i = 0; i < e_roots.size(); i++) {
            double y1 = e_roots.get(i);
            double y2 = e_roots_half.get(2 * i);
            double imp = y2 + (y2 - y1);
//            System.out.println("x: " + xx + ", y(h): " + y1 + ", y(h/2): " + y2
//                    + ", imp: " + imp + ", real: " + solution.get(i));
            e_improved.add(imp);
            xx += h;
        }
        double prev_x = x0;
        for (int i = 0; i < e_improved.size(); i++) {
            double err1 = Math.abs(solution.get(i) - e_improved.get(i));
            double err2 = Math.abs(e_roots.get(i) - e_improved.get(i));
            System.out.format(Locale.US, "k: %02d  x: %.2f  real y: %.6f  y(h): %.6f  error: %.6f\n  RR: %.6f  error: %.6f\n",
                    i, prev_x, solution.get(i), e_roots.get(i), err2, e_improved.get(i), err1);
            prev_x += h;
        }

//        em.check();

        System.out.println("\n~~~ Runge Kutta method ~~~");
        RungeKuttaMethod rkm = new RungeKuttaMethod(h, a, b, x0, y0, z0, f1, f2, real_f);
        ArrayList<Double> rk_roots = rkm.rungeKuttaMethod(h);
        ArrayList<Double> rk_roots_half = rkm.rungeKuttaMethod(h / 2);
        List<Double> rk_improved = new ArrayList<>();
        xx = x0;
        for (int i = 0; i < rk_roots.size(); i++) {
            double y1 = rk_roots.get(i);
            double y2 = rk_roots_half.get(2 * i);
            double imp = y2 + (y2 - y1) / 15;
//            System.out.println("x: " + xx + ", y(h): " + y1 + ", y(h/2): " + y2
//                    + ", imp: " + imp + ", real: " + solution.get(i));
            rk_improved.add(imp);
            xx += h;
        }
        prev_x = x0;
        for (int i = 0; i < rk_improved.size(); i++) {
            double err1 = Math.abs(solution.get(i) - rk_improved.get(i));
            double err2 = Math.abs(rk_roots.get(i) - rk_improved.get(i));
            System.out.format(Locale.US, "k: %02d  x: %.2f  real y: %.6f  y(h): %.6f  error: %.6f\n  RR: %.6f  error: %.6f\n",
                    i, prev_x, solution.get(i), rk_roots.get(i), err2, rk_improved.get(i), err1);
            prev_x += h;
        }

//        rkm.check();



        System.out.println("\n~~~ Adams method ~~~");
        AdamsMethod am = new AdamsMethod(h,a,b,x0,y0,z0,f1,f2,real_f);
        ArrayList<Double> a_roots = am.adamsMethod(h);
        ArrayList<Double> a_roots_half = am.adamsMethod(h / 2);
        ArrayList<Double> a_improved = new ArrayList<>();
        xx = x0;
        for (int i = 0; i < a_roots.size(); i++) {
            double y1 = a_roots.get(i);
            double y2 = a_roots_half.get(2 * i);
            double imp = y2 + (y2 - y1) / 15;
//            System.out.println("x: " + xx + ", y(h): " + y1 + ", y(h/2): " + y2
//                    + ", imp: " + imp + ", real: " + solution.get(i));
            a_improved.add(imp);
            xx += h;
        }
        prev_x = x0;
        for (int i = 0; i < a_improved.size(); i++) {
            double err1 = Math.abs(solution.get(i) - a_improved.get(i));
            double err2 = Math.abs(a_roots.get(i) - a_improved.get(i));
            System.out.format(Locale.US, "k: %02d  x: %.2f  real y: %.6f  y(h): %.6f  error: %.6f\n  RR: %.6f  error: %.6f\n",
                    i, prev_x, solution.get(i), a_roots.get(i), err2, a_improved.get(i), err1);
            prev_x += h;
        }
//        am.check();
        System.out.println("~~~~~~~~~~~~~~~");
    }

    void lab4_2_df(double h, double a, double b, double y_a, double y_b, Function<Double, Double> p,
                   Function<Double, Double> q, Function<Double, Double> f,
                   Function<Double, Double> real) throws FileNotFoundException {
        System.out.println("\n~~~ Finite Difference method ~~~");
        FiniteDifferenceMethod fm = new FiniteDifferenceMethod(h, a, b, y_a, y_b, p, q, f, real);
        fm.finiteDifferenceMethod();
        System.out.println("~~~~~~~~~~~~~~~");

    }

    void lab4_2_sm(double h, double a, double b, double y_a, double y_b,
                   Main.Function3<Double, Double, Double, Double> f1,
                   Main.Function3<Double, Double, Double, Double> f2,
                   Function<Double, Double> real_f) {
        System.out.println("\n~~~ Shooting method ~~~");
        ShootingMethod sm = new ShootingMethod(h, a, b, y_a, y_b, f1, f2, real_f);
        sm.shootingMethod();
        System.out.println("~~~~~~~~~~~~~~~");
    }
}
