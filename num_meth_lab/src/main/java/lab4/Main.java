package lab4;

import java.util.function.Function;

public class Main {
    interface Function3<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    public static void main(String[] args) {
        double h = 0.1;
        double a = 0;
        double b = 1;
        double x0 = 0;
        double y0 = 1;
        double z0 = 0;
        Function3<Double, Double, Double, Double> my_func1 = (x, y, z) -> z;
        Function3<Double, Double, Double, Double> my_func2 = (x, y, z) -> 4 * x * z - y * (4 * x * x - 3) + Math.exp(x * x);
        Function<Double, Double> real_f = (x) -> Math.exp(x * x) * (Math.exp(x) + Math.exp(-x) - 1);
        Lab4 l4 = new Lab4();
        l4.lab4_1(h, a, b, x0, y0, z0, my_func1, my_func2, real_f);
    }
}
