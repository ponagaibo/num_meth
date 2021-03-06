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

    LiebmannMethod(int n1, int n2) {
        super(n1, n2);
    }

    void writeMatrix(double eps, double[][] currentSolution) {
        String filename = "test_matr_3.txt";
        String dir = "./src/main/java/lab7/";
        File file = new File(dir, filename);
        int n1 = valueN1 - 1;
        int n2 = valueN2 - 1;
        int matr_size = (n1 + 2) * n2;
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.append(String.valueOf(matr_size)).append("\n");
            // var 5
            for (int i = 0; i <= n1 + 1; i++) {
                for (int j = 1; j <= n2; j++) {
                    if (i == 0 && j >= 1) {
                        // case 1
                        for (int k = 0; k < matr_size; k++) {
                            if (k == i * n2 + (j - 1)) {
                                fw.append(String.valueOf(-1.0)).append(" ");
                            } else if (k == (i + 1) * n2 + (j - 1)) {
                                fw.append(String.valueOf(1.0)).append(" ");
                            } else {
                                fw.append("0.0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (i >= 1 && i <= n1 && j == 1) {
                        // case 2
                        for (int k = 0; k < matr_size; k++) {
                            if (k == (i + 1) * n2 + (j - 1)) {
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == (i - 1) * n2 + (j - 1)) {
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * n2 + (j + 1 - 1)) {
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * n2 + (j - 1)) {
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy) + hx * hx * hy * hy)).append(" ");
                            } else {
                                fw.append("0.0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (i >= 1 && i <= n1 && j > 1 && j < n2) {
                        // case 3!
                        for (int k = 0; k < matr_size; k++) {
                            if (k == (i - 1) * n2 + (j - 1)) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == (i + 1) * n2 + (j - 1)) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * n2 + (j - 1 - 1)) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * n2 + (j + 1 - 1)) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * n2 + (j - 1)) {
//                                fw.append("2 ");
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy) + hx * hx * hy * hy)).append(" ");
                            } else {
                                fw.append("0.0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (j == n2 && i >= 1 && i <= n1) {
                        // case 4!
                        for (int k = 0; k < matr_size; k++) {
                            if (k == (i - 1) * n2 + (j - 1)) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == (i + 1) * n2 + (j - 1)) {
//                                fw.append("y ");
                                fw.append(String.valueOf(hy * hy)).append(" ");
                            } else if (k == i * n2 + (j - 1 - 1)) {
//                                fw.append("x ");
                                fw.append(String.valueOf(hx * hx)).append(" ");
                            } else if (k == i * n2 + (j - 1)) {
//                                fw.append("2 ");
                                fw.append(String.valueOf(-2.0 * (hx * hx + hy * hy) + hx * hx * hy * hy)).append(" ");
                            } else {
                                fw.append("0.0 ");
                            }
                        }
                        fw.append("\n");
                    } else if (i == n1 + 1 && j >= 1 && j <= n2){
                        // case 5
                        for (int k = 0; k < matr_size; k++) {
                            if (k == (i - 1) * n2 + (j - 1)) {
                                fw.append(String.valueOf(-1.0)).append(" ");
                            } else if (k == i * n2 + (j - 1)) {
                                fw.append(String.valueOf(1.0 - hx)).append(" ");
                            } else {
                                fw.append("0.0 ");
                            }
                        }
                        fw.append("\n");
                    }
                }
            }

            for (int i = 0; i <= n1 + 1; i++) {
                for (int j = 1; j <= n2; j++) {
                    if (i == 0 && j >= 1) {
                        // case 1
                        fw.append(String.valueOf(hx * phi1.apply(hy * j))).append(" ");
                    } else if (i >= 1 && i <= n1 && j == 1) {
                        // case 2
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j)
                                - hx * hx * currentSolution[i][j - 1])).append(" ");
                    } else if (i >= 1 && i <= n1 && j > 1 && j < n2) {
                        // case 3
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j))).append(" ");
                    } else if (j == n2 && i >= 1 && i <= n1) {
                        // case 4
                        fw.append(String.valueOf(hx * hx * hy * hy * f.apply(hx * i, hy * j)
                                - hx * hx * currentSolution[i][j + 1])).append(" ");
                    } else if (i == n1 + 1 && j >= 1 && j <= n2){
                        // case 5
                        fw.append(String.valueOf(hx * phi2.apply(hy * j))).append(" ");
                    }
                }
            }

            fw.append("\n");
            fw.append(String.valueOf(eps));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    double[] fillDataAndSolve(double eps, double[][] currentSolution) {
        int n1 = valueN1 - 1;
        int n2 = valueN2 - 1;
        int dim = n1 * n2;
        double[][] matr = new double[dim][dim];
        double[] right = new double[dim];

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                int str = (i - 1) * n1 + j - 1;
                if (i == 1 && j == 1) {
                    // case 1
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i + 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j + 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (i == 1 && j > 1 && j < n2) {
                    // case 2
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i + 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j - 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j + 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (i == 1 && j == n2) {
                    // case 3
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i + 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j - 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (j == 1 && i > 1 && i < n1) {
                    // case 4
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i - 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i + 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j + 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (i > 1 && i < n1 && j > 1 && j < n2) {
                    // case 5
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i - 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i + 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j - 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j + 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (j == n2 && i > 1 && i < n1) {
                    // case 6
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i - 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i + 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j - 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (i == n1 && j == 1) {
                    // case 7
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i - 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j + 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (i == n1 && j > 1 && j < n2) {
                    // case 8
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i - 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j - 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j + 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                } else if (i == n1 && j == n2) {
                    // case 9
                    for (int k = 0; k < n1 * n2; k++) {
                        if (k == (i - 1 - 1) * n2 + (j - 1)) {
                            matr[str][k] = hy * hy;
                        } else if (k == (i - 1) * n2 + (j - 1 - 1)) {
                            matr[str][k] = hx * hx;
                        } else if (k == (i - 1) * n2 + (j - 1)) {
                            matr[str][k] = -2.0 * (hx * hx + hy * hy);
                        } else {
                            matr[str][k] = 0.0;
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                int str = (i - 1) * n1 + j - 1;
                if (i == 1 && j == 1) {
                    // case 1
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j);
                } else if (i == 1 && j > 1 && j < n2) {
                    // case 2
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hy * hy * currentSolution[i - 1][j];
                } else if (i == 1 && j == n2) {
                    // case 3
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hy * hy * currentSolution[i - 1][j]
                            - hx * hx * currentSolution[i][j + 1];
                } else if (j == 1 && i > 1 && i < n1) {
                    // case 4
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hx * hx * currentSolution[i][j - 1];
                } else if (i > 1 && i < n1 && j > 1 && j < n2) {
                    // case 5
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j);
                } else if (j == n2 && i > 1 && i < n1) {
                    // case 6
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hx * hx * currentSolution[i][j + 1];
                } else if (i == n1 && j == 1) {
                    // case 7
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hy * hy * currentSolution[i + 1][j]
                            - hx * hx * currentSolution[i][j - 1];
                } else if (i == n1 && j > 1 && j < n2) {
                    // case 8
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hy * hy * currentSolution[i + 1][j];
                } else if (i == n1 && j == n2){
                    // case 9
                    right[str] = hx * hx * hy * hy * f.apply(hx * i, hy * j)
                            - hy * hy * currentSolution[i + 1][j]
                            - hx * hx * currentSolution[i][j + 1];
                }
            }
        }
        double prec = eps;

//        System.out.println("Dim: " + dim);
//        System.out.println("Matrix slau:");
//        printMatrix(matr);
//        System.out.println("Right part: ");
//        printArray(right);
//        System.out.println("Prec: " + prec);
//        System.out.println("_______________________");

        IterativeSiedelAlgorithm alg_Siedel = new IterativeSiedelAlgorithm();
        alg_Siedel.getData(dim, matr, right, prec);
        double[] res_s = alg_Siedel.solveSiedel();
        return res_s;
    }

    @Override
    double[] solve(int method, double relaxation) throws IOException, PythonExecutionException {
//        valueN1 = 3; // x, str
//        valueN2 = 5; // y, col
        // comment for input N
        hx = right_x / valueN1;
        hy = right_y / valueN2;
        System.out.println("hx : " + hx + ", hy: " + hy + ", hx^2 + hy^2 = " + (hx * hx + hy * hy));
        double eps_write = 0.00005;
        double[][] previousSolution = new double[valueN1 + 1][valueN2 + 1];
        double[][] currentSolution = new double[valueN1 + 1][valueN2 + 1];
        double[][] realSolution = new double[valueN1 + 1][valueN2 + 1];
        for (int i = 0; i <= valueN1; i++) {
            for (int j = 0; j <= valueN2; j++) {
                realSolution[i][j] = analyticSolution.apply(hx * i, hy * j);
            }
        }
        fullSolution = new ArrayList<>();
        for (int i = 0; i <= valueN1; i++) {
            currentSolution[i][0] = phi3.apply(hx * i);
            currentSolution[i][valueN2] = phi4.apply(hx * i);
        }
//        printMatrix(currentSolution);

        writeMatrix(eps_write, currentSolution);
//        double[] res_s = fillDataAndSolve(eps_write, currentSolution);

        IterativeSiedelAlgorithm alg_Siedel = new IterativeSiedelAlgorithm();
        alg_Siedel.readData("./src/main/java/lab7/test_matr_3.txt");
        double[] res_s = alg_Siedel.solveIterative();
//        System.out.println("Got res of size " + res_s.length + ": ");
//        printArray(res_s);

        for (int i = 0; i <= valueN1; i++) {
            for (int j = 1; j < valueN2; j++) {
                currentSolution[i][j] = res_s[i * (valueN2 - 1) + (j - 1)];
            }
        }
//        System.out.println("After iteration method: ");
//        printMatrix(currentSolution);

        for (int i = 0; i < currentSolution.length; i++) {
            System.arraycopy(currentSolution[i], 0, previousSolution[i], 0, currentSolution[i].length);
        }

        double eps_res = 0.00005;
        double err = matrixNorm(currentSolution, realSolution);
//        System.out.println("\nReal solution: ");
//        printMatrix(realSolution);
//        System.out.println("Err before method: " + err);
        int cnt = 0;
        if (method == 1) {
//            System.out.println("~~~ Liebmann method ~~~");
            while (err > eps_res) {
                for (int i = 1; i <= valueN1 - 1; i++) {
                    for (int j = 1; j <= valueN2 - 1; j++) {
                        currentSolution[i][j] = (hy * hy * previousSolution[i + 1][j]
                                + hy * hy * previousSolution[i - 1][j]
                                + hx * hx * previousSolution[i][j + 1]
                                + hx * hx * previousSolution[i][j - 1]
                                - hx * hx * hy * hy * f.apply(hx * i, hy * j))
                                / (2.0 * hx * hx + 2.0 * hy * hy - hx * hx * hy * hy);
                    }
                }
                for (int j = 1; j <= valueN2 - 1; j++) {
                    currentSolution[0][j] = currentSolution[1][j] - hx * phi1.apply(hy * j);
                    currentSolution[valueN1][j] = (hx * phi2.apply(hy * j) + currentSolution[valueN1 - 1][j])
                            / (1.0 - hx);
                }

                err = matrixNorm(currentSolution, previousSolution);
                for (int i = 0; i < currentSolution.length; i++) {
                    System.arraycopy(currentSolution[i], 0, previousSolution[i], 0, currentSolution[i].length);
                }
                cnt++;
            }
        } else if (method == 2) {
//            System.out.println("~~~ Siedel method ~~~");
            while (err > eps_res) {
                for (int i = 1; i <= valueN1 - 1; i++) {
                    for (int j = 1; j <= valueN2 - 1; j++) {
                        currentSolution[i][j] = (hy * hy * previousSolution[i + 1][j]
                                + hx * hx * previousSolution[i][j + 1]
                                + hy * hy * currentSolution[i - 1][j]
                                + hx * hx * currentSolution[i][j - 1]
                                - hx * hx * hy * hy * f.apply(hx * i, hy * j))
                                / (2.0 * hx * hx + 2.0 * hy * hy - hx * hx * hy * hy);
                    }
                }
                for (int j = 1; j <= valueN2 - 1; j++) {
                    currentSolution[0][j] = currentSolution[1][j] - hx * phi1.apply(hy * j);
                    currentSolution[valueN1][j] = (hx * phi2.apply(hy * j) + currentSolution[valueN1 - 1][j])
                            / (1.0 - hx);
                }
                err = matrixNorm(currentSolution, previousSolution);
                for (int i = 0; i < currentSolution.length; i++) {
                    System.arraycopy(currentSolution[i], 0, previousSolution[i], 0, currentSolution[i].length);
                }
                cnt++;
            }
        } else if (method == 3) {
//            System.out.println("~~~ Relaxation method ~~~");
            while (err > eps_res) {
                for (int i = 1; i < valueN1; i++) {
                    for (int j = 1; j < valueN2; j++) {
                        currentSolution[i][j] = (1.0 - relaxation) * previousSolution[i][j]
                                + relaxation * (hy * hy * previousSolution[i + 1][j]
                                + hx * hx * previousSolution[i][j + 1]
                                + hy * hy * currentSolution[i - 1][j]
                                + hx * hx * currentSolution[i][j - 1]
                                - hx * hx * hy * hy * f.apply(hx * i, hy * j))
                                / (2.0 * hx * hx + 2.0 * hy * hy - hx * hx * hy * hy);
                    }
                }
                for (int j = 1; j <= valueN2 - 1; j++) {
                    currentSolution[0][j] = currentSolution[1][j] - hx * phi1.apply(hy * j);
                    currentSolution[valueN1][j] = (hx * phi2.apply(hy * j) + currentSolution[valueN1 - 1][j])
                            / (1.0 - hx);
                }
                err = matrixNorm(currentSolution, previousSolution);
                for (int i = 0; i < currentSolution.length; i++) {
                    System.arraycopy(currentSolution[i], 0, previousSolution[i], 0, currentSolution[i].length);
                }
                cnt++;
                if (cnt % 5000 == 0) {
                    System.out.println("x5000");
                }
            }
        }
//        System.out.println("\nSolution:");
//        printMatrix(currentSolution);
//        System.out.println("\nReal solution: ");
//        printMatrix(realSolution);
        System.out.println("Final err: " + err + ", cnt: " + cnt);

        for (int i = 0; i < valueN1 + 1; i++) {
            ArrayList<Double> a = new ArrayList<>();
            for (int j = 0; j < valueN2 + 1; j++) {
                a.add(currentSolution[i][j]);
            }
            fullSolution.add(a);
        }
        return new double[0];
    }

    ArrayList<ArrayList<Double>> getFullSolution() {
        return fullSolution;
    }

    double getHx() {
        return hx;
    }

    public double getHy() {
        return hy;
    }
}
