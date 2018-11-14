package lab5;

import lab1.TridiagonalAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static lab5.Lab5.arrayNorm;

public class ImplicitFiniteDifferenceMethod extends ParabolicMethods {
    int k;
    double tau;
    double h;

    ImplicitFiniteDifferenceMethod(int n, double t) {
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
        double endTime = valueT;//0.5;//Math.PI; // 2pi!!!
        int times = (int) Math.ceil(2 * endTime * a / (h * h));
        k = times;
        tau = endTime / times;
        double sigma = a * tau / (h * h);

        System.out.println("h = " + h + ", N = " + points + ", K = " + times
                + ", tau = " + tau + ", sigma = " + sigma);

        double[] previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];
        double[] realSolution = new double[points + 1];
        fullSolution = new HashMap<>();

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
        for (time = 0; time < times; time++) {
            tempDD = new Double[2][points + 1];
            if (time % 10000 == 0) {
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

            d_terms[0] = -previousSolution[1]
                    - sigma * phi0.apply((time + 1) * tau)
                    - tau * f.apply(h, time * tau);
            for (int i = 2; i < dim; i++) {
                d_terms[i - 1] = -previousSolution[i]
                        - tau * f.apply(h * i, time * tau);
            }

            if (approx == 1) {
                d_terms[dim - 1] = -previousSolution[points - 1]
                        - h * sigma * phiN.apply((time + 1) * tau)
                        - tau * f.apply(h * (points - 1), time * tau);
            } else if (approx == 2) {
                d_terms[dim - 1] = -previousSolution[points - 1]
                        - 2.0 / 3.0 * h * sigma * phiN.apply((time + 1) * tau)
                        - tau * f.apply(h * (points - 1), time * tau);
            } else if (approx == 3) {
                double denom = 2.0 * tau + h * h;
                d_terms[dim - 1] = -previousSolution[points - 1]
                        - sigma * h * h / denom * previousSolution[points]
                        - 2.0 * h * tau * sigma / denom * phiN.apply((time + 1) * tau)
                        - tau * f.apply(h * (points - 1), time * tau)
                        - sigma * tau * h * h / denom * f.apply(h * points, (time + 1) * tau);
            }

            TridiagonalAlgorithm toCopy = new TridiagonalAlgorithm(dim, a_terms, b_terms, c_terms, d_terms);
            toCopy.algo();
            toCopy.getSolving(currentSolution, 1);
/*            if (time > 40000) {
                System.out.println("0");
            }*/
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

/*            if (time > 40000) {
                System.out.println("1");
            }*/
            for (int i = 0; i < points + 1; i++) {
                tempDD[0][i] = i * h;
                tempDD[1][i] = currentSolution[i];
            }
/*            if (time > 40000) {
                System.out.println("2");
            }*/
//            System.arraycopy(currentSolution, 0, tempDD[1], 0, currentSolution.length);
//            System.out.println("\ntime = " + (time + 1) * tau);
//            printArray(currentSolution);

            for (int i = 0; i < points; i++) {
                realSolution[i] = analyticSolution.apply(i * h, (time + 1) * tau);
            }
/*            if (time > 40000) {
                System.out.println("3");
            }*/
//            System.out.println("Real solution:");
//            printArray(realSolution);
            double eps = arrayNorm(currentSolution, realSolution);
//            System.out.println("Error: " + eps);
            if (eps < minError) {
                minError = eps;
                minErrorTime = time * tau;
            }
            if (eps > maxError) {
                maxError = eps;
                maxErrorTime = time * tau;
            }
            fullSolution.put(time + 1, tempDD);
/*            if (time > 40000) {
                System.out.println("4");
            }*/
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
