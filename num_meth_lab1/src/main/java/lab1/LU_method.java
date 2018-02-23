package lab1;

public class LU_method {
    // A = LU
    // E = AX, X = A^(-1)
    private static double[][] L_matrix;
    private static double[][] U_matrix;
    private static double[][] A_matrix;
    private static double[] b;
    private static int sign;

    private static double[][] E_matrix = { {1, 0, 0, 0},
                                           {0, 1, 0, 0},
                                           {0, 0, 1, 0},
                                           {0, 0, 0, 1} };
    static int dim;

    public LU_method(int n, double[][] matr, double[] b) {
        dim = n;
        A_matrix = matr;
        L_matrix = new double[n][n];
        U_matrix = new double[n][n];
        this.b = b;
    }

    public LU_method(int n, double[][] matr) {
        dim = n;
        A_matrix = matr;
        L_matrix = new double[n][n];
        U_matrix = new double[n][n];
        this.b = b;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public void setB(double[] b) {
        this.b = b;
    }

    public void setMatrix(double[][] matr) {
        A_matrix = matr;
    }

    public static void decompose_without_perm() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                U_matrix[0][i] = A_matrix[0][i];
                L_matrix[i][0] = A_matrix[i][0] / U_matrix[0][0];
                double sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += L_matrix[i][k] * U_matrix[k][j];
                }
                U_matrix[i][j] = A_matrix[i][j] - sum;
                if (i > j) {
                    L_matrix[j][i] = 0;
                } else {
                    sum = 0;
                    for (int k = 0; k < i; k++) {
                        sum += L_matrix[j][k] * U_matrix[k][i];
                    }
                    L_matrix[j][i] = (A_matrix[j][i] - sum) / U_matrix[i][i];
                }
            }
        }
    }

    public static double[][] multiply(double[][] A, double[][] B) {
        double[][] C = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                C[i][j] = 0.0;
            }
        }
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                for (int k = 0; k < dim; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static void decompose() {
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
                flag = !(flag & true);
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


    public static double[] solve(double[] column) {
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

    public static double[][] inverse_matrix() {
        double[][] inverse_m = new double[dim][dim];
        for (int j = 0; j < dim; j++) {
            double[] tmp = solve(E_matrix[j]);
            for (int i = 0; i < dim; i++) {
                inverse_m[i][j] = tmp[i];
            }
        }
        return inverse_m;
    }

    public static double determinant() {
        double res = 1;
        for (int i = 0; i < dim; i++) {
            res *= U_matrix[i][i];
        }
        return res * sign;
    }

    public static void print_matrix(double[][] matrix) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print("" + matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void lab1_n8() {
        // Item 1.1
        decompose();
        System.out.println("Matrix A:");
        print_matrix(A_matrix);
        System.out.println("\nMatrix L:");
        print_matrix(L_matrix);
        System.out.println("\nMatrix U:");
        print_matrix(U_matrix);
        System.out.println("\nDeterminant = " + determinant());
        double[] solving = solve(b);
        System.out.println("\nSolving of system:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + solving[i] + " ");
        }
        double[][] inverse_m = inverse_matrix();
        System.out.println("\nInverse matrix:");
        print_matrix(inverse_m);
        double[][] obr = multiply(A_matrix, inverse_m);
        System.out.println("\nA * A^-1:");
        print_matrix(obr);
    }
}