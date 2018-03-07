package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LU_Algorithm {
    private static double[][] L_matrix;
    private static double[][] U_matrix;
    private static double[][] A_matrix;
    private static double[] b;
    private static int sign;

    private static double[][] E_matrix = { {1, 0, 0, 0},
                                           {0, 1, 0, 0},
                                           {0, 0, 1, 0},
                                           {0, 0, 0, 1} };
    private static int dim;

    public LU_Algorithm(int n, double[][] matr, double[] b) {
        dim = n;
        A_matrix = matr;
        L_matrix = new double[n][n];
        U_matrix = new double[n][n];
        LU_Algorithm.b = b;
    }

    private static void decompose() {
        double[][] tmpL = new double[dim][dim];
        double[][] tmpA = new double[dim][dim];
        double[][] originalA = new double[dim][dim];
        double[] original_b = new double[dim];
        int[] p = new int[dim];
        boolean flag = false;

        for (int i = 0; i < dim; i++) {
            p[i] = i;
            System.arraycopy(A_matrix[i], 0, tmpA[i], 0, dim);
            System.arraycopy(A_matrix[i], 0, originalA[i], 0, dim);
        }
        System.arraycopy(b, 0, original_b, 0, dim);

        for (int i = 0; i < dim - 1; i++) {
            double max = Math.abs(tmpA[p[i]][i]);
            int pos = i;
            for (int j = i + 1; j < dim; j++) {
                if (Math.abs(tmpA[p[j]][i]) > max) {
                    max = tmpA[p[j]][i];
                    pos = j;
                }
            }
            if (pos != i) {
                flag = !flag;
            }
            int c = p[i];
            p[i] = p[pos];
            p[pos] = c;
            for (int j = i + 1; j < dim; j++) {
                double k = tmpA[p[j]][i] / tmpA[p[i]][i];
                for (int h = 0; h < dim; h++) {
                    tmpA[p[j]][h] -= k * tmpA[p[i]][h];
                }
                tmpL[p[j]][i] = k;
            }
        }
        for (int i = 0; i < dim; i++) {
            System.arraycopy(tmpA[p[i]], 0, U_matrix[i], 0, dim);
            System.arraycopy(tmpL[p[i]], 0, L_matrix[i], 0, dim);
            System.arraycopy(originalA[p[i]], 0, A_matrix[i], 0, dim);
            b[i] = original_b[p[i]];
        }
        for (int i = 0; i < dim; i++) {
            L_matrix[i][i] = 1.0;
        }
        if (flag) {
            sign = -1;
        } else {
            sign = 1;
        }
    }


    private static double[] solve(double[] column) {
        double[] tmp = new double[dim];
        System.arraycopy(column, 0, tmp, 0, dim);
        for (int i = 0; i < dim - 1; i++) {
            for (int j = i + 1; j < dim; j++) {
                tmp[j] -= L_matrix[j][i] * tmp[i];
            }
        }
        for (int i = dim - 1; i >= 0; i--) {
            tmp[i] /= U_matrix[i][i];
            for (int j = i - 1; j >= 0; j--) {
                tmp[j] -= U_matrix[j][i] * tmp[i];
            }
        }
        return tmp;
    }

    private static double[][] inverse_matrix() {
        double[][] inverse_m = new double[dim][dim];
        for (int j = 0; j < dim; j++) {
            double[] tmp = solve(E_matrix[j]);
            for (int i = 0; i < dim; i++) {
                inverse_m[i][j] = tmp[i];
            }
        }
        return inverse_m;
    }

    private static double determinant() {
        double res = 1;
        for (int i = 0; i < dim; i++) {
            res *= U_matrix[i][i];
        }
        return res * sign;
    }

    public static void lab1_n8_1_1() {
        // Item 1.1
        System.out.println("\n~~~ LU algorithm ~~~");
        decompose();
        System.out.println("\nMatrix A:");
        MatrixOperations.print_matrix(A_matrix);
        System.out.println("\nMatrix L:");
        MatrixOperations.print_matrix(L_matrix);
        System.out.println("\nMatrix U:");
        MatrixOperations.print_matrix(U_matrix);
        System.out.println("\nDeterminant = " + determinant());
        double[] solving = solve(b);
        System.out.println("\nSolving by LU:");
        for (int i = 0; i < dim; i++) {
            double tmp = solving[i] * 100000;
            int tmp1 = (int)tmp;
            tmp = tmp1 / 100000.0;
            System.out.print("" + tmp + " ");
        }
        double[][] inverse_m = inverse_matrix();
        System.out.println("\n\nInverse matrix:");
        MatrixOperations.print_matrix(inverse_m);
        double[][] obr = MatrixOperations.multiply(A_matrix, inverse_m);
        System.out.println("\nA * A^-1:");
        MatrixOperations.print_matrix(obr);
        System.out.println("\n~~~~~~~~~~~~~~~~~~");
    }

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        int cnt = 0;
        if (!sc.hasNextInt()) return;
        dim = sc.nextInt();
        A_matrix = new double[dim][dim];
        L_matrix = new double[dim][dim];
        U_matrix = new double[dim][dim];
        b = new double[dim];
        double[] elem = new double[dim * (dim + 1)];
        while (sc.hasNextDouble() && cnt < dim * (dim + 1)) {
            elem[cnt++] = sc.nextDouble();
        }
        cnt = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                A_matrix[i][j] = elem[cnt++];
            }
        }
        for (int i = 0; i < dim; i++) {
            b[i] = elem[cnt++];
        }
    }
}