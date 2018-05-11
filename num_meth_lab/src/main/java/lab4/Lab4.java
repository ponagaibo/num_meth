package lab4;

import java.util.function.Function;

public class Lab4 {
    void lab4_1(double h, double a, double b, double x0, double y0, double z0,
                       Main.Function3<Double, Double, Double, Double> f1,
                       Main.Function3<Double, Double, Double, Double> f2,
                       Function<Double, Double> real_f) {
        System.out.println("\n~~~ Euler method ~~~");
        EulerMethod em = new EulerMethod(h, a, b, x0, y0, z0, f1, f2, real_f);
//        em.eulerMethod();
        em.check();
        System.out.println("\n~~~ Runge Kutta method ~~~");
        RungeKuttaMethod rkm = new RungeKuttaMethod(h, a, b, x0, y0, z0, f1, f2, real_f);
//        rkm.rungeKuttaMethod(-1);
//        rkm.check();
        System.out.println("\n~~~ Adams method ~~~");
        AdamsMethod am = new AdamsMethod(h,a,b,x0,y0,z0,f1,f2,real_f);
//        am.adamsMethod();
//        am.check();
        System.out.println("~~~~~~~~~~~~~~~");
    }
}
