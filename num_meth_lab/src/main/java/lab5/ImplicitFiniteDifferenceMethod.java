package lab5;

import lab1.TridiagonalAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

import static lab5.Lab5.arrayNorm;
import static lab5.Lab5.printArray;

public class ImplicitFiniteDifferenceMethod extends ParabolicMethods{

    ImplicitFiniteDifferenceMethod(double a, double left, double right, Function<Double, Double> phi0,
                                   Function<Double, Double> phiN, Function<Double, Double> psi,
                                   Lab5.Function2<Double, Double, Double> f, double h, double tau,
                                   Lab5.Function2<Double, Double, Double> analyticSolution) {
        super(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }

    /// !!!!!!!
    /// when add approximation do not forget about boundary condition!!!
    /// !!!!!!!

    // TODO: fix problem with time: time=0 with psi, u_i already for time+1 e.g. from 1

    double[] solve(int approx) throws FileNotFoundException {
        double endTime = Math.PI; // 2pi!!!
        int points = 15;
        h = right / points;
        int kk = (int)Math.ceil(2 * endTime * points * points / (a * right * right));
        tau = endTime / kk;

        int times = (int)Math.ceil(endTime / tau) + 1;
        double sigma = a * tau / (h * h);
        System.out.println("h = " + h + ", delta = " + (right - left) + ", N = " + points + ", K = " + kk
                + ", tau = " + tau + ", sigma = " + sigma);

        double[] previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];
        double[] realSolution = new double[points + 1];

        // t = 0
        double time = 0;
        for (int i = 0; i < previousSolution.length; i++) {
            previousSolution[i] = psi.apply(i * h);
        }
        String filename = "tridiagonal_matrix_5_2.txt";
        String dir = "./src/main/java/lab5/";
        File file = new File(dir, filename);

        double minError = Integer.MAX_VALUE;
        double minErrorTime = 0.0;
        double maxError = 0.0;
        double maxErrorTime = 0.0;
        for (time = 0; time < times; time++) { // !!! times
            if (time % 25 == 0) {
                System.out.println("time = " + time);
            }
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            try (FileWriter fw = new FileWriter(file, false)) {
                fw.append("").append(String.valueOf(points - 1)).append("\n");
                fw.append("").append(String.valueOf(-(1 + 2 * sigma))).append(" ");
                fw.append("").append(String.valueOf(sigma)).append("\n");
                for (int i = 2; i < points - 1; i++) {
                    fw.append("").append(String.valueOf(sigma)).append(" ");
                    fw.append("").append(String.valueOf(-(1 + 2 * sigma))).append(" ");
                    fw.append("").append(String.valueOf(sigma)).append("\n");
                }
                if (approx == 1) {
                    fw.append("").append(String.valueOf(sigma)).append(" ");
                    fw.append("").append(String.valueOf(-(1 + sigma))).append("\n");

                } else if (approx == 2) {
                    fw.append("").append(String.valueOf(2.0 / 3.0 * sigma)).append(" ");
                    fw.append("").append(String.valueOf(-(1 + 2.0 / 3.0 * sigma))).append("\n");
                }

                // d_j
                fw.append("").append(String.valueOf(-previousSolution[1]
                        - sigma * phi0.apply((time + 1) * tau)
                        - tau * f.apply(h, time * tau))).append(" "); // d_1 cs[0] is u_1
                for (int i = 2; i < points - 1; i++) {
                    fw.append("").append(String.valueOf(-previousSolution[i]
                            - tau * f.apply(h * i, time * tau))).append(" ");
                }
                if (approx == 1) {
                    fw.append("").append(String.valueOf(-previousSolution[points - 1]
                            - h * sigma * phiN.apply((time + 1) * tau)
                            - tau * f.apply(h * (points - 1), time * tau))).append("\n"); // d_N-1
                } else if (approx == 2) {
                    fw.append("").append(String.valueOf(-previousSolution[points - 1]
                            - 2.0 / 3.0 * h * sigma * phiN.apply((time + 1) * tau)
                            - tau * f.apply(h * (points - 1), time * tau))).append("\n"); // d_N-1
                }
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            TridiagonalAlgorithm tridig_alg = new TridiagonalAlgorithm();
            tridig_alg.readData(dir.concat(filename));
            tridig_alg.algo();
            tridig_alg.getSolving(currentSolution, 1);
            currentSolution[0] = phi0.apply((time + 1) * tau);
            if (approx == 1) {
                currentSolution[points] = h * phiN.apply((time + 1) * tau) + currentSolution[points - 1];
            } else if (approx == 2) {
                currentSolution[points] = 2.0 / 3.0 * h * phiN.apply((time + 1) * tau)
                        + 4.0 / 3.0 * currentSolution[points - 1]
                        - 1.0 / 3.0 * currentSolution[points - 2];
            }
//            System.out.println("\ntime = " + time * tau);
//            Lab5.printArray(currentSolution);

            for (int i = 0; i < points; i++) {
                realSolution[i] = analyticSolution.apply(left + i * h, (time + 1) * tau);
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
