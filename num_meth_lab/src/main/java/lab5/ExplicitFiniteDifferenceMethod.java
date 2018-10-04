package lab5;

import java.util.function.Function;

import static lab5.Lab5.arrayNorm;
import static lab5.Lab5.printArray;

public class ExplicitFiniteDifferenceMethod extends ParabolicMethods{

    ExplicitFiniteDifferenceMethod(double a, double left, double right, Function<Double, Double> phi0,
                                   Function<Double, Double> phiN, Function<Double, Double> psi,
                                   Lab5.Function2<Double, Double, Double> f, double h, double tau,
                                   Lab5.Function2<Double, Double, Double> analyticSolution) {
        super(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }

    // TODO: add other approximations; remove h and tau from constructors; cases of approx
    // it is works, there is only 1d2p
    double[] solve(int approx) {
        int points = 15;
        h = right / points;
        double endTime = 2 * Math.PI; // 2pi!!!
        int kk = (int)Math.ceil(2 * endTime * points * points / (a * right * right));
        tau = endTime / kk;
        int times = (int)Math.ceil(endTime / tau) + 1;
        double sigma = a * tau / (h * h);
        System.out.println("h = " + h + ", delta = " + (right - left) + ", N = " + points + ", K = " + kk
                + ", tau = " + tau + ", sigma = " + sigma);

        double[] previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];

        // t = 0
        double time = 0;
        System.out.println("Points:");
        for (int i = 0; i < points + 1; i++) {
            currentSolution[i] = psi.apply(i * h);
            System.out.println("i: " + i + ", " + i * h);
        }
        System.out.println("\nt = 0:");
        printArray(currentSolution);
        double[] realSolution = new double[points + 1];
        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(i * h, time * tau);
        }
        System.out.println("Real solution:");
        printArray(realSolution);

        // t++
        double minError = Integer.MAX_VALUE;
        double minErrorTime = 0.0;
        double maxError = 0.0;
        double maxErrorTime = 0.0;
        for (time = 1; time < times; time++) {
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            currentSolution[0] = phi0.apply(time * tau);
            for (int j = 1; j < points; j++) {
                currentSolution[j] = sigma * previousSolution[j + 1] + (1 - 2 * sigma) * previousSolution[j]
                        + sigma * previousSolution[j - 1] + tau * f.apply(j * h, time * tau);
            }
            if (approx == 1) {
                currentSolution[points] = h * phiN.apply(time * tau) + currentSolution[points - 1];
            } else if (approx == 2) {
                currentSolution[points] = 2.0 / 3.0 * h * phiN.apply((time + 1) * tau)
                        + 4.0 / 3.0 * currentSolution[points - 1]
                        - 1.0 / 3.0 * currentSolution[points - 2];
            }
//            System.out.println("\ntime = " + time * tau);
//            printArray(currentSolution);
            for (int i = 0; i < points + 1; i++) {
                realSolution[i] = analyticSolution.apply(i * h, time * tau);
            }
//            System.out.println("Real solution:");
//            printArray(realSolution);
            double eps = arrayNorm(currentSolution, realSolution);
//            System.out.println("Error: " + eps);
            if (eps < minError) {
                minError = eps;
                minErrorTime = time;
            }
            if (eps > maxError) {
                maxError = eps;
                maxErrorTime = time;
            }
        }
        System.out.println("Min error: " + minError + " at time = " + minErrorTime + ", max error: " + maxError + " at time = " + maxErrorTime);
        return currentSolution;
    }
}
