package lab5;

import lab1.TridiagonalAlgorithm;

import java.util.Map;
import java.util.TreeMap;

import static lab5.Lab5.arrayNorm;
import static lab5.Lab5.printArray;
import static lab5.Lab5.printMap;

public class CrankNicolsonMethod extends ParabolicMethods {
    int k;
    double tau;
    double h;

    CrankNicolsonMethod(int n, double t) {
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
        double endTime = valueT; //0.5;//Math.PI; // 2pi!!!
        int points = valueN;
        h = right / points;
        int times = (int) Math.ceil(endTime * a / (h * h));
        k = times;
        tau = endTime / times;
        double sigma = a * tau / (2 * h * h);
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

        int dim = points - 1;
        double[] a_terms = new double[dim - 1];
        double[] b_terms = new double[dim];
        double[] c_terms = new double[dim - 1];
        double[] d_terms = new double[dim];

        double minError = Integer.MAX_VALUE;
        double minErrorTime = 0.0;
        double maxError = 0.0;
        double maxErrorTime = 0.0;

        // for t > 0
        for (time = 0; time < times; time++) {
            tempDD = new Double[2][points + 1];
            if (time % 1000 == 0) {
                System.out.println("time = " + time);
            }
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            int a_cnt = 0;
            int b_cnt = 0;
            int c_cnt = 0;

            b_terms[b_cnt++] = -(1 + 2 * sigma);
            c_terms[c_cnt++] = sigma;

            for (int i = 0; i < dim - 2; i++) {
                a_terms[a_cnt++] = sigma;
                b_terms[b_cnt++] = -(1 + 2 * sigma);
                c_terms[c_cnt++] = sigma;
            }

            if (approx == 1) {
                a_terms[a_cnt] = sigma;
                b_terms[b_cnt] = -(1 + sigma);
            } else if (approx == 2) {
                a_terms[a_cnt] = 2.0 / 3.0 * sigma;
                b_terms[b_cnt] = -(1 + 2.0 / 3.0 * sigma);
            } else if (approx == 3) {
                double denom = 2.0 * tau + h * h;
                a_terms[a_cnt] = sigma;
                b_terms[b_cnt] = -(1 + 2.0 * sigma * (tau + h * h) / denom);
            }

            d_terms[0] = -sigma * previousSolution[0]
                    - (1 - 2 * sigma) * previousSolution[1]
                    - sigma * previousSolution[2]
                    - sigma * phi0.apply((time + 1) * tau)
                    - tau * f.apply(h, time * tau);
            for (int i = 2; i < dim; i++) {
                d_terms[i - 1] = -sigma * previousSolution[i - 1]
                        - (1 - 2 * sigma) * previousSolution[i]
                        - sigma * previousSolution[i + 1]
                        - tau * f.apply(i * h, time * tau);
            }

            if (approx == 1) {
                d_terms[dim - 1] = -sigma * previousSolution[points - 2]
                        - (1 - 2 * sigma) * previousSolution[points - 1]
                        - sigma * previousSolution[points]
                        - sigma * h * phiN.apply((time + 1) * tau)
                        - tau * f.apply((points - 1) * h, time * tau);
            } else if (approx == 2) {
                d_terms[dim - 1] = -sigma * previousSolution[points - 2]
                        - (1 - 2 * sigma) * previousSolution[points - 1]
                        - sigma * previousSolution[points]
                        - 2.0 / 3.0 * sigma * h * phiN.apply((time + 1) * tau)
                        - tau * f.apply((points - 1) * h, time * tau);
            } else if (approx == 3) {
                double denom = 2.0 * tau + h * h;
                d_terms[dim - 1] = -sigma * previousSolution[points - 2]
                        - (1 - 2 * sigma) * previousSolution[points - 1]
                        - 2.0 * sigma * (tau + h * h) / denom * previousSolution[points]
                        - tau * f.apply((points - 1) * h, time * tau)
                        - sigma * tau * h * h / denom * f.apply(h * points, (time + 1) * tau)
                        - 2.0 * sigma * tau * h / denom * phiN.apply((time + 1) * tau);
            }

            TridiagonalAlgorithm toCopy = new TridiagonalAlgorithm(dim, a_terms, b_terms, c_terms, d_terms);
            toCopy.algo();
            toCopy.getSolving(currentSolution, 1);

            currentSolution[0] = phi0.apply((time + 1) * tau);
            if (approx == 1) {
                currentSolution[points] = h * phiN.apply((time + 1) * tau) + currentSolution[points - 1];
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

            for (int i = 0; i < points + 1; i++) {
                tempDD[0][i] = i * h;
                tempDD[1][i] = currentSolution[i];
            }

//            System.out.println("\ntime = " + time * tau);
//            System.out.println("My solution");
//            Lab5.printArray(tempDD[1]);

            for (int i = 0; i < points; i++) {
                realSolution[i] = analyticSolution.apply(i * h, (time + 1) * tau);
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
            fullSolution.put(time + 1, tempDD);
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