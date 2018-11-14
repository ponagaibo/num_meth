package lab6;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static lab6.Lab6.arrayNorm;
import static lab6.Lab6.printArray;

public class ExplicitCrossMethod extends HyperbolicMethods {
    double tau;
    double h;
    int k;

    ExplicitCrossMethod(int n, Double a) {
        super(n, a);
    }

    @Override
    double[] solve(int approx_x, int approx_t) throws IOException, PythonExecutionException {
        System.out.println("~~~ Explicit Cross Method ~~~");
        if (approx_x == 1) {
            System.out.println("\nSpace variable approximation accuracy: 1, points: 2");
        } else if (approx_x == 2) {
            System.out.println("\nSpace variable approximation accuracy: 2, points: 3");
        } else if (approx_x == 3) {
            System.out.println("\nSpace variable approximation accuracy: 2, points: 2");
        }
        if (approx_t == 1) {
            System.out.println("\nTime variable approximation accuracy: 1");
        } else if (approx_t == 2) {
            System.out.println("\nTime variable approximation accuracy: 2");
        }
        int points = valueN;
        h = right / points;
        double endTime = valueT;
        k = (int) Math.ceil(endTime * a / h) + 1;
        tau = endTime / k;
        double sigma = a * a * tau * tau / (h * h);
        System.out.println("h = " + h + ", tau = " + tau + ", sigma = " + sigma +
                ", N = " + points + ", K = " + k + ", a = " + a);

        double[] penultimateSolution = new double[points + 1];
        double[] previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];
        double[] realSolution = new double[points + 1];

        fullSolution = new TreeMap<>();

        double time = 0;
        Double[][] tempDD = new Double[2][points + 1];
        for (int i = 0; i < points + 1; i++) {
            penultimateSolution[i] = 0.0;
            previousSolution[i] = 0.0;
            currentSolution[i] = psi1.apply(i * h);
            tempDD[0][i] = i * h;
            tempDD[1][i] = currentSolution[i];
        }
        fullSolution.put(time, tempDD);

//        System.out.println("\ntime = " + time * tau);
//        printArray(currentSolution);
        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(i * h, time * tau);
        }
//        System.out.println("Real solution:");
//        printArray(realSolution);
        double eps = arrayNorm(currentSolution, realSolution);
//        System.out.println("Error: " + eps);

        System.arraycopy(previousSolution, 0, penultimateSolution, 0, previousSolution.length);
        System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

        for (int j = 0; j < points + 1; j++) {
            if (approx_t == 1) {
                currentSolution[j] = tau * psi2.apply(h * j) + psi1.apply(h * j);
            } else if (approx_t == 2) {
                currentSolution[j] = psi1.apply(h * j) + tau * psi2.apply(h * j)
                        + a * a * tau * tau / 2.0 * ddpsi1.apply(h * j);
            }
        }

        tempDD = new Double[2][points + 1];
        for (int i = 0; i < points + 1; i++) {
            tempDD[0][i] = i * h;
            tempDD[1][i] = currentSolution[i];
        }
        fullSolution.put(time + 1, tempDD);
//        System.out.println("\ntime = " + (time + 1)  * tau);
//        printArray(currentSolution);
        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(i * h, (time + 1) * tau);
        }
//        System.out.println("Real solution:");
//        printArray(realSolution);
        eps = arrayNorm(currentSolution, realSolution);
//        System.out.println("Error: " + eps);

        List<Double> errors = new ArrayList<>();
        List<Double> xs = new ArrayList<>();
        double maxError = 0.0;
        double maxErrorTime = 0.0;
        for (time = 1; time < k; time++) {
            if (time % 1000 == 0) {
                System.out.println("time = " + time);
            }
            System.arraycopy(previousSolution, 0, penultimateSolution, 0, currentSolution.length);
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            for (int j = 1; j < points; j++) {
                currentSolution[j] = sigma * previousSolution[j - 1] + 2.0 * (1.0 - sigma) * previousSolution[j]
                        + sigma * previousSolution[j + 1] - penultimateSolution[j];
            }
            if (approx_x == 1) {
                currentSolution[0] = currentSolution[1] / (1.0 + h)
                        - h * phi0.apply(tau * (time + 1)) / (1.0 + h);
                currentSolution[points] = currentSolution[points - 1] / (1.0 - h)
                        + h * phiN.apply(tau * (time + 1)) / (1.0 - h);
            } else if (approx_x == 2) {
                double denom0 = 3.0 + 2.0 * h;
                currentSolution[0] = 4.0 / denom0 * currentSolution[1]
                        - 1.0 / denom0 * currentSolution[2]
                        - 2.0 * h / denom0 * phi0.apply(tau * (time + 1));
                double denomN = 3.0 - 2.0 * h;
                currentSolution[points] = 4.0 / denomN * currentSolution[points - 1]
                        - 1.0 / denomN * currentSolution[points - 2]
                        + 2.0 * h / denomN * phiN.apply(tau * (time + 1));
            } else if (approx_x == 3) {
                double denom0 = 2.0 * a * a * tau * tau + h * h + 2.0 * a * a * tau * tau * h;
                currentSolution[0] = - h * h * penultimateSolution[0] / denom0
                        + 2.0 * h * h * previousSolution[0] / denom0
                        + 2.0 * a * a * tau * tau * currentSolution[1] / denom0
                        - 2.0 * a * a * tau * tau * h * phi0.apply(tau * (time + 1)) / denom0;

                double denomN = 2.0 * a * a * tau * tau + h * h - 2.0 * a * a * tau * tau * h;
                currentSolution[points] = - h * h * penultimateSolution[points] / denomN
                        + 2.0 * h * h * previousSolution[points] / denomN
                        + 2.0 * a * a * tau * tau * currentSolution[points - 1] / denomN
                        - 2.0 * a * a * tau * tau * h * phiN.apply(tau * (time + 1)) / denomN;
            }

            tempDD = new Double[2][points + 1];
            for (int i = 0; i < points + 1; i++) {
                tempDD[0][i] = i * h;
                tempDD[1][i] = currentSolution[i];
            }
            fullSolution.put(time + 1, tempDD);
//            System.out.println("\ntime = " + (time + 1) * tau);
//            printArray(currentSolution);
            for (int i = 0; i < points + 1; i++) {
                realSolution[i] = analyticSolution.apply(i * h, (time + 1) * tau);
            }
//            System.out.println("Real solution:");
//            printArray(realSolution);
            eps = arrayNorm(currentSolution, realSolution);
            errors.add(eps);
            xs.add(1.* time);
//            System.out.println("Error: " + eps);
            if (eps > maxError) {
                maxError = eps;
                maxErrorTime = time;
            }
        }
        System.out.println("\nMax error: " + maxError + " at time = " + maxErrorTime + " => " + maxErrorTime * tau);
        Plot plt = Plot.create();
        plt.plot().add(xs ,errors);
        plt.show();
        return new double[0];
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
