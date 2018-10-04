package lab5;

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
    double a = 1.;
    double left = 0.;
    double right = Math.PI / 2.0;
    System.out.println("~~~~~~~ Parabolic partial differential equation ~~~~~~~");
/*

    System.out.println("Enter parameter a: ");
    Scanner scan = new Scanner(System.in).useLocale(Locale.US);;
    if (scan.hasNextDouble()) {
        a = scan.nextDouble();
    }
    System.out.println("Your equation:\ndu/dt = " + a + "d^2u/dx^2");
    System.out.println("Enter left and right points: ");
    if (scan.hasNextDouble()) {
        left = scan.nextDouble();
    }
    if (scan.hasNextDouble()) {
        right = scan.nextDouble();
    }
*/

    Function<Double, Double> phi0 = (t) -> Math.sin(t);
    Function<Double, Double> phiN = (t) -> -Math.sin(t);
    Function<Double, Double> psi = (x) -> 0.0;
    Lab5.Function2<Double, Double, Double> f = (x, t) -> Math.cos(x) * (Math.cos(t) + Math.sin(t));

    double h = 0.25; // 0.25
    double tau = 0.05; // 0.03
    // a * t / h^2 <= 1/2!!
    Lab5.Function2<Double, Double, Double> analyticSolution = (x, t) -> Math.sin(t) * Math.cos(x);

    Lab5 lab5 = new Lab5();
//        System.out.println("~~~ Explicit Finite Difference Method  ~~~");
//        lab5.lab5_efdm(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
//        System.out.println("~~~ Implicit Finite Difference Method  ~~~");
//        lab5.lab5_ifdm(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
        System.out.println("~~~ Crank-Nicolson Method  ~~~");
        lab5.lab5_cnm(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }
}
