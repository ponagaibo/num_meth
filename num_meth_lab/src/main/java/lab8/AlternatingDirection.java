package lab8;

import lab1.IterativeSiedelAlgorithm;
import lab1.TridiagonalAlgorithm;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

import static lab8.Lab8.printArray;
import static lab8.Lab8.printMap;
import static lab8.Lab8.printMatrix;

public class AlternatingDirection extends Parabolic2DMethods {
    int k;
    double tau;
    double hx;
    double hy;
    double sigma_x;
    double sigma_y;

    AlternatingDirection(int n1, int n2) {
        super(n1, n2);
    }

    @Override
    double[] solve(int kk) {
        System.out.println("\n~~~ Alternating direction method ~~~");
        hx = right_x / valueN1;
        hy = right_y / valueN2;
        double minH = Math.min(hx, hy);
//        tau = 2.0 * minH * minH / a;
        if (kk == 0) {
            tau = (hx + hy) / 2.0;
            k = (int) Math.ceil(valueT / tau);
        } else {
            k = kk;
            tau = valueT / k;
        }

        sigma_x = a * tau / (2.0 * hx * hx);
        sigma_y = a * tau / (2.0 * hy * hy);
        double accur = Math.max(hx * hx, hy * hy);
        accur = Math.max(accur, tau * tau);

        System.out.println("hx = " + hx + ", hy = " + hy + ", N1 = " + valueN1 + ", N2 = " + valueN2
                + ", k = " + k + ", tau = " + tau + ", sigma_x = " + sigma_x + ", sigma_y = " + sigma_y
                + ", l1 = " + right_x + ", l2 = " + right_y + ", approx = " + accur);

        Double[][] prevSolution = new Double[valueN1 + 1][valueN2 + 1];
        Double[][] curSolution = new Double[valueN1 + 1][valueN2 + 1];
        Double[][] realSolution = new Double[valueN1 + 1][valueN2 + 1];

        fullSolution = new TreeMap<>();

        double time = 0.0;
        double maxError = -1.0;
        int errI = -1;
        int errJ = -1;
        Double[][] tempDD = new Double[valueN1 + 1][valueN2 + 1];
        for (int i = 0; i < valueN1 + 1; i++) {
            for (int j = 0; j < valueN2 + 1; j++) {
                prevSolution[i][j] = 0.0;
                curSolution[i][j] = psi.apply(i * hx, j * hy);
                tempDD[i][j] = curSolution[i][j];

                realSolution[i][j] = analyticSolution.apply(hx * i, hy * j, tau * time);
                double tmp = Math.abs(realSolution[i][j] - curSolution[i][j]);
                if (tmp > maxError) {
                    maxError = tmp;
                    errI = i;
                    errJ = j;
                }
            }
        }
        fullSolution.put(time, tempDD);
//        System.out.println("Max error = " + maxError + " at " + errI + " : " + errJ);

        int dim_x = valueN1 - 1;
        double[] a_terms_x = new double[dim_x - 1];
        double[] b_terms_x = new double[dim_x];
        double[] c_terms_x = new double[dim_x - 1];
        double[] d_terms_x = new double[dim_x];

        int dim_y = valueN2 - 1;
        double[] a_terms_y = new double[dim_y - 1];
        double[] b_terms_y = new double[dim_y];
        double[] c_terms_y = new double[dim_y - 1];
        double[] d_terms_y = new double[dim_y];

        double minError = Integer.MAX_VALUE;
        double minErrorTime = 0.0;
        maxError = 0.0;
        double maxErrorTime = 0.0;
//        System.out.println("Matrix at time " + time);
//        printMatrix(curSolution);

        double errTime = 0;
        while (time < k) {
            time += 1.0 / 2.0;
            if ((int)time % 10000 == 0) {
                System.out.println("time = " + time);
            }
            for (int i = 0; i <= valueN1; i++) {
                System.arraycopy(curSolution[i], 0, prevSolution[i], 0, curSolution[i].length);
            }

            for (int j = 1; j <= valueN2 - 1; j++) {
                int a_cnt_x = 0;
                int b_cnt_x = 0;
                int c_cnt_x = 0;

                b_terms_x[b_cnt_x++] = -(1.0 + 2.0 * sigma_x);
                c_terms_x[c_cnt_x++] = sigma_x;
                for (int i = 0; i < dim_x - 2; i++) {
                    a_terms_x[a_cnt_x++] = sigma_x;
                    b_terms_x[b_cnt_x++] = -(1.0 + 2.0 * sigma_x);
                    c_terms_x[c_cnt_x++] = sigma_x;
                }
                a_terms_x[a_cnt_x] = sigma_x;
                b_terms_x[b_cnt_x] = -(1.0 + 2.0 * sigma_x);
//                System.out.println("a_len: " + a_terms_x.length + ", b_ken: " + b_terms_x.length);

                d_terms_x[0] = -sigma_y * prevSolution[1][j + 1]
                        - (1.0 - 2.0 * sigma_y) * prevSolution[1][j]
                        - sigma_y * prevSolution[1][j - 1]
                        - tau / 2.0 * f.apply(hx * 1, hy * j, tau * time)
                        - sigma_x * phi3.apply(hy * j, tau * time);
                for (int i = 2; i < dim_x; i++) {
                    d_terms_x[i - 1] = -sigma_y * prevSolution[i][j + 1]
                            - (1.0 - 2.0 * sigma_y) * prevSolution[i][j]
                            - sigma_y * prevSolution[i][j - 1]
                            - tau / 2.0 * f.apply(hx * i, hy * j, tau * time);
                }
                d_terms_x[dim_x - 1] = -sigma_y * prevSolution[valueN1 - 1][j + 1]
                        - (1.0 - 2.0 * sigma_y) * prevSolution[valueN1 - 1][j]
                        - sigma_y * prevSolution[valueN1 - 1][j - 1]
                        - tau / 2.0 * f.apply(hx * (valueN1 - 1), hy * j, tau * time)
                        - sigma_x * phi4.apply(hy * j, tau * time);

                TridiagonalAlgorithm toCopy = new TridiagonalAlgorithm(dim_x, a_terms_x, b_terms_x, c_terms_x, d_terms_x);
                toCopy.algo();
                double[] jSolution = new double[dim_x];
                toCopy.getSolving(jSolution, 0);
//                System.out.println("Jsol len: " + jSolution.length);
//
//                System.out.println("Get res for all i and j = 1:");
//                printArray(jSolution);

                for (int i = 1; i <= valueN1 - 1; i++) {
//                    System.out.println("i = " + i + ", j = " + j + ", curSol[i][j] = " + curSolution[i][j]);
                    curSolution[i][j] = jSolution[i - 1];
                }
            }

            for (int i = 0; i <= valueN1; i++) {
                curSolution[i][0] = curSolution[i][1] - hy * phi1.apply(hx * i, tau * time);
                curSolution[i][valueN2] = phi2.apply(hx * i, tau * time);
            }
            for (int j = 0; j < valueN2; j++) {
                curSolution[0][j] = phi3.apply(hy * j, tau * time);
                curSolution[valueN1][j] = phi4.apply(hy * j, tau * time);
            }

//            System.out.println("Matr at time " + time);
//            printMatrix(curSolution);
            maxError = -1.0;
            errI = -1;
            errJ = -1;
//            System.out.println("Real solution: ");
            for (int i = 0; i < valueN1 + 1; i++) {
                for (int j = 0; j < valueN2 + 1; j++) {
                    realSolution[i][j] = analyticSolution.apply(hx * i, hy * j, tau * time);
                    double tmp = Math.abs(realSolution[i][j] - curSolution[i][j]);
                    if (tmp > maxError) {
                        maxError = tmp;
                        errI = i;
                        errJ = j;
                    }
                }
            }
//            printMatrix(realSolution);
//            System.out.println("Max error = " + maxError + " at " + errI + " : " + errJ);


            time += 1.0 / 2.0;
            tempDD = new Double[valueN1 + 1][valueN2 + 1];
            for (int i = 0; i <= valueN1; i++) {
                System.arraycopy(curSolution[i], 0, prevSolution[i], 0, curSolution[i].length);
            }

            for (int i = 1; i <= valueN1 - 1; i++) {
                int a_cnt_y = 0;
                int b_cnt_y = 0;
                int c_cnt_y = 0;

                b_terms_y[b_cnt_y++] = -(1 + sigma_y);
                c_terms_y[c_cnt_y++] = sigma_y;
                for (int k = 0; k < dim_y - 2; k++) {
                    a_terms_y[a_cnt_y++] = sigma_y;
                    b_terms_y[b_cnt_y++] = -(1 + 2 * sigma_y);
                    c_terms_y[c_cnt_y++] = sigma_y;
                }
                a_terms_y[a_cnt_y] = sigma_y;
                b_terms_y[b_cnt_y] = -(1 + 2 * sigma_y);

                d_terms_y[0] = -sigma_x * prevSolution[i + 1][1]
                        - (1.0 - 2.0 * sigma_x) * prevSolution[i][1]
                        - sigma_x * prevSolution[i - 1][1]
                        - tau / 2.0 * f.apply(hx * i, hy * 1, tau * time)
                        + sigma_y * hy * phi1.apply(hx * i, tau * time);
                for (int k = 2; k < dim_y; k++) {
                    d_terms_y[k - 1] = -sigma_x * prevSolution[i + 1][k]
                            - (1.0 - 2.0 * sigma_x) * prevSolution[i][k]
                            - sigma_x * prevSolution[i - 1][k]
                            - tau / 2.0 * f.apply(hx * i, hy * k, tau * time);
                }
                d_terms_y[dim_y - 1] = -sigma_x * prevSolution[i + 1][valueN2 - 1]
                        - (1.0 - 2.0 * sigma_x) * prevSolution[i][valueN2 - 1]
                        - sigma_x * prevSolution[i - 1][valueN2 - 1]
                        - tau / 2.0 * f.apply(hx * i, hy * (valueN2 - 1), tau * time)
                        - sigma_y * phi2.apply(hx * i, tau * time);

                TridiagonalAlgorithm toCopy = new TridiagonalAlgorithm(dim_y, a_terms_y, b_terms_y, c_terms_y, d_terms_y);
                toCopy.algo();
                double[] iSolution = new double[dim_y];
                toCopy.getSolving(iSolution, 0);
//                System.out.println("Jsol len: " + iSolution.length);
//                System.out.println("Get res for all i and j = 1:");
//                printArray(iSolution);

                for (int k = 1; k <= valueN2 - 1; k++) {
//                    System.out.println("i = " + i + ", j = " + k + ", curSol[i][j] = " + curSolution[i][k]);
                    curSolution[i][k] = iSolution[k - 1];
                    tempDD[i][k] = curSolution[i][k];

                }
            }

            for (int i = 0; i <= valueN1; i++) {
                curSolution[i][0] = curSolution[i][1] - hy * phi1.apply(hx * i, tau * time);
                tempDD[i][0] = curSolution[i][0];
                curSolution[i][valueN2] = phi2.apply(hx * i, tau * time);
                tempDD[i][valueN2] = curSolution[i][valueN2];
            }
            for (int j = 0; j < valueN2; j++) {
                curSolution[0][j] = phi3.apply(hy * j, tau * time);
                tempDD[0][j] = curSolution[0][j];
                curSolution[valueN1][j] = phi4.apply(hy * j, tau * time);
                tempDD[valueN1][j] = curSolution[valueN1][j];
            }
            fullSolution.put(time, tempDD);

            maxError = -1.0;
            errI = -1;
            errJ = -1;
/*            System.out.println("Matr at time " + time);
            printMatrix(curSolution);
            System.out.println("Error:");*/
            for (int i = 0; i < valueN1 + 1; i++) {
                for (int j = 0; j < valueN2 + 1; j++) {
                    realSolution[i][j] = analyticSolution.apply(hx * i, hy * j, tau * time);
                    double tmp = Math.abs(realSolution[i][j] - curSolution[i][j]);
//                    System.out.print(tmp + " ");
                    if (tmp > maxError) {
                        maxError = tmp;
                        errI = i;
                        errJ = j;
                        errTime = time;
                    }
                }
//                System.out.println();
            }
//            System.out.println("Real solution: ");
//            printMatrix(realSolution);
            System.out.println("Max error = " + maxError + " at " + errI + " : " + errJ + " at time " + errTime);



//            time = k;

        }
//        System.out.println("Max error = " + maxError + " at " + errI + " : " + errJ + " at time " + errTime);
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

    public double getHx() {
        return hx;
    }

    public double getHy() {
        return hy;
    }
}
