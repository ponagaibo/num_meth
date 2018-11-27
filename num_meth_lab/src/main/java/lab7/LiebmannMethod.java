package lab7;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lab1.IterativeSiedelAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lab7.Lab7.matrixNorm;
import static lab7.Lab7.printArray;
import static lab7.Lab7.printMatrix;

public class LiebmannMethod extends EllipticMethods {
    double hx;
    double hy;

    LiebmannMethod(int n1, int n2, double aa) {
        super(n1, n2, aa);
    }

    void writeMatrix(double eps, double[][] currentSolution) {
        String filename = "test_matr.txt";
        String dir = "./src/main/java/lab7/";
        File file = new File(dir, filename);
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.append(String.valueOf(valueN1 * valueN2)).append("\n");
            for (int i = 0; i < valueN1; i++) {
                for (int j = 0; j < valueN2; j++) {
                    if (i == 0) {
                        for (int k = 0; k < valueN1 * valueN2; k++) {
                            if (k == j) {
                                fw.append("-1 ");
                            } else if (k == j + valueN2) {
                                fw.append("1 ");
                            } else {
                                fw.append("0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (j == 0) {
                        for (int k = 0; k < valueN1 * valueN2; k++) {
                            if (k == i * valueN2) {
                                fw.append("-1 ");
                            } else if (k == i * valueN2 + 1) {
                                fw.append("1 ");
                            } else {
                                fw.append("0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (i == valueN1 - 1 && j == valueN2 - 1) {
                        // 8
                        for (int k = 0; k < valueN1 * valueN2; k++) {
                            if (k == (i - 1) * valueN2 + j) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * valueN2 + j - 1) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * valueN2 + j) {
//                                fw.append("2 ");
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy))).append(" ");
                            } else {
                                fw.append("0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (j == valueN2 - 1) {
                        // 6
                        for (int k = 0; k < valueN1 * valueN2; k++) {
                            if (k == (i - 1) * valueN2 + j) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == (i + 1) * valueN2 + j) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * valueN2 + j - 1) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * valueN2 + j) {
//                                fw.append("2 ");
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy))).append(" ");
                            } else {
                                fw.append("0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (i == valueN1 - 1) {
                        for (int k = 0; k < valueN1 * valueN2; k++) {
                            if (k == (i - 1) * valueN2 + j) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * valueN2 + j - 1) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * valueN2 + j + 1) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * valueN2 + j) {
//                                fw.append("2 ");
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy))).append(" ");
                            } else {
                                fw.append("0 ");
                            }
                        }
                        fw.append("\n");
                    } else {
                        for (int k = 0; k < valueN1 * valueN2; k++) {
                            if (k == (i - 1) * valueN2 + j) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == (i + 1) * valueN2 + j) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * valueN2 + j - 1) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * valueN2 + j + 1) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * valueN2 + j) {
//                                fw.append("2 ");
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy))).append(" ");
                            } else {
                                fw.append("0 ");
                            }
                        }
                        fw.append("\n");
                    }
                }
            }
            for (int i = 0; i < valueN1; i++) {
                for (int j = 0; j < valueN2; j++) {
                    if (i == 0 && j < valueN2) {
                        // 1
//                        fw.append("x1y\n");
                        fw.append(String.valueOf(hx * phi1.apply(hy * j))).append(" ");
                    } else if (j == 0 && i < valueN1 && i > 0) {
                        // 2
//                        fw.append("y3x\n");
                        fw.append(String.valueOf(hy * phi3.apply(hx * i))).append(" ");
                    } else if (i == valueN1 - 1 && j == valueN2 - 1) {
                        // 8
//                        fw.append("xyf-yu_i+1j-xu_ij+1\n");
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j)
                                - hy * hy * currentSolution[i + 1][j]
                                - hx * hx * currentSolution[i][j + 1])).append(" ");
                    } else if (j == valueN2 - 1 && i > 0 && i <= valueN1 - 2) {
                        // 6
//                        fw.append("xyf-xu_ij+1\n");
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j)
                                - hx * hx * currentSolution[i][j + 1])).append(" ");
                    } else if (i == valueN1 - 1 && j > 0 && j <= valueN2 - 2) {
                        // 7
//                        fw.append("xyf-yu_i+1j\n");
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j)
                                - hy * hy * currentSolution[i + 1][j])).append(" ");
                    } else {
                        // 5
//                        fw.append("xyf\n");
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j))).append(" ");
                    }
                }
            }
            fw.append("\n");
            fw.append(String.valueOf(eps));

