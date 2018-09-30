package lab5;

import java.util.function.Function;

import static lab5.Lab5.printArray;

public class ExplicitFiniteDifferenceMethod extends ParabolicMethods{
    /*private static double a;
    private static double left;
    private static double right;
    private static Function<Double, Double> phi0; // function
    private static Function<Double, Double> phiN; // function
    private static Function<Double, Double> psi; // (x) -> x + Math.sin(Math.PI * x);
    private static double h;
    private static double tau;
    Lab5.Function2<Double, Double, Double> analyticSolution;// = (x) -> x + Math.exp(-Math.PI * Math.PI * a * t) * Math.exp(Math.PI * x);
    // b, c, f*/
    private static double[] previousSolution;


    ExplicitFiniteDifferenceMethod(double a, double left, double right, Function<Double, Double> phi0,
                                   Function<Double, Double> phiN, Function<Double, Double> psi,
                                   Lab5.Function2<Double, Double, Double> f, double h, double tau,
                                   Lab5.Function2<Double, Double, Double> analyticSolution) {
        super(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }

    double[] solving() {
        int points = (int)Math.ceil((right - left) / h) + 1;
        int times = (int)Math.ceil(1 / tau) + 1;
        System.out.println("h = " + h + ", delta = " + (right - left) + ", N = " + points);
        previousSolution = new double[points];
        double[] currentSolution = new double[points];
        // t = 0
        double time = 0;
        currentSolution[0] = phi0.apply(time);
        currentSolution[points - 1] = phiN.apply(time);
        System.out.println("i: 0, " + phi0.apply(time) + " ");
        for (int i = 1; i < points - 1; i++) {
            currentSolution[i] = psi.apply(i * h);
            System.out.println("i: " + i + ", " + i * h);
        }
        System.out.println("i: " + (points - 1) + ", " + phiN.apply(time) + " ");
        System.out.println("\nt = 0:");
        printArray(currentSolution);
        double[] realSolution = new double[points];
        for (int i = 0; i < points; i++) {
            realSolution[i] = analyticSolution.apply(i * h, time * tau);
        }
        System.out.println("Real solution:");
        printArray(realSolution);
        // t++

        for (time = 1; time < times; time++) {
            double coef = tau * a / (h * h);
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);
            currentSolution[0] = phi0.apply(time * tau);
            for (int j = 1; j < points - 1; j++) {
                currentSolution[j] = coef * previousSolution[j + 1] + (1 - 2 * coef) * previousSolution[j] + coef * previousSolution[j - 1];
            }
            currentSolution[points - 1] = phiN.apply(time * tau);
            System.out.println("\ntime = " + time * tau);
            printArray(currentSolution);
            for (int i = 0; i < points; i++) {
                realSolution[i] = analyticSolution.apply(i * h, time * tau);
            }
            System.out.println("Real solution:");
            printArray(realSolution);
        }
        return currentSolution;
    }
}
