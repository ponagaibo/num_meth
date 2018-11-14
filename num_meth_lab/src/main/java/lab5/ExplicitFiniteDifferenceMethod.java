package lab5;

import java.util.Map;
import java.util.TreeMap;
import static lab5.Lab5.arrayNorm;
import static lab5.Lab5.printArray;
import static lab5.Lab5.printMap;

public class ExplicitFiniteDifferenceMethod extends ParabolicMethods {
    int k;
    double tau;
    double h;

    ExplicitFiniteDifferenceMethod(int n, double t) {
        super(n, t);
    }

    double[] solve(int approx) {
        if (approx == 1) {
            System.out.println("\nApproximation accuracy: 1, points: 2");
        } else if (approx == 2) {
            System.out.println("\nApproximation accuracy: 2, points: 3");
        } else if (approx == 3) {
            System.out.println("\nApproximation accuracy: 2, points: 2");
        }
        int points = valueN;
        h = right / points;
        double endTime = valueT;//Math.PI; // 2pi!!!
        int times = (int) Math.ceil(2 * endTime * a / (h * h));
        k = times;
        tau = endTime / times;
        double sigma = a * tau / (h * h);
        System.out.println("h = " + h + ", delta = " + right + ", N = " + points + ", K = " + times
                + ", tau = " + tau + ", sigma = " + sigma);

        double[] previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];
        double[] realSolution = new double[points + 1];

        fullSolution = new TreeMap<>();

        // t = 0
        double time = 0;
        Double[][] tempDD = new Double[2][points + 1];
        for (int i = 0; i < points + 1; i++) {
            currentSolution[i] = psi.apply(i * h);
            tempDD[0][i] = i * h;
            tempDD[1][i] = currentSolution[i];
        }
        fullSolution.put(time, tempDD);

        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(i * h, time * tau);
        }

        // t++
        double minError = Integer.MAX_VALUE;
        double minErrorTime = 0.0;
        double maxError = 0.0;
        double maxErrorTime = 0.0;
        for (time = 1; time <= times; time++) {
            tempDD = new Double[2][points + 1];

            if (time % 1000 == 0) {
                System.out.println("time = " + time);
            }
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            currentSolution[0] = phi0.apply(time * tau);
            tempDD[0][0] = 0.0;
            tempDD[1][0] = currentSolution[0];
            for (int j = 1; j < points; j++) {
                currentSolution[j] = sigma * previousSolution[j + 1] + (1 - 2 * sigma) * previousSolution[j]
                        + sigma * previousSolution[j - 1] + tau * f.apply(j * h, time * tau);
                tempDD[0][j] = j * h;
                tempDD[1][j] = currentSolution[j];
            }
            if (approx == 1) {
                currentSolution[points] = h * phiN.apply(time * tau) + currentSolution[points - 1];
            } else if (approx == 2) {
                currentSolution[points] = 2.0 / 3.0 * h * phiN.apply((time + 1) * tau)
                        + 4.0 / 3.0 * currentSolution[points - 1]
                        - 1.0 / 3.0 * currentSolution[points - 2];
            } else if (approx == 3) {
                double denom = 2.0 * tau + h * h;
                currentSolution[points] = 2.0 * tau * h / denom * phiN.apply((time + 1) * tau)
                        + 2.0 * tau / denom * currentSolution[points - 1]
                        + h * h / denom * previousSolution[points]
                        + tau * h * h / denom * f.apply(points * h, (time + 1) * tau);
            }
            tempDD[0][points] = points * h;
            tempDD[1][points] = currentSolution[points];
            fullSolution.put(time, tempDD);
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
//        printMap(fullSolution, tau);
        System.out.println("\nMin error: " + minError + " at time = " + minErrorTime + ", max error: " + maxError + " at time = " + maxErrorTime);
        return currentSolution;
    }

    Map<Double, Double[][]> getFullSolution() {
        return fullSolution;
    }

    public int getK() {
        return k;
    }

    public double getTau() {
        return tau;
    }

    public double getH() {
        return h;
    }
}
