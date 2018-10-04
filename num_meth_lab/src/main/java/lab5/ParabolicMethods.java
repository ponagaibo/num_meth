package lab5;

import java.io.FileNotFoundException;
import java.util.function.Function;

public abstract class ParabolicMethods {
    protected static double a;
    protected static double left;
    protected static double right;
    protected static Function<Double, Double> phi0; // function
    protected static Function<Double, Double> phiN; // function
    protected static Function<Double, Double> psi; // (x) -> x + Math.sin(Math.PI * x);
    protected Lab5.Function2<Double, Double, Double> f;
    protected static double h;
    protected static double tau;
    Lab5.Function2<Double, Double, Double> analyticSolution;

    ParabolicMethods(double a, double left, double right, Function<Double, Double> phi0, Function<Double, Double> phiN,
                     Function<Double, Double> psi, Lab5.Function2<Double, Double, Double> f, double h, double tau,
                     Lab5.Function2<Double, Double, Double> analyticSolution) {
        this.a = a;
        this.left = left;
        this.right = right;
        this.phi0 = phi0;
        this.phiN = phiN;
        this.psi = psi;
        this.f = f;
        this.h = h;
        this.tau = tau;
        this.analyticSolution = analyticSolution;
    }

    abstract double[] solve(int approx) throws FileNotFoundException;

}
