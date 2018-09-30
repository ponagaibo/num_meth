package lab5;

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
    double a = 1;
    double left = 0;
    double right = 1;
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

    Function<Double, Double> phi0 = (x) -> 0.; // function
    Function<Double, Double> phiN = (x) -> 1.; // function
    Function<Double, Double> psi = (x) -> x + Math.sin(Math.PI * x);
    Lab5.Function2<Double, Double, Double> f = (x, t) -> 0.;

    double h = 0.02;
    double tau = 0.25;
    // a * t / h^2 <= 1/2!!
    Lab5.Function2<Double, Double, Double> analyticSolution = (x, t) -> x + Math.exp(-Math.PI * Math.PI * a * t) * Math.sin(Math.PI * x);

    Lab5 lab5 = new Lab5();
//        System.out.println("~~~ Explicit Finite Difference Method  ~~~");
//        lab5.lab5_efdm(a, left, right, phi0, phiN, psi, h, tau, analyticSolution);
//        System.out.println("~~~ Implicit Finite Difference Method  ~~~");
//        lab5.lab5_ifdm(a, left, right, phi0, phiN, psi, h, tau, analyticSolution);
        System.out.println("~~~ Crank-Nicolson Method  ~~~");
        lab5.lab5_cnm(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }
}
