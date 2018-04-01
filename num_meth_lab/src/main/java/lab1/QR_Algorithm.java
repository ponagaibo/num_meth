package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class QR_Algorithm {
    private int dim;
    private double precision;
    private ComplexNumber[] solving;
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
            for (int j = i; j < dim; j++) {
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
            Q_matrix = MatrixOperations.multiply(Q_matrix, H_tmp);
            A_tmp = MatrixOperations.multiply(H_tmp, A_tmp);
        }
        for (int i = 0; i < dim; i++) {
            System.arraycopy(A_tmp[i], 0, R_matrix[i], 0, dim);
        }
    }

    private double[][] solve() {
        double[][] cur_A = new double[dim][dim];
        solving = new ComplexNumber[dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(original[i], 0, cur_A[i], 0, dim);
        }
        int roots_cnt;
        int cur_pos;
        boolean found_roots = false;
        label: while (true) {
            decompose(cur_A);
            cur_A = MatrixOperations.multiply(R_matrix, Q_matrix);
            ComplexNumber[] tmp_solving = new ComplexNumber[dim];
            for (int i = 0; i < dim; i++) {
                tmp_solving[i] = new ComplexNumber(0, 0);
            }
            cur_pos = 0;
            roots_cnt = 0;
            while (cur_pos < dim) {
                if (vector_norm(MatrixOperations.transpose(cur_A)[cur_pos], cur_pos + 1) < precision) {
                    tmp_solving[cur_pos] = new ComplexNumber(cur_A[cur_pos][cur_pos], 0);
                    roots_cnt++;
                    cur_pos++;
                } else if (vector_norm(MatrixOperations.transpose(cur_A)[cur_pos], cur_pos + 2) < precision) {
                    ComplexNumber[] roots = solve_quadratic(
                            cur_A[cur_pos][cur_pos],
                            cur_A[cur_pos + 1][cur_pos + 1],
                            cur_A[cur_pos][cur_pos + 1],
                            cur_A[cur_pos + 1][cur_pos]);
                    tmp_solving[cur_pos++] = roots[0];
                    tmp_solving[cur_pos++] = roots[1];
                    roots_cnt += 2;
                } else {
                    continue label;
                }
            }
            if (roots_cnt == dim) {
                if (!found_roots) {
                    found_roots = true;
                    System.arraycopy(tmp_solving, 0, solving, 0, dim);
                } else {
                    boolean found = true;
                    for (int i = 0; i < dim; i++) {
                        if (ComplexNumber.diff(solving[i], tmp_solving[i]) > precision) {
                            found = false;
                        }
                    }
                    if (found) {
                        break;
                    } else {
                        System.arraycopy(tmp_solving, 0, solving, 0, dim);
                    }
                }
            }
        }
        return cur_A;
    }

    private ComplexNumber[] solve_quadratic(double a, double b, double c, double d) {
        double discriminant = (a + b) * (a + b) - 4 * (a * b - c * d);
        if (discriminant < 0) {
            ComplexNumber x1 = new ComplexNumber((a + b) / 2, Math.sqrt(-discriminant) / 2);
            ComplexNumber x2 = new ComplexNumber((a + b) / 2, -Math.sqrt(-discriminant) / 2);
            ComplexNumber[] res = new ComplexNumber[2];
            res[0] = x1;
            res[1] = x2;
            return res;
        } else if (discriminant > 0) {
            ComplexNumber x1 = new ComplexNumber((a + b + Math.sqrt(discriminant)) / 2, 0);
            ComplexNumber x2 = new ComplexNumber((a + b - Math.sqrt(discriminant)) / 2, 0);
            ComplexNumber[] res = new ComplexNumber[2];
            res[0] = x1;
            res[1] = x2;
            return res;
        } else {
            ComplexNumber[] res = new ComplexNumber[1];
            ComplexNumber x = new ComplexNumber((a + b) / 2, 0);
            res[0] = x;
            return res;
        }
    }

    void lab1_n8_1_5() {
        System.out.println("\n~~~ QR algorithm ~~~");
        System.out.println("\nOriginal matrix:");
        MatrixOperations.print_matrix(original);
        double[][] m = solve();
        System.out.println("\nDiagonal matrix:");
        MatrixOperations.print_matrix(m);
        System.out.println("\nSolving:");
        for (ComplexNumber root : solving) {
            root.print_complex_number();
        }
        System.out.println("\n~~~~~~~~~~~~~~~~~~");
    }
}
