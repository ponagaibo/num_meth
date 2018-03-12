package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class QR_Algorithm {
    private int dim;
    private double precision;
    private double[][] original;
    private double[][] Q_matrix;
    private double[][] R_matrix;

    void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextInt()) return;
        dim = sc.nextInt();
        original = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!sc.hasNextDouble()) return;
                original[i][j] = sc.nextDouble();
            }
        }
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    private double vector_norm(double[] vect, int start) {
        double sum = 0;
        for (int i = start; i < dim; i++) {
            sum += vect[i] * vect[i];
        }
        return Math.sqrt(sum);
    }

    private double transposed_x_vector(double[] v) {
        double res = 0;
        for (double aV : v) {
            res += aV * aV;
        }
        return res;
    }

    private double[][] vector_x_transposed(double[] v) {
        double[][] res = new double[v.length][v.length];
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                res[i][j] = v[i] * v[j];
            }
        }
        return res;
    }

    private int my_sign(double a) {
        if (a >= 0) return 1;
        else return -1;
    }

    private double[][] E_matrix(int dim) {
        double[][] res = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            res[i][i] = 1;
        }
        return res;
    }

    private void decompose(double[][] orig) {
        double[][] A_tmp = new double[dim][dim];
        double[][] H_tmp = new double[dim][dim];
        double[][] E = E_matrix(dim);
        Q_matrix = E_matrix(dim);
        R_matrix = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(orig[i], 0, A_tmp[i], 0, dim);
        }
        for (int i = 0; i < dim - 1; i++) {
            double[] v = new double[dim];
            for (int j = i ; j < dim; j++) {
                if (i == j) {
                    double tmp = vector_norm(MatrixOperations.transpose(A_tmp)[j], j);
                    v[j] = A_tmp[j][j] + my_sign(A_tmp[j][j]) * tmp;
                } else {
                    v[j] = A_tmp[j][i];
                }
            }

            double coef = transposed_x_vector(v);
            for (int ii = 0; ii < dim; ii++) {
                for (int jj = 0; jj < dim; jj++) {
                    H_tmp[ii][jj] = E[ii][jj] - 2 * vector_x_transposed(v)[ii][jj] / coef;
                }
            }
//            System.out.println("\nH matrix");
//            MatrixOperations.print_matrix(H_tmp);
            Q_matrix = MatrixOperations.multiply(Q_matrix, H_tmp);
            A_tmp = MatrixOperations.multiply(H_tmp, A_tmp);
//            System.out.println("\nNew a matrix");
//            MatrixOperations.print_matrix(A_tmp);
        }

        for (int i = 0; i < dim; i++) {
            System.arraycopy(A_tmp[i], 0, R_matrix[i], 0, dim);
        }


    }

    private void solve() {
        double[][] cur_A = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(original[i], 0, cur_A[i], 0, dim);
        }
        double eps = 6;
        while (eps > precision) {
            decompose(cur_A);

//            System.out.println("\nQ matrix");
//            MatrixOperations.print_matrix(Q_matrix);
//            System.out.println("\nR matrix");
//            MatrixOperations.print_matrix(R_matrix);
//            double[][] res = MatrixOperations.multiply(Q_matrix, R_matrix);
//            System.out.println("\nres matrix");
//            MatrixOperations.print_matrix(res);

            cur_A = MatrixOperations.multiply(R_matrix, Q_matrix);
            eps--;
        }

    }

    void lab1_n8_1_5() {
        System.out.println("\n~~~ QR algorithm ~~~");
        System.out.println("\nOriginal matrix:");
        MatrixOperations.print_matrix(original);
        solve();
        System.out.println("\n~~~~~~~~~~~~~~~~~~");
    }
}
