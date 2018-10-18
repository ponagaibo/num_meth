package lab5;

import java.io.FileNotFoundException;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//    double a = 1.;
//    double left = 0.;
//    double right = Math.PI / 2.0;
    System.out.println("~~~~~~~ Parabolic partial differential equation ~~~~~~~");

//    Function<Double, Double> phi0 = (t) -> Math.sin(t);
//    Function<Double, Double> phiN = (t) -> -Math.sin(t);
//    Function<Double, Double> psi = (x) -> 0.0;
//    Lab5.Function2<Double, Double, Double> f = (x, t) -> Math.cos(x) * (Math.cos(t) + Math.sin(t));

//    double h = 0.25; // 0.25
//    double tau = 0.05; // 0.03
    int n = 8;
    double t = 1.5;

//    Lab5.Function2<Double, Double, Double> analyticSolution = (x, t) -> Math.sin(t) * Math.cos(x);

    Lab5 lab5 = new Lab5();
//        System.out.println("\n\n~~~ Explicit Finite Difference Method  ~~~");
//        lab5.lab5_efdm(h, tau);
//        System.out.println("\n\n~~~ Implicit Finite Difference Method  ~~~");
//        lab5.lab5_ifdm(h, tau);
//        System.out.println("\n\n~~~ Crank-Nicolson Method  ~~~");
//        lab5.lab5_cnm(n, t, 1);
    }
}
