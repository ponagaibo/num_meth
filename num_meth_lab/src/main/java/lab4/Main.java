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
        double koshi_h = 0.1;
        double koshi_a = 0;
        double koshi_b = 1;
        double koshi_x0 = 0;
        double koshi_y0 = 1;
        double koshi_z0 = 0;
        Function3<Double, Double, Double, Double> koshi_func1 = (x, y, z) -> z;
        Function3<Double, Double, Double, Double> koshi_func2 = (x, y, z) -> 4 * x * z - y * (4 * x * x - 3) + Math.exp(x * x);
        Function<Double, Double> koshi_real_f = (x) -> Math.exp(x * x) * (Math.exp(x) + Math.exp(-x) - 1);
        Lab4 l4 = new Lab4();
        l4.lab4_1(koshi_h, koshi_a, koshi_b, koshi_x0, koshi_y0, koshi_z0, koshi_func1, koshi_func2, koshi_real_f);

        double boundary_h = 0.2;
        double boundary_a = -2;
        double boundary_b = 0;
        double alpha = 2;
        double beta = 0;
        double y_a = -9;
        double y_b = 1;
        Function<Double, Double> p_x = (x) -> 4 * x / (2 * x + 1);
        Function<Double, Double> q_x = (x) -> -4 / (2 * x + 1);
        Function<Double, Double> f_x = (x) -> 0.;
        Function<Double, Double> boundary_real = (x) -> 3 * x + Math.exp(-2 * x);
        l4.lab4_2(boundary_h, boundary_a, boundary_b, alpha, beta, y_a, y_b, p_x, q_x, f_x, boundary_real);

    }
}