//            fw.append("").append(String.valueOf(times)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    double[] solve(int method) throws IOException, PythonExecutionException {
        valueN1 = 2; // x, str
        valueN2 = 4; // y, col
        hx = right_x / EllipticMethods.valueN1;
        hy = right_y / EllipticMethods.valueN2;
        System.out.println("hx : " + hx + ", hy: " + hy);
        double eps_write = 0.005;
        double[][] previousSolution = new double[valueN1 + 1][valueN2 + 1];
        double[][] currentSolution = new double[valueN1 + 1][valueN2 + 1];
        double[][] realSolution = new double[valueN1 + 1][valueN2 + 1];
        for (int i = 0; i < realSolution.length; i++) {
            for (int j = 0; j < realSolution[0].length; j++) {
                realSolution[i][j] = analyticSolution.apply(hx * i, hy * j);
            }
        }
        for (int j = 0; j <= valueN2; j++) {
            // 3
            currentSolution[valueN1][j] = phi2.apply(hy * j);
        }
        for (int i = 0; i < valueN1; i++) {
            currentSolution[i][valueN2] = phi4.apply(hx * i);
        }
        printMatrix(currentSolution);
        writeMatrix(eps_write, currentSolution);

        IterativeSiedelAlgorithm alg_Siedel = new IterativeSiedelAlgorithm();
        alg_Siedel.readData("./src/main/java/lab7/test_matr.txt");
        double[] res_s = alg_Siedel.solveSiedel();
//        System.out.println("Got res:");
        printArray(res_s);
        for (int i = 0; i < valueN1; i++) {
            for (int j = 0; j < valueN2; j++) {
//                System.out.println("ind: " + (i * n1 + j) + ", res_s[i * n1 + j]: " + res_s[i * n1 + j]);
                currentSolution[i][j] = res_s[i * valueN1 + j];
            }
        }
        System.out.println("After iteration method: ");
        printMatrix(currentSolution);

        for (int i = 0; i < currentSolution.length; i++) {
            System.arraycopy(currentSolution[i], 0, previousSolution[i], 0, currentSolution[i].length);
        }

        double eps_res = 0.005;
        double err = matrixNorm(currentSolution, realSolution);
        System.out.println("\nReal solution: ");
        printMatrix(realSolution);
        System.out.println("Err: " + err);
        int cnt = 0;
        if (method == 1) {
            while (err > eps_res && cnt < 90) {
                for (int i = 1; i <= valueN1 - 1; i++) {
                    for (int j = 1; j <= valueN2 - 1; j++) {
                        currentSolution[i][j] = (hy * hy * previousSolution[i + 1][j]
                                + hy * hy * previousSolution[i - 1][j]
                                + hx * hx * previousSolution[i][j + 1]
                                + hx * hx * previousSolution[i][j - 1]
                                - hx * hx * hy * hy * f.apply(hx * i, hy * j))
                                / (2.0 * (hx * hx + hy * hy));
//                        System.out.println("Last u[i][j]: " + previousSolution[i][j]);
//                        System.out.println("New u[i][j]: " + currentSolution[i][j]);
                    }
                }
                for (int j = 0; j <= valueN2 - 1; j++) {
                    currentSolution[0][j] = currentSolution[1][j] - hx * phi1.apply(hy * j);
                }
                for (int i = 1; i < valueN1 - 1; i++) {
                    currentSolution[i][0] = currentSolution[i][1] - hy * phi3.apply(hx * i);
                }
//                System.out.println("\nLiebmann solution in loop " + cnt + ":");
//                printMatrix(currentSolution);
//                err = matrixNorm(currentSolution, realSolution);
//                System.out.println("Loop err: " + err);
                for (int i = 0; i < currentSolution.length; i++) {
                    System.arraycopy(currentSolution[i], 0, previousSolution[i], 0, currentSolution[i].length);
                }
                cnt++;
            }
        }
        System.out.println("\nLiebmann solution:");
        printMatrix(currentSolution);
        System.out.println("Final err: " + err);
        return new double[0];
    }
}
