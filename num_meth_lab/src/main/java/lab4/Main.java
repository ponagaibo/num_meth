package lab4;

import java.io.FileNotFoundException;
import java.util.function.Function;

public class Main {
    interface Function3<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    public static void main(String[] args) throws FileNotFoundException {
        double cauchy_h = 0.1;
        double cauchy_a = 0;
        double cauchy_b = 1;
        double cauchy_x0 = 0;
        double cauchy_y0 = 1;
        double cauchy_z0 = 0;
        Function3<Double, Double, Double, Double> cauchy_func1 = (x, y, z) -> z;
        Function3<Double, Double, Double, Double> cauchy_func2 = (x, y, z) -> 4 * x * z - y * (4 * x * x - 3) + Math.exp(x * x);
        Function<Double, Double> cauchy_real_f = (x) -> Math.exp(x * x) * (Math.exp(x) + Math.exp(-x) - 1);
        Lab4 l4 = new Lab4();

/*
        EulerMethod ch = new EulerMethod(cauchy_h, cauchy_a, cauchy_b, cauchy_x0, cauchy_y0, cauchy_z0, cauchy_func1, cauchy_func2, cauchy_real_f);
        ch.eulerMethodWithPrint(cauchy_h, cauchy_y0, cauchy_z0);
        System.out.println();
        double tmp_y = Math.exp(1) * (Math.exp(1) + Math.exp(-1) - 1);
        double tmp_z = 2 * Math.exp(1) * (Math.exp(1) + Math.exp(-1) - 1) + Math.exp(1) * (Math.exp(1) - Math.exp(-1));
        ch.reversedEulerMethod(cauchy_h, cauchy_b, tmp_y, tmp_z, true);
*/


        l4.lab4_1(cauchy_h, cauchy_a, cauchy_b, cauchy_x0, cauchy_y0, cauchy_z0, cauchy_func1, cauchy_func2, cauchy_real_f);

        double boundary_h = 0.05;
        double boundary_a = 0.;
        double boundary_b = 1.;
        double boundary_y_a = 2.;
        double boundary_y_b = 3. + Math.PI / 2.;

        Function<Double, Double> p_x = (x) -> 0.;
        Function<Double, Double> q_x = (x) -> -2. / (x * x + 1.);
        Function<Double, Double> f_x = (x) -> 0.;

        Function<Double, Double> boundary_real = (x) -> x * x + x + 1. + (x * x + 1.) * Math.atan(x);
        Function3<Double, Double, Double, Double> boundary_func1 = (x, y, z) -> z;
        Function3<Double, Double, Double, Double> boundary_func2 = (x, y, z) -> 2. * y / (x * x + 1.);

/*
// my function
        double boundary_h = 0.1;
        double boundary_a = -2.;
        double boundary_b = 0.;
        double y_a = -9.;
        double y_b = 1.;
        Function<Double, Double> p_x = (x) -> 4. * x / (2. * x + 1);
        Function<Double, Double> q_x = (x) -> -4. / (2. * x + 1);
        Function<Double, Double> f_x = (x) -> 0.;
        Function<Double, Double> boundary_real = (x) -> 3. * x + Math.exp(-2. * x);
*/

        l4.lab4_2_df(boundary_h, boundary_a, boundary_b, boundary_y_a, boundary_y_b, p_x, q_x, f_x, boundary_real);


        // shooting method
        double sm_h = 0.05;
        double sm_a = -2.;
        double sm_b = 0.;
        double y_a = -6. + Math.exp(4.);
        double y_b = 1.;
        Function3<Double, Double, Double, Double> sm_func1 = (x, y, z) -> z;
        Function3<Double, Double, Double, Double> sm_func2 = (x, y, z) -> 4. * (y - x * z) / (2. * x + 1);
        Function<Double, Double> sm_real = (x) -> 3. * x + Math.exp(-2. * x);

//        double sm_h = 0.05;
//        double sm_a = 0.;
//        double sm_b = 1.;
//        double y_a = -1.;
//        double y_b = 3.;
//        Function3<Double, Double, Double, Double> sm_func1 = (x, y, z) -> z;
//        Function3<Double, Double, Double, Double> sm_func2 = (x, y, z) -> 4. * (y - x * z) / (2. * x + 1);
//        Function<Double, Double> sm_real = (x) -> x + Math.exp(-2. * x);

        l4.lab4_2_sm(sm_h, boundary_a, boundary_b, boundary_y_a, boundary_y_b, boundary_func1, boundary_func2, boundary_real);

    }
}
