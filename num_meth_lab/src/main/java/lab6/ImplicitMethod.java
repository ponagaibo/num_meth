package lab6;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lab1.TridiagonalAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static lab6.Lab6.arrayNorm;
import static lab6.Lab6.printArray;

public class ImplicitMethod extends HyperbolicMethods {
    int k;
    double tau;
    double h;

    ImplicitMethod(int n, double a, double s) {
        super(n, a, s);
    }

    @Override
    double[] solve(int approx_x, int approx_t) throws IOException, PythonExecutionException {
        System.out.println("~~~ Implicit Method ~~~");
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
//        k = (int) Math.ceil(endTime * a / h) * 10;
//        tau = endTime / k;
//        double sigma = a * a * tau * tau / (h * h);
        double sigma = valueS;
        tau = Math.sqrt(sigma) * h / a;
        k = (int) Math.round(valueT / tau);

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
        int dim = points - 1;
        double[] a_terms = new double[dim - 1];
        double[] b_terms = new double[dim];
        double[] c_terms = new double[dim - 1];
        double[] d_terms = new double[dim];
        for (time = 1; time < k; time++) {
            if (time % 1000 == 0) {
                System.out.println("time = " + time);
            }
            System.arraycopy(previousSolution, 0, penultimateSolution, 0, currentSolution.length);
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            int a_cnt = 0;
            int b_cnt = 0;
            int c_cnt = 0;

            if (approx_x == 1) {
                b_terms[b_cnt++] = sigma / (1.0 + h) - 2.0 * sigma - 1.0;
                c_terms[c_cnt++] = sigma;
            } else if (approx_x == 2) {
                b_terms[b_cnt++] = 4.0 * sigma / (3.0 + 2.0 * h) - 2.0 * sigma - 1.0;
                c_terms[c_cnt++] = sigma - sigma / (3.0 + 2.0 * h);
            } else if (approx_x == 3) {
                double denom0 = 2.0 * a * a * tau * tau + h * h + 2.0 * a * a * tau * tau * h;
                b_terms[b_cnt++] = 2.0 * a * a * tau * tau * sigma / denom0 - 2.0 * sigma - 1.0;
                c_terms[c_cnt++] = sigma;
            }
            for (int i = 0; i < dim - 2; i++) {
                a_terms[a_cnt++] = sigma;
                b_terms[b_cnt++] = -1.0 - 2.0 * sigma;
                c_terms[c_cnt++] = sigma;
            }
            if (approx_x == 1) {
                a_terms[a_cnt] = sigma;
                b_terms[b_cnt] = sigma / (1.0 - h) - 2.0 * sigma - 1.0;
            } else if (approx_x == 2) {
                a_terms[a_cnt] = sigma - sigma / (3.0 - 2.0 * h);
                b_terms[b_cnt] = 4.0 * sigma / (3.0 - 2.0 * h) - 2.0 * sigma - 1.0;
            } else if (approx_x == 3) {
                double denomN = 2.0 * a * a * tau * tau + h * h - 2.0 * a * a * tau * tau * h;
                a_terms[a_cnt] = sigma;
                b_terms[b_cnt] = 2.0 * a * a * tau * tau * sigma / denomN - 2.0 * sigma - 1.0;
            }

            if (approx_x == 1) {
                d_terms[0] = -2.0 * previousSolution[1]
                        + penultimateSolution[1]
                        + sigma * h / (1.0 + h) * phi0.apply(tau * (time + 1));
            } else if (approx_x == 2) {
                d_terms[0] = -2.0 * previousSolution[1]
                        + penultimateSolution[1]
                        + 2.0 * sigma * h / (3.0 + 2.0 * h) * phi0.apply(tau * (time + 1));
            } else if (approx_x == 3) {
                double denom0 = 2.0 * a * a * tau * tau + h * h + 2.0 * a * a * tau * tau * h;
                d_terms[0] = -2.0 * previousSolution[1]
                        + penultimateSolution[1]
                        - 2.0 * h * h * sigma / denom0 * previousSolution[0]
                        + h * h * sigma / denom0 * penultimateSolution[0]
                        + 2.0 * sigma * a * a * tau * tau * h / denom0 * phi0.apply(tau * (time +1));
            }

            for (int i = 2; i < dim; i++) {
                d_terms[i - 1] = -2.0 * previousSolution[i]
                        + penultimateSolution[i];
            }

            if (approx_x == 1) {
                d_terms[dim - 1] = -2.0 * previousSolution[points - 1]
                        + penultimateSolution[points - 1]
                        - sigma * h / (1.0 - h) * phiN.apply(tau * (time + 1));
            } else if (approx_x == 2) {
                d_terms[dim - 1] = -2.0 * previousSolution[points - 1]
                        + penultimateSolution[points - 1]
                        - 2.0 * sigma * h / (3.0 - 2.0 * h) * phiN.apply(tau * (time + 1));
            } else if (approx_x == 3) {
                double denomN = 2.0 * a * a * tau * tau + h * h - 2.0 * a * a * tau * tau * h;
                d_terms[dim - 1] = -2.0 * previousSolution[points - 1]
                        + penultimateSolution[points - 1]
                        - 2.0 * h * h * sigma / denomN * previousSolution[points]
                        + h * h * sigma / denomN * penultimateSolution[points]
                        - 2.0 * sigma * a * a * tau * tau * h / denomN * phiN.apply(tau * (time +1));
            }

            TridiagonalAlgorithm toCopy = new TridiagonalAlgorithm(dim, a_terms, b_terms, c_terms, d_terms);
            toCopy.algo();
            toCopy.getSolving(currentSolution, 1);

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
