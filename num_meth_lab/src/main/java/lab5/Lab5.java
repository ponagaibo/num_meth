package lab5;

import java.io.FileNotFoundException;
import java.util.function.Function;

public class Lab5 {
    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    public static void printArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("" + array[i] + " ");
        }
        System.out.println();
    }

    void lab5_efdm(double a, double left, double right, Function<Double, Double> phi0, Function<Double, Double> phiN,
                   Function<Double, Double> psi, double h, double tau,
                   Function2<Double, Double, Double> analyticSolution) {
        ExplicitFiniteDifferenceMethod efdm = new ExplicitFiniteDifferenceMethod(a, left, right, phi0, phiN, psi, h, tau, analyticSolution);
        efdm.solving();
    }

    void lab5_ifdm(double a, double left, double right, Function<Double, Double> phi0, Function<Double, Double> phiN,
                   Function<Double, Double> psi, double h, double tau,
                   Function2<Double, Double, Double> analyticSolution) throws FileNotFoundException {
        ImplicitFiniteDifferenceMethod ifdm = new ImplicitFiniteDifferenceMethod(a, left, right, phi0, phiN, psi, h, tau, analyticSolution);
        ifdm.solving();
    }

    void lab5_cnm(double a, double left, double right, Function<Double, Double> phi0, Function<Double, Double> phiN,
                   Function<Double, Double> psi, Lab5.Function2<Double, Double, Double> f, double h, double tau,
                   Function2<Double, Double, Double> analyticSolution) throws FileNotFoundException {
        CrankNicolsonMethod cnm = new CrankNicolsonMethod(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
        cnm.solving();
    }
}
